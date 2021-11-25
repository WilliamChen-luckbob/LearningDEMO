package com.infiai.webcommon.utils.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类动态扫描标记注解
 * <br> 在反射获取field时，有时需要递归至父类，并且遍历无关的field
 * <br> 此注解的作用为在递归时方便获取此类及所有超类中所有需要用到的field
 * <br> 经典用法见web-inventory中对入参{@link com.infiai.webinventory.model.dto.Search4InventoryFormDTO}Search4InventoryFormDTO中field的动态获取并装配查询条件
 *
 * @author william
 * @description
 * @Date: 2021-08-25 10:51
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicFieldScan {
    /**
     * 用于标记同一个继承关系中不同的动态扫描类型，默认为空
     * @return
     */
    DynamicFieldScanTypeEnum value() default DynamicFieldScanTypeEnum.DEFAULT;
}
