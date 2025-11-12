package com.gpt.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @CreateTime: 2025-10-11  11:01
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
@Schema(name="Orders对象", description="订单表")
public class OrdersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "订单号")
    private String number;

    @Schema(description = "订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
    private Integer status;

    @Schema(description = "下单用户")
    private Long userId;

    @Schema(description = "地址id")
    private Long addressBookId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "下单时间")
    private Date orderTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "结账时间")
    private Date checkoutTime;

    @Schema(description = "支付方式 1微信,2支付宝")
    private Integer payMethod;

    @Schema(description = "实收金额")
    private BigDecimal amount;

    @Schema(description = "备注")
    private String remark;

    private String phone;

    private String address;

    private String userName;

    private String consignee;
}
