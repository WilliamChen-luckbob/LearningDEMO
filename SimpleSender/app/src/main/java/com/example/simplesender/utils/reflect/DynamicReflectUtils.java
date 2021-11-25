package com.infiai.webcommon.utils.reflect;

import com.google.common.base.CaseFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态反射工具包，用于处理一些常用的反射操作
 *
 * @author william
 * @description
 * @Date: 2021-08-25 10:58
 */
@Slf4j
public class DynamicReflectUtils {
    /**
     * RAW USE
     * <br>获取当前目标类及其超类中所有被目标注解标记的field，只抓取指定枚举的field，若为空，默认全抓
     *
     * @param targetClass   目标类
     * @param tagAnnotation 标记注解
     * @param specifiedEnum 标记注解的指定元素
     * @param <T>           必须是动态属性扫描器注解
     * @return
     */
    public static <T extends DynamicFieldScan> List<Field> getTaggedFieldsByAnnotation(Class<?> targetClass,
                                                                                       Class<T> tagAnnotation,
                                                                                       @Nullable DynamicFieldScanTypeEnum specifiedEnum) {
        if (specifiedEnum == null) {
            return getTaggedFieldsByAnnotation(targetClass, tagAnnotation);
        }

        List<Field> fieldList = new ArrayList<>();
        //已经遍历到Object时，直接返回空列表
        if (targetClass == Object.class) {
            return fieldList;
        }
        //开始对当前类的所有field进行遍历
        List<Field> currentClassMatched = Arrays.stream(targetClass.getDeclaredFields())
                .filter(e -> {
                    DynamicFieldScan annotation = e.getDeclaredAnnotation(tagAnnotation);
                    if (annotation != null && annotation.value().equals(specifiedEnum)) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
        fieldList.addAll(currentClassMatched);
        if (targetClass.getSuperclass() != null) {
            fieldList.addAll(getTaggedFieldsByAnnotation(targetClass.getSuperclass(), tagAnnotation, specifiedEnum));
        }
        return fieldList;
    }

    /**
     * RAW USE
     * <br>获取当前目标类及其超类中所有被目标注解标记的field,默认只要标记就抓取
     *
     * @param targetClass   目标类
     * @param tagAnnotation 标记注解
     * @param <T>
     * @return
     */
    public static <T extends Annotation> List<Field> getTaggedFieldsByAnnotation(Class<?> targetClass,
                                                                                 Class<T> tagAnnotation) {
        List<Field> fieldList = new ArrayList<>();
        //已经遍历到Object时，直接返回空列表
        if (targetClass == Object.class) {
            return fieldList;
        }
        //开始对当前类的所有field进行遍历
        List<Field> currentClassMatched = Arrays.stream(targetClass.getDeclaredFields())
                .filter(e -> {
                    Annotation annotation = e.getDeclaredAnnotation(tagAnnotation);
                    if (annotation != null) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
        fieldList.addAll(currentClassMatched);
        if (targetClass.getSuperclass() != null) {
            fieldList.addAll(getTaggedFieldsByAnnotation(targetClass.getSuperclass(), tagAnnotation));
        }
        return fieldList;
    }

    /**
     * RAW USE
     * <br>获取目标类中的指定属性对应的get set方法
     *
     * @param targetClass 目标类
     * @param fields      属性
     * @param from        属性的原有名称格式，默认小写驼峰
     * @param to          指定输出中属性名的格式
     * @return 属性名指定格式对应的getset方法的map
     */
    public static Map<String, MethodPack> getMethodsByFields(Class<?> targetClass, List<Field> fields, @Nullable CaseFormat from, @Nullable CaseFormat to) {
        if (from == null) {
            from = CaseFormat.LOWER_CAMEL;
        }

        Boolean translateName = from != null && to != null;
        Boolean failed = false;
        Map<String, MethodPack> result = new HashMap<>(8);
        for (Field field : fields) {
            String name = field.getName();
            Class<?> type = field.getType();
            Method getMethod = null;
            Method setMethod = null;
            try {
                getMethod = targetClass.getMethod("get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, name));
                setMethod = targetClass.getMethod("set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, name), type);
            } catch (NoSuchMethodException e) {
                log.error(e.getMessage(), e);
                failed = true;
            }
            if (result.containsKey(name)) {
                log.warn("field中出现了同名的fieldName={}，自动覆盖，请检查类的继承关系以免产生异常！", name);
            }
            //是否需要转字段名称格式
            name = translateName ? from.to(to, name) : name;
            result.put(name, new MethodPack(new GetMethodPack(type, getMethod), new SetMethodPack(type, getMethod)));
        }

        return failed ? new HashMap<>() : result;
    }

    /**
     * 根据注解获取类中被其标记的所有field对应的getset方法
     *
     * @param targetClass 目标类
     * @param annotation  标记注解
     * @param <T>
     * @return
     */
    public static <T extends Annotation> Map<String, MethodPack> getMethodByAnnotation(Class<?> targetClass, Class<T> annotation) {
        return getMethodByAnnotation(targetClass, annotation, null, null);
    }

    /**
     * 根据注解获取类中被其标记的所有field对应的getset方法
     *
     * @param targetClass 目标类
     * @param annotation  标记注解
     * @param from        原有属性格式，不填默认为小写驼峰
     * @param to          指定响应属性格式
     * @param <T>
     * @return
     */
    public static <T extends Annotation> Map<String, MethodPack> getMethodByAnnotation(Class<?> targetClass, Class<T> annotation, CaseFormat from, CaseFormat to) {
        return getMethodsByFields(targetClass, getTaggedFieldsByAnnotation(targetClass, annotation), from, to);
    }

    /**
     * 根据注解获取类中被其标记的所有field对应的getset方法
     *
     * @param targetClass   目标类
     * @param annotation    标记注解
     * @param specifiedEnum 指定的枚举
     * @param <T>
     * @return
     */
    public static <T extends DynamicFieldScan> Map<String, MethodPack> getMethodByAnnotation(Class<?> targetClass,
                                                                                             Class<T> annotation,
                                                                                             @Nullable DynamicFieldScanTypeEnum specifiedEnum) {
        return getMethodByAnnotation(targetClass, annotation, specifiedEnum, null, null);
    }

    /**
     * 根据注解获取类中被其标记的所有field对应的getset方法
     *
     * @param targetClass   目标类
     * @param annotation    标记注解
     * @param specifiedEnum 指定的枚举
     * @param from          原有属性格式，不填默认为小写驼峰
     * @param to            指定响应属性格式
     * @param <T>
     * @return
     */
    public static <T extends DynamicFieldScan> Map<String, MethodPack> getMethodByAnnotation(Class<?> targetClass,
                                                                                             Class<T> annotation,
                                                                                             @Nullable DynamicFieldScanTypeEnum specifiedEnum,
                                                                                             CaseFormat from,
                                                                                             CaseFormat to) {
        return getMethodsByFields(targetClass, getTaggedFieldsByAnnotation(targetClass, annotation, specifiedEnum), from, to);
    }


    /**
     * 反射获取指定类型的对象
     *
     * @param targetDTO 被抓取的实体对象
     * @param getMethod get方法
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends Object> T getValue(Object targetDTO, GetMethodPack<T> getMethod) throws Exception {
        Object invoke = getMethod.getMethod().invoke(targetDTO);
        if (invoke == null) {
            return null;
        }
        if (getMethod.getType().isAssignableFrom(invoke.getClass())) {
            return (T) invoke;
        }
        log.error("类型不匹配，无法转换！");
        return null;
    }

    @Data
    @AllArgsConstructor
    public static class MethodPack<T extends Object> {
        private GetMethodPack<T> getMethod;
        private SetMethodPack<T> setMethod;
    }

    @Data
    @AllArgsConstructor
    public static class GetMethodPack<T extends Object> {
        private Class<T> type;
        private Method method;
    }

    @Data
    @AllArgsConstructor
    public static class SetMethodPack<T extends Object> {
        private Class<T> type;
        private Method method;
    }
}
