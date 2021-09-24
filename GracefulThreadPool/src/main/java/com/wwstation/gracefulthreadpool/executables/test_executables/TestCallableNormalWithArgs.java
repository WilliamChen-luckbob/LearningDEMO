package com.wwstation.gracefulthreadpool.executables.test_executables;

import com.wwstation.gracefulthreadpool.executables.IRejectFreeCallable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 12:10
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class TestCallableNormalWithArgs implements Callable<String> {

    private String arg1;
    private String arg2;
    private String arg3;
    public String arg4;

    @Override
    public String call() throws Exception {
        log.info("线程开始执行命令");
        TimeUnit.SECONDS.sleep(5L);
        log.info("线程执行完毕");
        return "成功";
    }
}
