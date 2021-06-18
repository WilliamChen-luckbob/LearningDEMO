package com.wwstation.startdemo.server.hbase.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.wwstation.startdemo.server.hbase.model.GeneralRowMapper;
import com.wwstation.startdemo.server.hbase.model.WilliamTest;
import com.wwstation.startdemo.server.hbase.model.WilliamTestRowMapper;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author william
 * @description
 * @Date: 2021-05-19 09:58
 */
@Service
public class CRUDService {
    @Autowired
    private HbaseTemplate hbaseTemplate;

    public void createTable() throws Exception {
        Admin admin = hbaseTemplate.getConnection().getAdmin();
        TableName tableName = TableName.valueOf("william_test");
        TableDescriptorBuilder desc = TableDescriptorBuilder.newBuilder(tableName);
        desc.setColumnFamily(new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor("family1".getBytes()));
        desc.setColumnFamily(new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor("family2".getBytes()));

        admin.createTable(desc.build());
    }

    public void deleteTable() throws Exception {
        Admin admin = hbaseTemplate.getConnection().getAdmin();
        TableName tableName = TableName.valueOf("william_test");
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
    }

    public void insertData() throws Exception {
        List<Mutation> puts = new ArrayList<>();

        Put put1 = new Put("3".getBytes());
        put1.addColumn("family1".getBytes(), "name".getBytes(), "william3".getBytes());
        put1.addColumn("family1".getBytes(), "code".getBytes(), "this is a code3".getBytes());
        put1.addColumn("family1".getBytes(), "comment".getBytes(), "this is a comment3".getBytes());
        put1.addColumn("family1".getBytes(), "address".getBytes(), "this is an address3".getBytes());


        Put put2 = new Put("4".getBytes());
        put2.addColumn("family1".getBytes(), "name".getBytes(), "william 4".getBytes());
        put2.addColumn("family1".getBytes(), "code".getBytes(), "this is a code 4".getBytes());
        put2.addColumn("family1".getBytes(), "comment".getBytes(), "this is a comment 4".getBytes());
        put2.addColumn("family1".getBytes(), "address".getBytes(), "this is an address 4".getBytes());

        puts.add(put1);
        puts.add(put2);


        hbaseTemplate.saveOrUpdates("william_test", puts);

//
//        Table table = hbaseTemplate.getConnection().getTable(TableName.valueOf("william_test"));
//
//        System.out.println(table.getDescriptor().getColumnFamilies().toString());
//
//        table.put(put1);
//        Result result = table.get(new Get("1".getBytes()));
    }

    public void modify() throws Exception {
        Put put1 = new Put("3".getBytes());
        put1.addColumn("family1".getBytes(), "name".getBytes(), "modified william3".getBytes());
        put1.addColumn("family1".getBytes(), "code".getBytes(), "modified code3".getBytes());
        put1.addColumn("family1".getBytes(), "comment".getBytes(), "modified comment3".getBytes());
        put1.addColumn("family1".getBytes(), "address".getBytes(), "modified address3".getBytes());
        hbaseTemplate.saveOrUpdate("william_test", put1);
    }

    public void getListData() throws Exception {
        Get get = new Get("3".getBytes());

        List<WilliamTest> william_test = hbaseTemplate.find("william_test", new Scan(get), new WilliamTestRowMapper());
        william_test.forEach(e -> System.out.println(e + "\n"));
    }

    public void getData() throws Exception {
        WilliamTest williamTest = hbaseTemplate.get("william_test", "3", new WilliamTestRowMapper());
        System.out.println(JSONObject.toJSONString(williamTest));
    }

    public void getDynamicData() throws Exception {
        WilliamTest williamTest = hbaseTemplate.get(new WilliamTest(), "3");
        System.out.println(JSONObject.toJSONString(williamTest));
    }
}
