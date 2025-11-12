package com.gpt.Dto;

import com.gpt.Entity.OrderDetailEntity;
import com.gpt.Entity.OrdersEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11
 * @BelongsProject: Regent_Takeout
 * @Description: 订单Dto，包含订单详情
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrdersDto extends OrdersEntity {
    
    // 订单详情列表
    private List<OrderDetailEntity> orderDetails;
}

