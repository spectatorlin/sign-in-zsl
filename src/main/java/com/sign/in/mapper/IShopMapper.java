package com.sign.in.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sign.in.entity.IShop;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * I茅台商品Mapper接口
 *
 * @author oddfar
 * @date 2023-07-02
 */
@Mapper
public interface IShopMapper extends BaseMapper<IShop> {
    @Update("truncate table i_shop")
    void truncateShop();
}
