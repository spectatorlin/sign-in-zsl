package com.sign.in.controller;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.sign.in.common.R;
import com.sign.in.config.RedisCache;
import com.sign.in.constant.CacheConstants;
import com.sign.in.constant.Constants;
import com.sign.in.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class CaptchaController {


    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     */
    @GetMapping(value = "/captchaImage", name = "生产验证码")
    public R getCode(HttpServletResponse response) throws IOException {
        R ajax = R.ok();
        ajax.put("captchaEnabled", true);
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;
        String capText = captchaProducerMath.createText();
        capStr = capText.substring(0, capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@") + 1);
        image = captchaProducerMath.createImage(capStr);

        stringRedisTemplate.opsForValue().set(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
//        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return R.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }
}
