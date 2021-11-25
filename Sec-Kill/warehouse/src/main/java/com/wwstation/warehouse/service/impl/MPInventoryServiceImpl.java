package com.wwstation.warehouse.service.impl;

import com.wwstation.warehouse.model.po.Inventory;
import com.wwstation.warehouse.mapper.InventoryMapper;
import com.wwstation.warehouse.service.MPInventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author william
 * @since 2021-11-24
 */
@Service
public class MPInventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements MPInventoryService {

    @Override
    public void preReduceInventory(Integer num,Integer id) {
        baseMapper.preReduceInventory(num,id);
    }

    @Override
    public void realReduceInventory(Integer num,Integer id) {
        baseMapper.realReduceInventory(num,id);
    }

    @Override
    public void rollBackFromSold(Integer num,Integer id) {
        baseMapper.rollBackFromSold(num,id);
    }

    @Override
    public void rollBackFromTimeout(Integer num,Integer id) {
        baseMapper.rollBackFromTimeout(num,id);
    }
}
