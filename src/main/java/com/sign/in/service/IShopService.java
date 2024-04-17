package com.sign.in.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sign.in.entity.IShop;
import com.sign.in.entity.domain.IMTItemInfo;

import java.util.List;

public interface IShopService extends IService<IShop> {
    /**
     * @param shopType 1：预约本市出货量最大的门店，2：预约你的位置附近门店
     * @param itemId   项目id即预约项目code
     * @param province 省份，例如：河北省，北京市
     * @param city     市：例如石家庄市
     * @return
     */
    String getShopId(int shopType, String itemId, String province, String city, String lat, String lng);

    /**
     * 查询所在省市的投放产品和数量
     *
     * @param province 省份，例如：河北省，北京市
     * @param itemId   项目id即预约项目code
     */

    List<IMTItemInfo> getShopsByProvince(String province, String itemId);

    /**
     * 获取当天的sessionId
     */
    String getCurrentSessionId();
}
