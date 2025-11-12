package com.gpt.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpt.Entity.OrderDetailEntity;
import com.gpt.Mapper.OrderDetailMapper;
import com.gpt.Service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11  14:48
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailEntity> implements OrderDetailService {
}
