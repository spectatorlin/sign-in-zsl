package com.sign.in.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sign.in.common.R;
import com.sign.in.constant.CacheConstants;
import com.sign.in.constant.Constants;
import com.sign.in.entity.SysUserEntity;
import com.sign.in.entity.domain.LoginBody;
import com.sign.in.mapper.SysUserEntityMapper;
import com.sign.in.service.SysService;
import com.sign.in.utils.PasswordEncoder;
import com.sign.in.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 邹松林
 * @version 1.0
 * @Title: SysServiceImpl
 * @Description: TODO
 * @date 2024/4/3 14:57
 */
@Service
public class SysServiceImpl extends ServiceImpl<SysUserEntityMapper,SysUserEntity> implements SysService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysUserEntityMapper sysUserEntityMapper;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public R login(LoginBody loginBody) {
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        String code = loginBody.getCode();
        if (username == null || username.isEmpty()) return R.error("请输入账号");
        if (password==null || password.isEmpty()) return R.error("请输入密码");
        if (code==null || code.isEmpty()) return R.error("请输入验证码");
        String key= CacheConstants.CAPTCHA_CODE_KEY + loginBody.getUuid();
        String val = stringRedisTemplate.opsForValue().get(key);
        if (!code.equals(val)){
            return R.error("验证码错误");
        }
//        SysUserEntity sysUserEntity = sysUserEntityMapper.selectById(username);
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserEntity::getUserName,username);
        SysUserEntity sysUserEntity = sysUserEntityMapper.selectOne(queryWrapper);

        String pw = DigestUtils.md5DigestAsHex((password).getBytes(StandardCharsets.UTF_8));
//        String pw = PasswordEncoder.encode(password);
        if (!sysUserEntity.getPassword().equals(pw)){
            return R.error("密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, username);
        String token = tokenUtils.createToken(claims);
        R r = R.ok();
        r.put(Constants.TOKEN, token);
        return r;
    }
}
