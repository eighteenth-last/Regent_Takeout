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
 * 地址簿
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("address_book")
@Schema(name="AddressBook对象", description="地址管理")
public class AddressBookEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "收货人")
    private String consignee;

    @Schema(description = "性别 0 女 1 男")
    private Integer sex;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "省级区划编号")
    private String provinceCode;

    @Schema(description = "省级名称")
    private String provinceName;

    @Schema(description = "市级区划编号")
    private String cityCode;

    @Schema(description = "市级名称")
    private String cityName;

    @Schema(description = "区级区划编号")
    private String districtCode;

    @Schema(description = "区级名称")
    private String districtName;

    @Schema(description = "详细地址")
    private String detail;

    @Schema(description = "标签")
    private String label;

    @Schema(description = "默认 0 否 1是")
    private int isDefault;

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
