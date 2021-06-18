package com.wwstation.service1;

import com.wwstation.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author william
 * @description
 * @Date: 2021-06-18 15:20
 */
@FeignClient("service2")
public interface API {
    @GetMapping("/test/conn")
    CommonResult<String> conn();
}
