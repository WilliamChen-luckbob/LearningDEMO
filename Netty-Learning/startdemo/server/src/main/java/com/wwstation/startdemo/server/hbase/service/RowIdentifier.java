package com.wwstation.startdemo.server.hbase.service;

import org.apache.hadoop.hbase.client.Get;

/**
 * @author william
 * @description
 * @Date: 2021-05-19 16:22
 */
public interface RowIdentifier<T> {

    Get getConditions(T t, String rowName) throws Exception;

    String getTableName() throws Exception;
}
