package com.sign.in.controller;

import com.sign.in.common.R;
import com.sign.in.entity.IShop;
import com.sign.in.mapper.IShopMapper;
import com.sign.in.service.IShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * i茅台商品Controller
 *
 * @author oddfar
 * @date 2023-07-05
 */
@RestController
@RequestMapping("/imt/shop")
@RequiredArgsConstructor
public class IShopController {


    /**
     * 查询i茅台商品列表
     */
    @GetMapping("/list")
    public R list(IShop iShop) {

        return R.ok();
    }


    /**
     * 刷新i茅台商品列表
     */
    @GetMapping(value = "/refresh", name = "刷新i茅台商品列表")
    public R refreshShop() {
        return R.ok();
    }

}
