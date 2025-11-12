package com.gpt.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpt.Entity.OrdersEntity;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11  14:45
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

public interface OrdersService extends IService<OrdersEntity> {


    public void submit(OrdersEntity ordersEntity);
}
