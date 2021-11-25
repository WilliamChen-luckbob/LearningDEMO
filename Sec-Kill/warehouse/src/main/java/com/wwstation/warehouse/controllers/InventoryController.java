package com.wwstation.warehouse.controllers;

import com.wwstation.api.dubbo.WarehouseInventoryApiDubbo;
import com.wwstation.common.model.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 10:39
 */
@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {
    @DubboReference
    private WarehouseInventoryApiDubbo warehouseService;

    @PostMapping("/connTest")
    public Result testConn() {
        return Result.succeed("成功");
    }

    /**
     * 预扣库
     *
     * @param num
     * @return
     */
    @PostMapping("/reduce/pre")
    public Result<String> preReduceInventory(@RequestParam(name = "num") Integer num) throws Exception{
        warehouseService.preReduceInventory(num,1);
        log.info("成功预扣库{}个", num);
        return Result.succeed();
    }

    /**
     * 实际扣库
     *
     * @param num
     * @return
     */
    @PostMapping("/reduce")
    public Result<String> reduceInventory(@RequestBody Integer num) throws Exception{
        warehouseService.realReduceInventory(num,1);
        log.info("成功扣减库存{}个", num);
        return Result.succeed();
    }
}
