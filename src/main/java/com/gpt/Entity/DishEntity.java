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
 * @CreateTime: 2025-10-04  13:32
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dish")
@Schema(name="Dish对象", description="菜品管理")
public class DishEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "菜品名称")
    private String name;

    @Schema(description = "菜品分类id")
    private Long categoryId;

    @Schema(description = "菜品价格")
    private BigDecimal price;

    @Schema(description = "商品码")
    private String code;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "描述信息")
    private String description;

    @Schema(description = "0 停售 1 起售")
    private Integer status;

    @Schema(description = "顺序")
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
