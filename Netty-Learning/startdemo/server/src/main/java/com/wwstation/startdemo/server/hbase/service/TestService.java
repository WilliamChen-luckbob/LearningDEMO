package com.wwstation.startdemo.server.hbase.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author william
 * @description
 * @Date: 2021-05-18 17:02
 */
@Service
@Slf4j
public class TestService {

    public void init() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "hdp1.infiai.com");
        config.set("hbase.zookeeper.property.clientPort", "2181");
//        config.set("hadoop.home.dir", "/hbase-data");
//        config.set("zookeeper.znode.parent","");
        config.set("hbase.master", "hdp1.infiai.com"); // master info
        config.set("hbase.master.port", "16000");

        HBaseAdmin.available(config);


        TableName table = TableName.valueOf("william_test");
        String family1 = "Family1";
        String family2 = "Family2";

        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();

//        createTable(table, family1, family2, admin);
        admin.disableTable(table);
        admin.deleteTable(table);

    }

    private void createTable(TableName table, String family1, String family2, Admin admin) throws IOException {
        HTableDescriptor desc = new HTableDescriptor(table);
        desc.addFamily(new HColumnDescriptor(family1));
        desc.addFamily(new HColumnDescriptor(family2));
        admin.createTable(desc);
    }
}
