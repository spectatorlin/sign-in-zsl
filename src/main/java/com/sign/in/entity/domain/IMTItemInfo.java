package com.sign.in.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * i茅台预约商品信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IMTItemInfo implements Serializable {

    private String shopId;

    private int count;

    private String itemId;

    /**
     * 库存
     */
    private int inventory;


}
