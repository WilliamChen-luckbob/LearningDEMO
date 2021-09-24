package com.wwstation.gracefulthreadpool;

import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 14:47
 */
public class MyAsyncInterceptor extends AsyncExecutionAspectSupport {
    public MyAsyncInterceptor(Executor defaultExecutor) {
        super(defaultExecutor);
    }

    public MyAsyncInterceptor(Executor defaultExecutor, AsyncUncaughtExceptionHandler exceptionHandler) {
        super(defaultExecutor, exceptionHandler);
    }

    @Override
    protected String getExecutorQualifier(Method method) {
        return null;
    }
}
