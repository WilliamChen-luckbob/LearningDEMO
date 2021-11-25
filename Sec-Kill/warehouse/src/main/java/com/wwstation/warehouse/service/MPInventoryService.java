package com.wwstation.warehouse.service;

import com.wwstation.warehouse.model.po.Inventory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author william
 * @since 2021-11-24
 */
public interface MPInventoryService extends IService<Inventory> {

    void preReduceInventory(Integer num, Integer id);

    void realReduceInventory(Integer num, Integer id);

    void rollBackFromSold(Integer num, Integer id);

    void rollBackFromTimeout(Integer num, Integer id);
}
