package com.wwstation.warehouse.mapper;

import com.wwstation.warehouse.model.po.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author william
 * @since 2021-11-24
 */
public interface InventoryMapper extends BaseMapper<Inventory> {

    void preReduceInventory(@Param("num") Integer num,
                            @Param("id") Integer id);

    void realReduceInventory(@Param("num") Integer num,
                             @Param("id") Integer id);

    void rollBackFromSold(@Param("num") Integer num,
                          @Param("id") Integer id);

    void rollBackFromTimeout(@Param("num") Integer num,
                             @Param("id") Integer id);
}
