package com.sign.in.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sign.in.entity.IUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * I茅台用户Mapper接口
 *
 * @author oddfar
 * @date 2023-07-02
 */
@Mapper
public interface IUserMapper extends BaseMapper<IUser> {
    @Select("select * from i_user where mobile=#{mobile}")
    public IUser getById(Long mobile);

}
