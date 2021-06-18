package com.wwstation.startdemo.server.hbase.model;

import org.springframework.context.annotation.Primary;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记字段属于哪个ColumnFamily
 *
 * @author william
 * @description
 * @Date: 2021-05-19 15:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface HColumnFamily {
    String value() default "column_family";
}
