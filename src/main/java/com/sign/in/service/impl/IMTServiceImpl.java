package com.sign.in.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sign.in.config.RedisCache;
import com.sign.in.service.IMTService;
import com.sign.in.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IMTServiceImpl implements IMTService {
    @Autowired
    private RedisCache redisCache;
    @Override
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
