package com.sign.in;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sign.in.common.GDClient;
import com.sign.in.entity.IShop;
import com.sign.in.entity.IUser;
import com.sign.in.mapper.IUserMapper;
import com.sign.in.service.IShopService;
import com.sign.in.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

/**
 * @author 邹松林
 * @version 1.0
 * @Title: test
 * @Description: TODO
 * @date 2024/4/4 14:28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    IUserService iUserService;
    @Autowired
    IShopService iShopService;
    @Autowired
    IUserMapper iUserMapper;
    @Autowired
    GDClient gdClient;
    @Test
    public void get(){
//        boolean b = iUserService.removeById("15122067230");
//        System.out.println(b);
//        IUser iUser = new IUser();
//        iUser.setMobile(1234L);
//        boolean save = iUserService.save(iUser);
//        System.out.println(save);
//        List<IUser> list = iUserService.listAll();
//        for (IUser iUser : list) {
//            System.out.println(iUser.toString());
//        }

//        IUser iUser = iUserMapper.getById(15122067230L);
//        System.out.println(iUser.toString());

        List<IShop> list1 = iShopService.list();
        for (IShop iShop : list1) {
            System.out.println(iShop.toString());
        }
    }
    @Test
    public void test1(){
//        String url = String.format("https://restapi.amap.com/v3/geocode/geo?address=%s&key=b4a6ce9966cd7e7117bee0917b4f881c","中国民航大学");
//        System.out.println(url);
//        JSONObject body = JSONObject.parseObject(HttpUtil.get(url));
//        JSONObject geocodes = (JSONObject) body.getJSONArray("geocodes").get(0);
//        String location = geocodes.getString("location");
//        System.out.println(location);
//        String[] split = location.split(",");
        String[] lngLat = gdClient.getLngLat("中国民航大学");
        System.out.println(lngLat[0]+"  "+lngLat[1]);
    }

    public static void main(String[] args) {
//        JSONArray jsonArray = new JSONArray();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("sex","男");
//        map.put("age","24");
//        jsonArray.add(map);
//        System.out.println(jsonArray.toString());
        String url = String.format("https://restapi.amap.com/v3/geocode/geo?address=%s&key=b4a6ce9966cd7e7117bee0917b4f881c","中国民航大学");

        System.out.println(url);
    }
}
