package com.wwstation.gracefulthreadpool.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 09:40
 */
@Service
@Slf4j
@Data
public class AsyncTestService {
    private String str = "test";
    @Async()
    public void doAsync(String str1,String str2) throws InterruptedException {
        log.info("异步线程执行开始");
        TimeUnit.SECONDS.sleep(3);
        log.info("异步线程执行完毕");
    }
}
