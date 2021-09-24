package com.wwstation.gracefulthreadpool.executables;

import java.util.concurrent.Callable;

/**
 * @author william
 * @description
 * @Date: 2021-09-18 10:39
 */
public interface IRejectFreeCallable<T> extends IRejectFreeExecutable, Callable<T> {
}
