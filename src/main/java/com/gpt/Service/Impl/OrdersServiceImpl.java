package com.gpt.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpt.Common.BaseContextCommon;
import com.gpt.Common.CustomException;
import com.gpt.Entity.*;
import com.gpt.Mapper.OrdersMapper;
import com.gpt.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11  14:45
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, OrdersEntity> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UseService useService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    // 实现用户下单的逻辑
    @Transactional
    public void submit(OrdersEntity ordersEntity) {
        // 获取当前用户的id
        long userId = BaseContextCommon.getCurrentUserId();

        // 查询当前用户的购物车
        LambdaQueryWrapper<ShoppingCartEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCartEntity::getUserId, userId);
        List<ShoppingCartEntity> shoppingCartEntityList = shoppingCartService.list(queryWrapper);

        if(shoppingCartEntityList.isEmpty()){
            throw new CustomException("购物车为空");
        }

        // 查用户信息表
        UserEntity user = useService.getById(userId);
        if (user == null) {
            throw new CustomException("用户信息不存在，请重新登录");
        }

        // 查地址表
        Long addressBookId = ordersEntity.getAddressBookId();
        AddressBookEntity addressBookEntity = addressBookService.getById(addressBookId);
        if (addressBookEntity == null) {
            throw new CustomException("地址信息有误，请选择收货地址");
        }

        // 生成订单号
        long orderId = IdWorker.getId();

        // 计算总金额
        AtomicInteger amount=new AtomicInteger(0);
        List<OrderDetailEntity> orderDetailsails =shoppingCartEntityList.stream().map((item)->{
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            orderDetailEntity.setOrderId(orderId);
            orderDetailEntity.setNumber(item.getNumber());
            orderDetailEntity.setDishFlavor(item.getDishFlavor());
            orderDetailEntity.setDishId(item.getDishId());
            orderDetailEntity.setSetmealId(item.getSetmealId());
            orderDetailEntity.setName(item.getName());
            orderDetailEntity.setImage(item.getImage());
            orderDetailEntity.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            // addAndGet 累加单份金额
            return orderDetailEntity;
        }).collect(Collectors.toList());

        // 向订单表插入一条数据
        ordersEntity.setId(orderId);
        ordersEntity.setOrderTime(new Date());
        ordersEntity.setCheckoutTime(new Date());
        ordersEntity.setStatus(2);
        ordersEntity.setAmount(new BigDecimal(amount.get()));//总金额
        ordersEntity.setUserId(userId);
        ordersEntity.setNumber(String.valueOf(orderId));
        ordersEntity.setUserName(user.getName());
        ordersEntity.setConsignee(addressBookEntity.getConsignee());
        ordersEntity.setPhone(addressBookEntity.getPhone());
        ordersEntity.setAddress((addressBookEntity.getProvinceName() == null ? "" : addressBookEntity.getProvinceName())
                + (addressBookEntity.getCityName() == null ? "" : addressBookEntity.getCityName())
                + (addressBookEntity.getDistrictName() == null ? "" : addressBookEntity.getDistrictName())
                + (addressBookEntity.getDetail() == null ? "" : addressBookEntity.getDetail()));

        this.save(ordersEntity);
        // 向订单明细表插入一条或多条数据
        orderDetailService.saveBatch(orderDetailsails);

        // 清空购物车的数据
        shoppingCartService.remove(queryWrapper);
    }
}
