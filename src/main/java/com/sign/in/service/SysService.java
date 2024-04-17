package com.sign.in.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sign.in.common.R;
import com.sign.in.entity.SysUserEntity;
import com.sign.in.entity.domain.LoginBody;

/**
 * @author 邹松林
 * @version 1.0
 * @Title: SysService
 * @Description: TODO
 * @date 2024/4/3 14:53
 */
public interface SysService extends IService<SysUserEntity> {

    public R login(LoginBody loginBody);

}
