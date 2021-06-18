package com.wwstation.startdemo.server.hbase.model;

import lombok.Data;

/**
 * @author william
 * @description
 * @Date: 2021-05-18 17:01
 */
@Data
public class WilliamTest {
    @HColumnFamily(value = "family1")
    private String name;
    @HColumnFamily(value = "family1")
    private String code;
    @HColumnFamily(value = "family1")
    private String comment;
    @HColumnFamily(value = "family1")
    private String address;
}
