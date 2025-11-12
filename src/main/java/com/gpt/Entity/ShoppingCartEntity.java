package com.gpt.Entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11  11:01
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shopping_cart")
@Schema(name="ShoppingCart对象", description="购物车")
public class ShoppingCartEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "主键")
    private Long userId;

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

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createTime;
}

