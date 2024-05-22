package com.sign.in.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
/**
 * @author 邹松林
 * @version 1.0
 * @Title: ThreadPoolExecutor
 * @Description: TODO
 * @date 2024/5/22 17:24
 */
public class ThreadPoolExecutor {
    private int corePoolSize=10;
    private int maximumPoolSize=20;
    private long keepAliveTime=5;
    private int queueCapacity=100;

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, int queueCapacity) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.queueCapacity = queueCapacity;
    }
    @Bean
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        executor.setKeepAliveSeconds((int) keepAliveTime);
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        return executor;
    }

}
