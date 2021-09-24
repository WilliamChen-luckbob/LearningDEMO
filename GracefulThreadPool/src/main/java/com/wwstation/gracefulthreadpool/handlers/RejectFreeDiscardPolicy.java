package com.wwstation.gracefulthreadpool.handlers;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wwstation.gracefulthreadpool.dto.RejectFreeDataDTO;
import com.wwstation.gracefulthreadpool.dto.RejectFreeDataParametersDTO;
import com.wwstation.gracefulthreadpool.executables.IRejectFreeExecutable;
import com.wwstation.gracefulthreadpool.executables.IRejectFreeRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 10:43
 */
@Slf4j
public class RejectFreeDiscardPolicy<T extends RejectedExecutionHandler> implements RejectedExecutionHandler {
    private static final String MATCH_ERROR = "未能识别的任务{}，由于线程池爆满而丢弃任务！数据内容暂且获取失败！一些可见信息为：{}";
    private static final String MATCH_UNSTABLE = "未指定跟踪的任务{}，由于线程池爆满而丢弃任务！关键数据信息如下：{}";
    private static final String MATCH_SUCCEED = "任务{}，由于线程池爆满而丢弃任务！关键数据信息如下：{}";
    private static final String FAILED_REASON = "线程池爆满遭到丢弃";

    private static final String SLEUTH_RUNNABLE_PATTERN="TraceRunnable";
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //获取真正的执行线程
        Callable<?> actualCallable = null;
        if (r.getClass().getSimpleName().equals(SLEUTH_RUNNABLE_PATTERN)) {
            //sleuth包装时
            getSleuthActualCallable(r);
            return;
        } else {
            actualCallable = getActualCallable(r);
        }


        //执行四类判断
        //1.    继承自callable的类(无论显式或隐式) 使用getActualCallable直接可拿到对应的callable
        //2.    所有Runnable 使用getActualCallable 可以获取一个 java.util.concurrent.Executors$RunnableAdapter 类型的callable
        //3.    @Async方法 使用getActualCallable 可以获取一个 AsyncExecutionInterceptor$$Lambda$ 类型的callable
        //4.    兼容sleuth sleuth使用TraceRunnable包装了一两层，通过递归获取到delegate内容为FutureTask时，这个FutureTask中的callable即为进一步所需的callable

        //如果送入线程池的是callable，参数直接在r.callable下依次取出
        //如果送入线程池的是runnable，参数在r.callable下依次取出，最后一位为runnable

        //校验是否为runnable
        if (actualCallable.getClass().getName().startsWith("java.util.concurrent.Executors$RunnableAdapter")) {
            //runnable线程分为三种：普通，继承自IRejectFreeExecutable，匿名
            tryProcessRejectFreeExecutableFromRunnable(actualCallable);
        }
        //校验是否为@Async方法
        else if (actualCallable.getClass().getSimpleName().startsWith("AsyncExecutionInterceptor$$Lambda$")) {
            //是，对方法参数进行处理
            tryMatchAndProcessAsyncMethod(actualCallable);
        }
        //校验这个callable是否继承自IRejectFreeExecutable
        else if (IRejectFreeExecutable.class.isAssignableFrom(actualCallable.getClass())) {
            if (!CollectionUtils.isEmpty(((IRejectFreeExecutable) actualCallable).getData())) {
                buildLogInfo((IRejectFreeExecutable) actualCallable);
            } else {
                try {
                    buildLogInfoByFields(actualCallable, ((IRejectFreeExecutable) actualCallable).getInvocationName());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    log.error(MATCH_ERROR, actualCallable.getClass().getName(), JSONObject.toJSONString(actualCallable));
                }
            }
        } else {
            //尝试进行手动获取callable的内容
            try {
                boolean isAnonymous = Arrays.stream(actualCallable.getClass().getDeclaredFields()).anyMatch(e -> e.getName().equals("this$0"));
                if (isAnonymous) {
                    Field originCallableArg = actualCallable.getClass().getDeclaredField("this$0");
                    originCallableArg.setAccessible(true);
                    Object originalCallable = originCallableArg.get(actualCallable);
                    buildLogInfoByFields(actualCallable, originalCallable.getClass().getName());
                } else {
                    buildLogInfoByFields(actualCallable, actualCallable.getClass().getName());
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log.error(MATCH_ERROR, actualCallable.getClass().getName(), JSONObject.toJSONString(actualCallable));
            }
        }
    }

    private void tryProcessRejectFreeExecutableFromRunnable(Callable<?> actualCallable) {
        try {
            Field runnableField = actualCallable.getClass().getDeclaredField("task");
            runnableField.setAccessible(true);
            Object probableRejectFreeRunnable = runnableField.get(actualCallable);
            String runnableName = probableRejectFreeRunnable.getClass().getName();
            if (IRejectFreeRunnable.class.isAssignableFrom(probableRejectFreeRunnable.getClass())) {
                //继承自IRejectFreeRunnable，直接处理
                buildLogInfo((IRejectFreeExecutable) probableRejectFreeRunnable);
                return;
            } else {
                //非继承自IrejectFreeRunnable，需要手动处理参数
                buildLogInfoByFields(probableRejectFreeRunnable, runnableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void buildLogInfoByFields(Object threadObject, String runnableName) throws IllegalAccessException {
        if (SLEUTH_RUNNABLE_PATTERN.equals(threadObject.getClass().getSimpleName())){
            getSleuthActualCallable((Runnable) threadObject);
            return;
        }

        List<RejectFreeDataParametersDTO> parameterList = new ArrayList<>();
        Field[] taskFields = threadObject.getClass().getDeclaredFields();
        for (int i = 0; i < taskFields.length; i++) {
            Field field = taskFields[i];
            field.setAccessible(true);
            Object o = field.get(threadObject);
            RejectFreeDataParametersDTO parameter = new RejectFreeDataParametersDTO();
            parameter.setParamValue(o);
            parameter.setParamType(field.getType());
            parameter.setParamName(field.getName());
            parameterList.add(parameter);
        }
        RejectFreeDataDTO logInfo = new RejectFreeDataDTO();
        logInfo.setMethodName(runnableName);
        logInfo.setMethodReturnType(null);
        logInfo.setFailedReson(FAILED_REASON);
        logInfo.setData(parameterList);
        buildLogInfo(logInfo);
    }

    private void buildLogInfo(RejectFreeDataDTO logInfo) {
        log.info(MATCH_UNSTABLE, logInfo.getMethodName(), JSONObject.toJSONString(logInfo));
    }

    private void buildLogInfo(IRejectFreeExecutable executable) {
        RejectFreeDataDTO dto = new RejectFreeDataDTO();
        dto.setData(executable.getData());
        dto.setFailedReson(FAILED_REASON);
        dto.setMethodName(executable.getInvocationName());
        dto.setMethodReturnType(null);
        log.info(MATCH_SUCCEED, dto.getMethodName(), JSONObject.toJSONString(dto));
    }

    private void printLogInfo(RejectFreeDataDTO logInfo) {
        log.warn(MATCH_SUCCEED, logInfo.getMethodName(), JSONObject.toJSONString(logInfo));
    }


    private void printLogInfo(String methodName,
                              String failedReason,
                              List<RejectFreeDataParametersDTO> data) {
        RejectFreeDataDTO logInfo = new RejectFreeDataDTO();
        logInfo.setMethodName(methodName);
        logInfo.setFailedReson(failedReason);
        logInfo.setData(data);
        log.warn(MATCH_SUCCEED, methodName, JSONObject.toJSONString(logInfo));
    }

//    private IRejectFreeExecutable checkRejectFreeExecutable(Callable r) {
//        try {
//            if (!IRejectFreeExecutable.class.isAssignableFrom(r.getClass())) {
//                return null;
//            } else {
//                return find4IRejectFreeExecutable(r);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private IRejectFreeExecutable find4IRejectFreeExecutable(Object o) {
        if (null == o) {
            return null;
        }

        if (IRejectFreeExecutable.class.isAssignableFrom(o.getClass())) {
            return (IRejectFreeExecutable) o;
        }
        return find4IRejectFreeExecutable(o.getClass().getSuperclass());
    }

    /**
     * 通过反射的方式获取实际的工作线程对象
     *
     * @param r
     * @return
     */
    private Callable<?> getActualCallable(Runnable r) {
        if (null == r) {
            return null;
        }
        return getActualCallableFromFutureTask(r);
    }

    private Callable<?> getActualCallableFromFutureTask(Runnable r) {
        try {
            Field callableField = getTargetField(r.getClass(), "callable");
            if (null == callableField) {

                throw new ClassNotFoundException("没有找到任何可能的callable方法");
            }
            callableField.setAccessible(true);
            return (Callable<?>) callableField.get(r);
        } catch (Exception e) {
            log.error("出现了异常：" + e.getMessage(), e);
        }
        return null;
    }

    private Callable<?> getSleuthActualCallable(Runnable r) {
        if (null == r) {
            return null;
        }

        Object delegateObj = null;

        try {
            Field delegateField = getTargetField(r.getClass(), "delegate");
            if (null == delegateField) {
                throw new ClassNotFoundException("没有找到任何可能的callable方法");
            }
            delegateField.setAccessible(true);
            delegateObj = delegateField.get(r);
        } catch (Exception e) {
            log.error("出现了异常：" + e.getMessage(), e);
        }

        if (null == delegateObj) {
            return null;
        }

        if (SLEUTH_RUNNABLE_PATTERN.equals(delegateObj.getClass().getSimpleName())) {
            //如果还有包装类，递归拆包直到拿到FutureTask
            return getSleuthActualCallable((Runnable) delegateObj);
        } else if ("FutureTask".equals(delegateObj.getClass().getSimpleName())) {
            return getActualCallableFromFutureTask((Runnable) delegateObj);
        } else if (Runnable.class.isAssignableFrom(delegateObj.getClass())) {
            try {
                buildLogInfoByFields(delegateObj, delegateObj.getClass().getSimpleName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Field getTargetField(Class<?> aClass, String callable) throws Exception {
        Field targetField = null;
        try {
            targetField = aClass.getDeclaredField(callable);
        } catch (NoSuchFieldException e) {
            return getTargetField(aClass.getSuperclass(), callable);
        }
        return targetField;
    }

    /**
     * 处理@Async标记的异步方法
     *
     * @param actualCallable
     */
    private void tryMatchAndProcessAsyncMethod(Callable<?> actualCallable) {
        log.debug("这可能是一个被@Async标记的方法！继续校验看是否携带@Async注解");
        Field typeField = actualCallable.getClass().getDeclaredFields()[0];
        typeField.setAccessible(true);
        Field dataField = actualCallable.getClass().getDeclaredFields()[1];
        dataField.setAccessible(true);

        Object dataClass = null;
        try {
            dataClass = dataField.get(actualCallable);
            ReflectiveMethodInvocation clzObject = (ReflectiveMethodInvocation) dataClass;
            Method invokedMethod = clzObject.getMethod();
            Async asyncAnnotation = invokedMethod.getDeclaredAnnotation(Async.class);
            if (null == asyncAnnotation) {
                log.debug("这不是一个@Async标记的方法，跳过");
                log.warn(MATCH_ERROR, actualCallable.getClass().getSimpleName(), JSONObject.toJSONString(actualCallable));
                return;
            }
            log.debug("开始处理数据");
            List<RejectFreeDataParametersDTO> parametersDTOList = new ArrayList<>();
            Parameter[] declaredParameters = invokedMethod.getParameters();
            Object[] args = clzObject.getArguments();
            //将方法参数等数据装进参数实体列表中
            for (int i = 0; i < declaredParameters.length; i++) {
                Parameter parameter = declaredParameters[i];
                Class<?> type = parameter.getType();
                if (type.isAssignableFrom(args[i].getClass())) {
                    RejectFreeDataParametersDTO paramDTO = new RejectFreeDataParametersDTO();
                    paramDTO.setParamName(parameter.getName());
                    paramDTO.setParamType(type);
                    paramDTO.setParamValue(args[i]);
                    parametersDTOList.add(paramDTO);
                }
            }
            RejectFreeDataDTO logInfo = new RejectFreeDataDTO();
            logInfo.setMethodName(invokedMethod.getName());
            logInfo.setMethodReturnType(invokedMethod.getReturnType());
            logInfo.setFailedReson(FAILED_REASON);
            logInfo.setData(parametersDTOList);
            log.warn(MATCH_SUCCEED, invokedMethod.getDeclaringClass() + "." + invokedMethod.getName(), JSONObject.toJSONString(logInfo));
        } catch (Exception e) {
            log.error("出现了奇怪的错误" + e.getMessage(), e);
        }
    }
}
