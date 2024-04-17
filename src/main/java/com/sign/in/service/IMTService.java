package com.sign.in.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sign.in.entity.IUser;

public interface IMTService{
    /**
     * 获取i茅台app版本号
     *
     * @return
     */
    String getMTVersion();

}
