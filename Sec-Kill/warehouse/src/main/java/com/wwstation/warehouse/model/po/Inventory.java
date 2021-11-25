package com.wwstation.warehouse.model.po;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author william
 * @since 2021-11-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @ApiModel(value="Inventory对象", description="")
public class Inventory extends Model<Inventory> {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @ApiModelProperty(value = "商品名称")
      private String name;

      @ApiModelProperty(value = "总库存")
      private Integer qtyTotal;

      @ApiModelProperty(value = "锁定库存")
      private Integer qtyLock;

      @ApiModelProperty(value = "可用库存")
      private Integer qtyAvailable;

      @ApiModelProperty(value = "不可用（异常）库存")
      private Integer qtyInvailable;


    @Override
    protected Serializable pkVal() {
          return this.id;
      }

}
