package com.sign.in.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sign.in.common.R;
import com.sign.in.config.RedisCache;
import com.sign.in.entity.IUser;
import com.sign.in.entity.domain.IUserVO;
import com.sign.in.exception.ServiceException;
import com.sign.in.mapper.IUserMapper;
import com.sign.in.service.IMTLogFactory;
import com.sign.in.service.IMTService;
import com.sign.in.service.IShopService;
import com.sign.in.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IUserServiceImpl extends ServiceImpl<IUserMapper, IUser> implements IUserService {


    @Autowired
    private IShopService iShopService;
    @Autowired
    private IUserMapper iUserMapper;
    @Autowired
    private IMTService imtService;
    @Autowired
    private RedisCache redisCache;

    private final static String SALT = "2af72f100c356273d46284f6fd1dfc08";

    private final static String AES_KEY = "qbhajinldepmucsonaaaccgypwuvcjaa";
    private final static String AES_IV = "2018534749963515";

    private static final Logger logger = LoggerFactory.getLogger(IUserServiceImpl.class);

    @Override
    public R sendCode(String mobile, String deviceId) {
        if (StringUtils.isEmpty(mobile)) return R.error("手机号不能为空");
        Map<String, Object> data = new HashMap<>();
        data.put("mobile", mobile);
        final long curTime = System.currentTimeMillis();
        data.put("md5", signature(mobile, curTime));
        data.put("timestamp", String.valueOf(curTime));
//        data.put("MT-APP-Version", MT_VERSION);

        HttpRequest request = HttpUtil.createRequest(Method.POST,
                "https://app.moutai519.com.cn/xhr/front/user/register/vcode");

        request.header("MT-Device-ID", deviceId);
        request.header("MT-APP-Version", getMTVersion());
        request.header("User-Agent", "iOS;16.3;Apple;?unrecognized?");

        request.header("Content-Type", "application/json");
        HttpResponse execute = request.body(JSONObject.toJSONString(data)).execute();
        JSONObject jsonObject = JSONObject.parseObject(execute.body());
        //成功返回 {"code":2000}
        logger.info("「发送验证码返回」：" + jsonObject.toJSONString());
        if (jsonObject.getString("code").equals("2000")) {
            return R.ok();
        } else {
            logger.error("「发送验证码-失败」：" + jsonObject.toJSONString());
//            throw new ServiceException("发送验证码错误");
            return R.error("发送验证码错误");
//            return false;
        }
    }

    @Override
    public R insertIUser(IUserVO iUserVO) {
        IUser iUser = iUserVO.getIUser();
        String code = iUserVO.getCode();
        Long phone = iUser.getMobile();
        if (iUserMapper.selectById(phone)!=null) {
            return R.error("不能重复添加用户");
        }
        String mobile = String.valueOf(iUser.getMobile());
        if (StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)){
            return R.error("参数不能为空");
        }
        String deviceId=UUID.randomUUID().toString().toLowerCase();

        JSONObject jsonObject = login(mobile, code, deviceId);

        JSONObject data = jsonObject.getJSONObject("data");
        iUser.setUserId(data.getLong("userId"));;
        iUser.setToken(data.getString("token"));
        iUser.setCookie(data.getString("cookie"));
        iUser.setDeviceId(deviceId);

        boolean b = save(iUser);
        if (b) return R.ok();
        return R.error("新增用户失败");
    }

    public JSONObject login(String mobile, String code,String deviceId) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("vCode", code);

        final long curTime = System.currentTimeMillis();
        map.put("md5", signature(mobile + code + "" + "", curTime));

        map.put("timestamp", String.valueOf(curTime));
        map.put("MT-APP-Version", getMTVersion());

        HttpRequest request = HttpUtil.createRequest(Method.POST,
                "https://app.moutai519.com.cn/xhr/front/user/register/login");

        request.header("MT-Device-ID", deviceId);
        request.header("MT-APP-Version", getMTVersion());
        request.header("User-Agent", "iOS;16.3;Apple;?unrecognized?");
        request.header("Content-Type", "application/json");

        HttpResponse execute = request.body(JSONObject.toJSONString(map)).execute();

        JSONObject body = JSONObject.parseObject(execute.body());
        //{"code":2000,"data":{"userId":1067395815,"userName":"邹**","mobile":"151****7230","verifyStatus":1,"idCode":"362502199906194433","idType":0,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtdCIsImV4cCI6MTcxNTc2MTMxNiwidXNlcklkIjoxMDY3Mzk1ODE1LCJkZXZpY2VJZCI6ImFhMzE1N2VjLTc0YjYtNDAwMS05ZDE1LTI1MGM0MGNiNjZkNiIsImlhdCI6MTcxMzE2OTMxNn0.qhPPGbHMowFy2xb6QEWcBSostxmuEHAJ_S86K20ZROI","userTag":0,"cookie":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtdCIsImV4cCI6MTcxNTc2MTMxNiwidXNlcklkIjoxMDY3Mzk1ODE1LCJkZXZpY2VJZCI6ImFhMzE1N2VjLTc0YjYtNDAwMS05ZDE1LTI1MGM0MGNiNjZkNiIsImlhdCI6MTcxMzE2OTMxNn0.xJctQNuRvvaznvzXnYvaF-QG0kS8TV_kugSaLmfkmV4","did":"did:mt:1:0x973e8ff6644720903169f36b0bd92afbd50a9f9df1aa2df5425e995bc3e0ab37","birthday":"1999-06-19"}}
        if (body.getString("code").equals("2000")) {
            return body;
        } else {
            logger.error("「登录请求-失败」" + body.toJSONString());
            throw new ServiceException("登录失败，本地错误日志已记录");
        }

    }

    @Override
    public R reservation(String mobile) {
        if (mobile == null || mobile.length() != 11) return R.error("用户不存在");
//        IUser iUser = getOne(Wrappers.<IUser>lambdaQuery().eq(IUser::getMobile, Long.parseLong(mobile)));
        IUser iUser = iUserMapper.getById(Long.parseLong(mobile));
//        IUser iUser = getById(Long.parseLong(mobile));
//        IUser iUser = iUserMapper.selectById(mobile);
        if (iUser == null) return R.error("用户不存在");
        String itemCode = iUser.getItemCode();
        if (StringUtils.isEmpty(itemCode)) return R.error("商品预约码不存在");

        String[] items = itemCode.split("@");
        String logContent = "";
        for (String itemId : items) {
            try {
                String shopId = iShopService.getShopId(iUser.getShopType(), itemId,
                        iUser.getProvinceName(), iUser.getCityName(), iUser.getLat(), iUser.getLng());
                JSONObject json = reservation(iUser, itemId, shopId);
                logContent += String.format("[预约项目]：%s\n[shopId]：%s\n[结果返回]：%s\n\n", itemId, shopId, json.toString());
            }catch (Exception e){
                logContent += String.format("执行报错--[预约项目]：%s\n[结果返回]：%s\n\n", itemId, e.getMessage());
            }

        }
        //日志记录
        IMTLogFactory.reservation(iUser, logContent);
        //预约后延迟领取耐力值
//        getEnergyAwardDelay(iUser);

        return R.ok();
    }



    public JSONObject reservation(IUser iUser, String itemId, String shopId) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> info = new HashMap<>();
        JSONArray itemInfoList = new JSONArray();

        info.put("count", 1);
        info.put("itemId", "itemId");
        itemInfoList.add(info);
        map.put("itemInfoList",itemInfoList);

        map.put("sessionId", iShopService.getCurrentSessionId());
        map.put("userId", iUser.getUserId().toString());
        map.put("shopId", shopId);

        map.put("actParam", AesEncrypt(JSON.toJSONString(map)));

        HttpRequest request = HttpUtil.createRequest(Method.POST,
                "https://app.moutai519.com.cn/xhr/front/mall/reservation/add");
        request.header("MT-Lat", iUser.getLat());
        request.header("MT-Lng", iUser.getLng());
        request.header("MT-Token", iUser.getToken());
        request.header("MT-Info", "028e7f96f6369cafe1d105579c5b9377");
        request.header("MT-Device-ID", iUser.getDeviceId());
        request.header("MT-APP-Version", imtService.getMTVersion());
        request.header("User-Agent", "iOS;16.3;Apple;?unrecognized?");
        request.header("Content-Type", "application/json");
        request.header("userId", iUser.getUserId().toString());
//        String post = HttpUtil.post("https://app.moutai519.com.cn/xhr/front/mall/reservation/add", "");
        HttpResponse response = request.body(JSONObject.toJSONString(map)).execute();
        JSONObject body = JSONObject.parseObject(response.body());
        //{"code":2000,"data":{"successDesc":"申购完成，请于7月6日18:00查看预约申购结果","reservationList":[{"reservationId":17053404357,"sessionId":678,"shopId":"233331084001","reservationTime":1688608601720,"itemId":"10214","count":1}],"reservationDetail":{"desc":"申购成功后将以短信形式通知您，请您在申购成功次日18:00前确认支付方式，并在7天内完成提货。","lotteryTime":1688637600000,"cacheValidTime":1688637600000}}}
        if (body.getInteger("code") != 2000) {
            String message = body.getString("message");
            logger.error(message);
            throw new ServiceException(message);
        }
        return body;
    }

    /**
     * 加密
     *
     * @param params
     * @return
     */
    public static String AesEncrypt(String params) {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, AES_KEY.getBytes(), AES_IV.getBytes());
        return aes.encryptBase64(params);
    }

    /**
     * 解密
     *
     * @param params
     * @return
     */
    public static String AesDecrypt(String params) {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, AES_KEY.getBytes(), AES_IV.getBytes());
        return aes.decryptStr(params);
    }

    /**
     * 获取验证码的md5签名，密钥+手机号+时间
     * 登录的md5签名：密钥+mobile+vCode+ydLogId+ydToken
     *
     * @param content
     * @return
     */
    private static String signature(String content, long time) {

        String text = SALT + content + time;
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            md5 = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public String getMTVersion() {
        String mtVersion = Convert.toStr(redisCache.getCacheObject("mt_version"));
        if (StringUtils.isNotEmpty(mtVersion)) {
            return mtVersion;
        }
        String url = "https://apps.apple.com/cn/app/i%E8%8C%85%E5%8F%B0/id1600482450";
        String htmlContent = HttpUtil.get(url);
        Pattern pattern = Pattern.compile("new__latest__version\">(.*?)</p>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(htmlContent);
        if (matcher.find()) {
            mtVersion = matcher.group(1);
            mtVersion = mtVersion.replace("版本 ", "");
        }
        redisCache.setCacheObject("mt_version", mtVersion);

        return mtVersion;

    }


}
