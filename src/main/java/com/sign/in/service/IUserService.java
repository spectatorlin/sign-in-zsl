package com.sign.in.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sign.in.common.R;
import com.sign.in.entity.IUser;
import com.sign.in.entity.domain.IUserVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface IUserService extends IService<IUser> {
    /**
     * 预约
     */
    public R reservation(String mobile);

    /**
     * 发送手机验证码
     *
     * @param mobile   手机号
     * @param deviceId 设备id
     */
    R sendCode(String mobile, String deviceId);

    /**
     * 添加i茅台用户
     *
     * @param iUserVO
     * @return
     */
    R insertIUser(IUserVO iUserVO);

    /**
     * 每天9:10批量预约
     *
     * @return
     */
    @Scheduled(cron = "0 10 9 * * ?")
    void batchReservation();

}
