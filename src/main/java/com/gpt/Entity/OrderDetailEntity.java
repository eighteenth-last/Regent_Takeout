package com.gpt.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11  11:01
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_detail")
@Accessors(chain = true)
@Schema(name="OrderDetail对象", description="订单明细表")
public class OrderDetailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "名字")
    private String name;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "订单id")
    private Long orderId;

    @Schema(description = "菜品id")
    private Long dishId;

    @Schema(description = "套餐id")
    private Long setmealId;

    @Schema(description = "口味")
    private String dishFlavor;

    @Schema(description = "数量")
    private Integer number;

    @Schema(description = "金额")
    private BigDecimal amount;


}
