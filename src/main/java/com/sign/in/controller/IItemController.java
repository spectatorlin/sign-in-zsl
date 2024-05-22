package com.sign.in.controller;

import com.sign.in.common.R;
import com.sign.in.mapper.IItemMapper;
import com.sign.in.service.IShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/imt/item")
@RequiredArgsConstructor
public class IItemController {


    /**
     * 查询I茅台预约商品列列表
     */
    @GetMapping(value = "/list", name = "查询I茅台预约商品列列表")
    public R list() {

        return R.ok();
    }

    /**
     * 刷新i茅台预约商品列表
     */
    @GetMapping(value = "/refresh", name = "刷新i茅台预约商品列表")
    public R refreshItem() {
        return R.ok();
    }

}
