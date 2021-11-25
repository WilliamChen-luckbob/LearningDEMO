package com.wwstation.api.dubbo;

import com.wwstation.common.model.result.Result;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 17:25
 */
public interface WarehouseInventoryApiDubbo {
    Result connTest();

    /**
     * 预扣库
     *
     * @param num 数量
     * @param id  库存ID
     * @return
     */
    Result preReduceInventory(Integer num, Integer id);

    /**
     * @param num 数量
     * @param id  库存ID
     * @return
     */
    Result realReduceInventory(Integer num, Integer id);

    /**
     * @param num 数量
     * @param id  库存ID
     * @return
     */
    Result rollBackFromSold(Integer num, Integer id);

    /**
     * 由于超时而退货回库存
     *
     * @param num 数量
     * @param id  库存ID
     * @return
     */
    Result rollBackFromTimeout(Integer num, Integer id);
}
