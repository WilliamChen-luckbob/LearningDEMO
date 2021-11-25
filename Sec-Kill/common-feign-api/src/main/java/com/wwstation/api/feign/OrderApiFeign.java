package com.wwstation.api.feign;

import com.wwstation.common.model.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 17:28
 */
@FeignClient("order")
public interface OrderApiFeign {
     @PostMapping("/seckill/order/connTest")
     Result connTest();
}
