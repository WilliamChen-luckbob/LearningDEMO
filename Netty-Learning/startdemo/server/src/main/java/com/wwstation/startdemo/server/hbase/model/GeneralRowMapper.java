package com.wwstation.startdemo.server.hbase.model;

import com.google.common.base.CaseFormat;
import com.wwstation.startdemo.server.hbase.service.RowIdentifier;
import com.wwstation.startdemo.server.hbase.service.RowMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author william
 * @description
 * @Date: 2021-05-19 15:29
 */
public class GeneralRowMapper<T> implements RowMapper<T>, RowIdentifier<T> {
    private Class<T> clz;

    public GeneralRowMapper(Class<T> clz) {
        this.clz = clz;
    }

    @Override
    public T mapRow(Result result, int rowNum) throws Exception {
        T t = clz.newInstance();

        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            HColumnFamily family = field.getAnnotation(HColumnFamily.class);
            if (family == null) {
                //如果没有标记注解，将不进行匹配
                //log.info()
                continue;
            }
            Class<?> type = field.getType();

            //校验参数类型
            Assert.isTrue(type.isAssignableFrom(String.class) ||
                    type.isAssignableFrom(Integer.class) ||
                    type.isAssignableFrom(Long.class),
                "实体参数必须为integer，string或long类型");

            String name = field.getName();
            Method method = clz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), type);
            method.invoke(t, getValue(family.value(), type, result, name));
        }

        return t;
    }

    private <E> E getValue(String familyName, Class<E> clz, Result result, String colName) {
        if (clz.isAssignableFrom(Long.class)) {
            return (E) getLong(familyName, result, colName);
        } else if (clz.isAssignableFrom(Integer.class)) {
            return (E) getInteger(familyName, result, colName);
        } else if (clz.isAssignableFrom(String.class)) {
            return (E) getString(familyName, result, colName);
        } else {
            return null;
        }
    }

    private Long getLong(String familyName, Result result, String colName) {
        return Bytes.toLong(result.getValue(familyName.getBytes(), colName.getBytes()));
    }

    private Integer getInteger(String familyName, Result result, String colName) {
        return Bytes.toInt(result.getValue(familyName.getBytes(), colName.getBytes()));
    }

    private String getString(String familyName, Result result, String colName) {
        return Bytes.toString(result.getValue(familyName.getBytes(), colName.getBytes()));
    }

    @Override
    public Get getConditions(T t, String rowName) throws Exception {
        Assert.notNull(this.clz, "通用mapper可能尚未初始化！");
        Assert.isTrue(t.getClass().isAssignableFrom(clz),
            String.format("入参类型不匹配！expected %s but found %s", clz.getName(), t.getClass().getName()));

        return new Get(rowName.getBytes());
        //todo 暂时不考虑用其他条件查询的问题
//        Field[] fields = clz.getDeclaredFields();
//        for (Field field : fields) {
//            HColumnFamily columnFamily = field.getDeclaredAnnotation(HColumnFamily.class);
//            if (columnFamily ==null){
//                continue;
//            }
//            String name = field.getName();
//            Method method = clz.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
//            Object value = method.invoke(t);
//            if (value!=null){
//                get.addColumn(columnFamily.value().getBytes(),name.getBytes()).set;
//            }
//        }


    }

    @Override
    public String getTableName() throws Exception {
        Assert.notNull(this.clz, "通用mapper可能尚未初始化！");

        HTableName tableNameAnnotation = clz.getDeclaredAnnotation(HTableName.class);
        if (tableNameAnnotation != null && StringUtils.isNotEmpty(tableNameAnnotation.value())) {
            return tableNameAnnotation.value();
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clz.getSimpleName());
    }
}
