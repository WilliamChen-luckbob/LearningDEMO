package com.wwstation.order.service.impl;

import com.wwstation.api.dubbo.OrderApiDubbo;
import com.wwstation.api.dubbo.WarehouseInventoryApiDubbo;
import com.wwstation.common.model.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 17:24
 */
@DubboService
@Slf4j
public class OrderApiDubboImpl implements OrderApiDubbo {
    @DubboReference
    private WarehouseInventoryApiDubbo warehouseInventoryApiDubbo;
    @Override
    public Result connTest() {
        String s = "order service 收到了信息，正在转发到warehouse service";
        log.info(s);
        return warehouseInventoryApiDubbo.connTest();
    }
}
