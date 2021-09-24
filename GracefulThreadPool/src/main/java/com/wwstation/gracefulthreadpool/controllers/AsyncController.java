package com.wwstation.gracefulthreadpool.controllers;

import com.wwstation.gracefulthreadpool.components.RejectFreeThreadPoolExecutor;
import com.wwstation.gracefulthreadpool.executables.test_executables.TestCallableNormalWithArgs;
import com.wwstation.gracefulthreadpool.executables.test_executables.TestCallableNormalWithoutArgs;
import com.wwstation.gracefulthreadpool.executables.test_executables.TestCallableWithArgs;
import com.wwstation.gracefulthreadpool.executables.test_executables.TestCallableWithoutArgs;
import com.wwstation.gracefulthreadpool.executables.test_executables.TestRunnableNormalWithArgs;
import com.wwstation.gracefulthreadpool.executables.test_executables.TestRunnableNormalWithoutArgs;
import com.wwstation.gracefulthreadpool.executables.test_executables.TestRunnableWithArgs;
import com.wwstation.gracefulthreadpool.executables.test_executables.TestRunnableWithoutArgs;
import com.wwstation.gracefulthreadpool.service.AsyncTestService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 11:59
 */
@RestController
@RequestMapping("/async/test")
@Slf4j
public class AsyncController {
    @Resource
    private AsyncTestService component;
    @Resource
    private RejectFreeThreadPoolExecutor executor;


    @PostMapping
    public Result test() throws InterruptedException, ExecutionException {
//        log.info("开始执行异步线程");

        //使用注解调用@Async异步方法的管理
//        callAsyncService();

        //submit显式调用
        //显式调用 继承自IRejectFreeExecutable 的 无参 callable
//        submitCallableNoArg();
        //显式调用 继承自IRejectFreeExecutable 的 有参 callable
//        submitCallableWithArg();
        //显式调用 普通 的 有参 callable
//        submitNormalCallableWithArg();
        //显式调用 普通 的 无参 callable
//        submitNormalCallableNoArg();
        //显式调用 普通 的 有参 callable 但不传参
//        submitNormalCallableWithEmptyArg();
        //显式调用 继承自IRejectFreeExecutable 的 有参 callable 但不传参
//        submitCallableWithEmptyArg();
        //匿名调用 普通 的 有参 callable
//        anonymousNormalCallableWithArg();
        //匿名调用 普通 的 无参 callable
//        anonymousNormalCallableNoArg();

        //显式调用 继承自IRejectFreeExecutable 的 无参 runnable
//        submitRunnableNoArg();
        //显式调用 继承自IRejectFreeExecutable 的 有参 runnable
//        submitRunnableWithArg();
        //显式调用 普通 的 无参 runnable
//        submitNormalRunnableNoArg();
        //显式调用 普通 的 有参 runnable
//        submitNormalRunnableWithArg();
        //显式调用 普通 的 有参 runnable 但不传参
//        submitNormalRunnableWithEmptyArg();
        //显式调用 继承自IRejectFreeExecutable 的 有参 runnable 但不传参
//        submitRunnableWithEmptyArg();
        //匿名调用 普通 有参 runnable
//        anonymousRunnableWithArg();
        //匿名调用 普通 无参 runnable
//        anonymousRunnableNoArg();

        //execute 显式调用runnable
//        executeRunnableWithArg();
        //execute 匿名调用runnable
//        executeAnonymousRunnable();
        //execute 显式调用普通有参runnable
//        executeNormalRunnableWithArg();

//        log.info("主线程工作完成");
        return new Result().succeed("成功");
    }

    private void executeNormalRunnableWithArg() {
        executor.execute(new TestRunnableNormalWithArgs("1", "2", "3", "4"));
    }

    private void executeAnonymousRunnable() {
        executor.execute(new Runnable() {
            private Integer arg1 = 1;
            private Integer arg2 = 2;
            private Integer arg3 = 3;
            private Integer arg4 = 4;

            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void executeRunnableWithArg() {
        executor.execute(new TestRunnableWithArgs("1", "2", "3", "4"));
    }

    private void anonymousRunnableNoArg() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void submitCallableWithEmptyArg() {
        executor.submit(new TestCallableWithArgs());
    }

    private void submitRunnableWithEmptyArg() {
        executor.submit(new TestRunnableWithArgs());
    }

    private void submitNormalRunnableWithEmptyArg() {
        executor.submit(new TestRunnableWithArgs());
    }

    private void submitNormalRunnableWithArg() {
        Future<?> submit = executor.submit(new TestRunnableNormalWithArgs());
    }

    private void submitNormalRunnableNoArg() {
        Future<?> submit = executor.submit(new TestRunnableNormalWithoutArgs());
    }

    private void submitRunnableWithArg() {
        Future<?> submit = executor.submit(new TestRunnableWithArgs("1", "2", "3", "4"));
    }

    private void anonymousNormalCallableNoArg() {
        executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "成功";
            }
        });
    }

    private void submitNormalCallableWithEmptyArg() {
        executor.submit(new TestCallableWithArgs());
    }

    private void submitNormalCallableNoArg() {
        executor.submit(new TestCallableNormalWithoutArgs());
    }

    private void submitNormalCallableWithArg() {
        executor.submit(new TestCallableNormalWithArgs("1", "2", "3", "4"));
    }

    private void submitCallableWithArg() {
        executor.submit(new TestCallableWithArgs("1", "2", "3", "4"));
    }

    private void submitNormalRunnable() {
        executor.submit(new TestRunnableNormalWithArgs("1", "2", "3", "4"));
    }

    private void anonymousNormalCallableWithArg() {
        executor.submit(new Callable<String>() {
            private String arg1 = "test1";
            private String arg2 = "test2";
            private String test3 = "test3";

            @Override
            public String call() throws Exception {
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "成功";
            }
        });
    }

    private void submitRunnableNoArg() {
        Future<?> submit = executor.submit(new TestRunnableWithoutArgs());
    }

    private void anonymousRunnableWithArg() {
        executor.submit(new Runnable() {
            private String arg1 = "test1";
            private String arg2 = "test2";
            private String test3 = "test3";

            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void submitCallableNoArg() throws InterruptedException, ExecutionException {
        Future<String> submit = executor.submit(new TestCallableWithoutArgs());
    }

    private void callAsyncService() throws InterruptedException {
        component.doAsync("test1", "test2");
    }

    @Data
    class Result {
        private Integer code;
        private String msg;
        private Object data;

        public Result succeed(String msg) {
            Result result = new Result();
            result.setCode(200);
            result.setMsg(msg);
            result.setData(null);
            return result;
        }
    }
}
