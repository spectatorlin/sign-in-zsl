package com.sign.in.controller;

import com.sign.in.common.R;
import com.sign.in.entity.IUser;
import com.sign.in.entity.domain.IUserVO;
import com.sign.in.mapper.IUserMapper;
import com.sign.in.service.IMTService;
import com.sign.in.service.IShopService;
import com.sign.in.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/imt/user")
public class IUserController {
    @Autowired
    private IUserService iUserService;
    /**
     * 查询I茅台用户列表
     */
    @GetMapping(value = "/list", name = "查询I茅台用户列表")
    public R list(IUser iUser) {
        return R.ok();
    }

    /**
     * 发送验证码
     */
    @GetMapping(value = "/sendCode", name = "发送验证码")
    public R sendCode(@RequestParam String mobile, String deviceId) {
        return iUserService.sendCode(mobile,deviceId);
    }

    /**
     * 预约
     */
    @GetMapping(value = "/reservation", name = "预约")
    public R reservation(@RequestParam String mobile) {
        return iUserService.reservation(mobile);
    }

    /**
     * 小茅运旅行活动
     */
    @GetMapping(value = "/travelReward", name = "旅行")
    public R travelReward(String mobile) {
        return R.ok();
    }

    /**
     * 登录
     */
    @GetMapping(value = "/login", name = "登录")
    public R login(String mobile, String code, String deviceId) {
        return R.ok();
    }


    /**
     * 获取I茅台用户详细信息
     */
    @GetMapping(value = "/{mobile}", name = "获取I茅台用户详细信息")
    public R getInfo(@PathVariable("mobile") Long mobile) {
        return R.ok();
    }

    /**
     * 新增I茅台用户
     */
    @PostMapping(name = "新增I茅台用户")
    public R add(@RequestBody IUserVO iUserVO) {
        return iUserService.insertIUser(iUserVO);
    }

    /**
     * 修改I茅台用户
     */
    @PutMapping(name = "修改I茅台用户")
    public R edit(@RequestBody IUser iUser) {
        return R.ok();
    }

    /**
     * 删除I茅台用户
     */
    @DeleteMapping(value = "/{mobiles}", name = "删除I茅台用户")
    public R remove(@PathVariable Long[] mobiles) {
        return R.ok();
    }
}
