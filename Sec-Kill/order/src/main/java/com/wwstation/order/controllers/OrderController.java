package com.wwstation.order.controllers;


import com.wwstation.common.model.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 10:21
 */
@RestController
@RequestMapping("/seckill/order")
public class OrderController {
    /**
     * 下单
     *
     * @return
     */
    @PostMapping()
    public Result<String> takeOder() {
        return Result.succeed();
    }
}
