package com.gpt.Entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-08  16:42
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dish_flavor")
@Schema(name="DishFlavor对象", description="菜品口味关系表")
public class DishFlavorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "菜品")
    private Long dishId;

    @Schema(description = "口味名称")
    private String name;

    @Schema(description = "口味数据list")
    private String value;

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
