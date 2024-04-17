package com.sign.in.controller;

import com.sign.in.common.R;
import com.sign.in.entity.domain.LoginBody;
import com.sign.in.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 邹松林
 * @version 1.0
 * @Title: SysController
 * @Description: TODO
 * @date 2024/4/3 14:48
 */
@RestController
@RequestMapping
public class SysController {
    @Autowired
    private SysService sysService;
    @PostMapping("/login")
    public R login(@RequestBody LoginBody loginBody){
        return sysService.login(loginBody);
    }

}
