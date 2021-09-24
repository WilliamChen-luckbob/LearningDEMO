package com.wwstation.gracefulthreadpool.components;

import com.wwstation.gracefulthreadpool.handlers.RejectFreeDiscardPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义的队列冲爆后能够打印详细日志并丢弃的包装线程池
 * 实现TaskExecutor用于直接将此线程池用作默认的异步任务线程池，@Async不指定即用此线程池
 * 如果没有指定名称AsyncExecutionInterceptor类的getDefaultExecutor方法中获取@Async的默认线程池
 *
 * @author william
 * @description
 * @Date: 2021-09-17 12:27
 */
@Slf4j
public class RejectFreeThreadPoolExecutor extends ThreadPoolExecutor implements TaskExecutor {
    public RejectFreeThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new RejectFreeDiscardPolicy());
    }

    public RejectFreeThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public RejectFreeThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    //todo 如何在不需要修改旧的@Async代码的情况下也能让线程池管理并使用自定义拒绝策略
//    /**
//     * 手写的加包装submit方法
//     * 在后面的拒绝策略中可以获取用户自定义的任务关键信息打入日志或后接其他方法
//     *
//     * @param task
//     * @return
//     */
//    @Override
//    public Future<?> submit(Runnable task) {
//        if (task == null) {
//            throw new NullPointerException();
//        }
//        RejectFreeExecutableFutureTask<Void> ftask = new RejectFreeExecutableFutureTask<>(task, null);
//        super.execute(ftask);
//        return ftask;
//    }
//
//    /**
//     * 手写的加包装submit方法
//     * 在后面的拒绝策略中可以获取用户自定义的任务关键信息打入日志或后接其他方法
//     *
//     * @param task
//     * @param result
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> Future<T> submit(Runnable task, T result) {
//        if (task == null) {
//            throw new NullPointerException();
//        }
//        RejectFreeExecutableFutureTask<T> ftask = new RejectFreeExecutableFutureTask<>(task, result);
//        super.execute(ftask);
//        return ftask;
//    }
//
//    /**
//     * 注解@Async标记的方法在异步调用时会进入此方法
//     * 同时也可用于进行Callable任务的提交
//     * 对于@Async的使用参见{@link org.springframework.aop.interceptor.AsyncExecutionInterceptor}
//     *
//     * @param task 送入的Callable任务
//     * @param <T>  Callable的响应类型
//     * @return Future
//     */
//    @Override
//    public <T> Future<T> submit(Callable<T> task) {
//        if (task == null) {
//            throw new NullPointerException();
//        }
//        RejectFreeExecutableFutureTask<T> ftask = new RejectFreeExecutableFutureTask<>(task);
//        super.execute(ftask);
//        return ftask;
//    }
}
