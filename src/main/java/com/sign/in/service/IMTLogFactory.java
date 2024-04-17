package com.sign.in.service;

import com.sign.in.entity.ILog;
import com.sign.in.entity.IUser;

import java.util.TimerTask;

/**
 * i茅台日志记录
 */
public class IMTLogFactory {


    public static void reservation(IUser iUser, String logContent) {
        //{"code":2000,"data":{"successDesc":"申购完成，请于7月6日18:00查看预约申购结果","reservationList":[{"reservationId":17053404357,"sessionId":678,"shopId":"233331084001","reservationTime":1688608601720,"itemId":"10214","count":1}],"reservationDetail":{"desc":"申购成功后将以短信形式通知您，请您在申购成功次日18:00前确认支付方式，并在7天内完成提货。","lotteryTime":1688637600000,"cacheValidTime":1688637600000}}}

    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final ILog operLog) {
        return null;
    }


}
