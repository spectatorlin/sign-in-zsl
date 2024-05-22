package com.sign.in.common;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author 邹松林
 * @version 1.0
 * @Title: GDClient
 * @Description: TODO
 * @date 2024/4/18 22:53
 */
@Component
@Slf4j
public class GDClient {
    public String[] getLngLat(String address){
        try {
            String url = String.format("https://restapi.amap.com/v3/geocode/geo?address=%s&key=b4a6ce9966cd7e7117bee0917b4f881c",address);
            JSONObject body = JSONObject.parseObject(HttpUtil.get(url,2000));
            JSONObject geocodes = (JSONObject) body.getJSONArray("geocodes").get(0);
            String location = geocodes.getString("location");
            if(location==null || location.length()==0){
                return new String[2];
            }
            return location.split(",");
        }
        catch (Exception e){
            log.error(String.valueOf(e));
        }
        return new String[2];
    }

    public String[] fallBack(){

        return new String[]{"0","0"};
    }

}
