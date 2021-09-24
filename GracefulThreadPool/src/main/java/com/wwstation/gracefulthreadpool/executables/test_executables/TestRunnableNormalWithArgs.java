package com.wwstation.gracefulthreadpool.executables.test_executables;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-09-18 15:38
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TestRunnableNormalWithArgs implements Runnable {
    private String arg1;
    private String arg2;
    private String arg3;
    public String arg4;

    @Override
    public void run() {
        log.info("线程开始执行命令");
        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("线程执行完毕");
    }
}
