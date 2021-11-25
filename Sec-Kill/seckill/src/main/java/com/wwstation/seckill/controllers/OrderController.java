package com.wwstation.seckill.controllers;


import com.wwstation.common.model.result.Result;
import com.wwstation.seckill.service.SecKillService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 10:50
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    @Resource
    SecKillService secKillService;

    @PostMapping("/connTestWithDubbo")
    public Result connTestWithDubbo() {
        return secKillService.connTestByDubbo();
    }

    @PostMapping("/inventory/acquire")
    public Result<String> tryAcquireInventory() {
        return Result.succeed();
    }
}
