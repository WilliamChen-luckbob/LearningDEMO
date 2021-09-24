//package com.wwstation.gracefulthreadpool.configs;
//
//import com.wwstation.gracefulthreadpool.components.AsyncThreadPoolBean;
//import com.wwstation.gracefulthreadpool.components.MyCustomizedThreadPoolExecutor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//
//import javax.annotation.Resource;
//import java.util.concurrent.Executor;
//
///**
// * @author william
// * @description
// * @Date: 2021-09-17 14:55
// */
//@Configuration
//@AutoConfigureAfter(AsyncThreadPoolBean.class)
//@Slf4j
//public class AsyncConfig implements AsyncConfigurer {
//    @Resource
//    ApplicationContext context;
//
//    @Override
//    public Executor getAsyncExecutor() {
//        MyCustomizedThreadPoolExecutor myExecutor = context.getBean("myExecutor", MyCustomizedThreadPoolExecutor.class);
//        return myExecutor;
//    }
//}
