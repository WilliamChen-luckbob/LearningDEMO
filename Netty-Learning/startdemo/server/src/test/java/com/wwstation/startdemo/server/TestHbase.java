package com.wwstation.startdemo.server;

import com.wwstation.startdemo.server.hbase.service.CRUDService;
import com.wwstation.startdemo.server.hbase.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author william
 * @description
 * @Date: 2021-05-18 17:11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHbase {
    @Autowired
    private TestService testService;
    @Autowired
    private CRUDService crudService;

    @Test
    public void testCreate() throws Exception {
        crudService.createTable();
    }

    @Test
    public void testDelete() throws Exception{
        crudService.deleteTable();
    }

    @Test
    public void testInsertData() throws Exception{
        crudService.insertData();
    }

    @Test
    public void testModify() throws Exception{
        crudService.modify();
    }

    @Test
    public void testGetData() throws Exception{
        crudService.getData();
    }

    @Test
    public void testGetListData() throws Exception{
        crudService.getListData();
    }

    @Test
    public void testGetDynamicData() throws Exception{
        crudService.getDynamicData();
    }

    @Test
    public void testInit() throws IOException {
        testService.init();
    }
}
