package com.wwstation.gracefulthreadpool.components;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 11:57
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.thread-pool.reject-free",name = "enabled",havingValue = "true")
public class RejectFreeExecutorConfiguration {
    @Bean
    @ConditionalOnMissingBean(RejectFreeThreadPoolExecutor.class)
    public RejectFreeThreadPoolExecutor getExecutor() {
        return new RejectFreeThreadPoolExecutor(
            5,
            10,
            15,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(20),
            r -> new Thread(r, "rejectFreeExecutor")
        );
    }
}
