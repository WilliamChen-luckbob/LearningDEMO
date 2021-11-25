package com.wwstation.seckill.service.impl;

import com.wwstation.api.dubbo.OrderApiDubbo;
import com.wwstation.common.model.result.Result;
import com.wwstation.seckill.service.SecKillService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 17:49
 */
@Service
public class SecKillServiceImpl implements SecKillService {
    @DubboReference()
    private OrderApiDubbo orderApiDubbo;

    @Override
    public Result connTestByDubbo() {
        return orderApiDubbo.connTest();
    }
}
