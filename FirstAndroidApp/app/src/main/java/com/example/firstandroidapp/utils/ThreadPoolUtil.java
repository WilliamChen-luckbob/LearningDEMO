package com.example.SimpleSender.utils;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-10-21 10:15
 */
public class ThreadPoolUtil {
    private static ThreadPoolExecutor threadPool;

    private ThreadPoolUtil() {
    }

    public static void submit(Runnable runnable) {
        if (threadPool == null) {
            initThreadPool();
        }
        threadPool.submit(runnable);
    }

    synchronized
    private static void initThreadPool() {
        if (threadPool == null) {
            threadPool = new ThreadPoolExecutor(5, 15, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    Log.i("thread", "工作线程加入任务");
                    return new Thread(runnable, "工作线程");
                }
            }, new ThreadPoolExecutor.DiscardPolicy());
        }
    }
}
