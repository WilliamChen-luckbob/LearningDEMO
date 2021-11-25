package com.wwstation.warehouse.service.impl;

import com.wwstation.api.dubbo.WarehouseInventoryApiDubbo;
import com.wwstation.common.model.result.Result;
import com.wwstation.warehouse.service.MPInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 17:25
 */

@DubboService
@Slf4j
public class WarehouseInventoryApiDubboImpl implements WarehouseInventoryApiDubbo {
    @Autowired
    private MPInventoryService inventoryService;

    @Override
    public Result connTest() {
        String s = "warehouse service 收到了信息";
        log.info(s);
        return Result.succeed(s);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public Result preReduceInventory(Integer num, Integer id) {
        inventoryService.preReduceInventory(num, id);
        return Result.succeed();
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public Result realReduceInventory(Integer num, Integer id) {
        inventoryService.realReduceInventory(num, id);
        return Result.succeed();
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public Result rollBackFromSold(Integer num, Integer id) {
        inventoryService.rollBackFromSold(num, id);
        return Result.succeed();
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public Result rollBackFromTimeout(Integer num, Integer id) {
        inventoryService.rollBackFromTimeout(num, id);
        return Result.succeed();
    }
}
