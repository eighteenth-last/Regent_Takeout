package com.gpt.Entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-01-01  00:00
 * @BelongsProject: Regent_Takeout
 * @Description: 套餐菜品关系实体类
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("setmeal_dish")
@Schema(name="SetmealDish对象", description="套餐菜品关系")
public class SetmealDishEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "套餐id")
    private String setmealId;

    @Schema(description = "菜品id")
    private String dishId;

    @Schema(description = "菜品名称（冗余字段）")
    private String name;

    @Schema(description = "菜品原价（冗余字段）")
    private BigDecimal price;

    @Schema(description = "份数")
    private Integer copies;

    @Schema(description = "排序")
    private Integer sort;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人")
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改人")
    private Long updateUser;

    @Schema(description = "是否删除")
    private Integer isDeleted;
}