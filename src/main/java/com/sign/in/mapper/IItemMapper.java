package com.sign.in.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sign.in.entity.IItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * I茅台预约商品Mapper接口
 *
 * @author oddfar
 * @date 2023-07-02
 */
@Mapper
public interface IItemMapper extends BaseMapper<IItem> {

    //清空指定表
    @Update("truncate table i_item")
    void truncateItem();
}
