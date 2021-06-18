package com.wwstation.startdemo.server.component;

import lombok.Data;

import java.util.List;
import java.util.Properties;

/**
 * @author william
 * @description
 * @Date: 2021-05-15 16:56
 */
@Data
public class Constants {
    //获取系统运行时数据
public static final String TEST_NAME=System.getProperty("os.name");
public static final Properties propertyList=System.getProperties();
}
