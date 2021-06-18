package com.wwstation.startdemo.server.hbase.model;

import com.wwstation.startdemo.server.hbase.service.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

/**
 * @author william
 * @description
 * @Date: 2021-05-19 09:30
 */
@Service
public class WilliamTestRowMapper implements RowMapper<WilliamTest> {
    private static String COLUMNFAMILY = "family1";
    private static String NAME = "name";
    private static String CODE = "code";
    private static String COMMENT = "comment";
    private static String ADDRESS = "address";


    @Override
    public WilliamTest mapRow(Result result, int rowNum) throws Exception {
        WilliamTest dto = new WilliamTest();
        // TODO: 设置相关的属性值
        dto.setName(getString(result, NAME));
        dto.setCode(getString(result, CODE));
        dto.setAddress(getString(result, ADDRESS));
        dto.setComment(getString(result, COMMENT));
        return dto;
    }

    private int getInteger(Result result, String colName) {
        return Bytes.toInt(result.getValue(COLUMNFAMILY.getBytes(), colName.getBytes()));
    }

    private String getString(Result result, String colName) {
        return Bytes.toString(result.getValue(COLUMNFAMILY.getBytes(), colName.getBytes()));
    }


}
