package com.sign.in.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sign.in.entity.IUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * I茅台用户Mapper接口
 *
 * @author oddfar
 * @date 2023-07-02
 */
@Mapper
public interface IUserMapper extends BaseMapper<IUser> {

}
