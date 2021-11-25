package com.wwstation.aspectj_demo.aop_using_service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author william
 * @description
 * @Date: 2021-10-15 16:41
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class SpringAOP {
    @Pointcut("execution(public * com.wwstation.aspectj_demo.aop_using_service.ProcessService.process(..))")
    public void idempotentProcess() {
    }

    @Around("idempotentProcess()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("now in pointcut, thread name={}", Thread.currentThread().getName());
        return joinPoint.proceed();
    }

}
