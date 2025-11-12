package com.gpt.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gpt.Common.BaseContextCommon;
import com.gpt.Common.R;
import com.gpt.Dto.OrdersDto;
import com.gpt.Entity.OrderDetailEntity;
import com.gpt.Entity.OrdersEntity;
import com.gpt.Entity.ShoppingCartEntity;
import com.gpt.Service.OrderDetailService;
import com.gpt.Service.OrdersService;
import com.gpt.Service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11  14:42
 * @BelongsProject: Regent_Takeout
 * @Description: 下单
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private OrderDetailService orderDetailService;
    
    @Autowired
    private ShoppingCartService shoppingCartService;

    // 跳转支付
    @PostMapping("/submit")
    public R<String> submit(@RequestBody OrdersEntity ordersEntity) {

        log.info("订单数据：{}", ordersEntity);
        ordersService.submit(ordersEntity);

        return R.success("下单成功");
    }

    // 历史订单的开发
    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize) {
        log.info("历史订单页{}，数据数量{}", page, pageSize);

        // 获取当前登录用户id
        Long userId = BaseContextCommon.getCurrentUserId();

        // 构造分页构造器
        Page<OrdersEntity> pageInfo = new Page<>(page, pageSize);

        // 构造条件构造器，查询当前用户的订单
        LambdaQueryWrapper<OrdersEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrdersEntity::getUserId, userId);
        queryWrapper.orderByDesc(OrdersEntity::getOrderTime);

        // 执行查询
        ordersService.page(pageInfo, queryWrapper);

        // 创建 OrdersDto 的分页对象
        Page<OrdersDto> dtoPage = new Page<>();

        // 对象拷贝（除了records，其他属性都拷贝）
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        // 获取订单列表
        List<OrdersEntity> records = pageInfo.getRecords();

        // 遍历订单列表，为每个订单查询对应的订单详情
        List<OrdersDto> dtoList = records.stream().map(item -> {
            OrdersDto ordersDto = new OrdersDto();
            
            // 拷贝订单基本信息
            BeanUtils.copyProperties(item, ordersDto);
            
            // 查询订单详情
            LambdaQueryWrapper<OrderDetailEntity> detailWrapper = new LambdaQueryWrapper<>();
            detailWrapper.eq(OrderDetailEntity::getOrderId, item.getId());
            List<OrderDetailEntity> orderDetails = orderDetailService.list(detailWrapper);
            
            // 设置订单详情
            ordersDto.setOrderDetails(orderDetails);
            
            return ordersDto;
        }).collect(Collectors.toList());

        // 设置 records
        dtoPage.setRecords(dtoList);

        return R.success(dtoPage);
    }

    /**
     * 订单明细的分页查询（后台管理）
     * @param page 当前页
     * @param pageSize 每页数量
     * @param number 订单号（可选）
     * @param beginTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, String beginTime, String endTime
    ) {
        log.info("订单分页查询 - 页码:{}, 每页数量:{}, 订单号:{}, 开始时间:{}, 结束时间:{}", 
                 page, pageSize, number, beginTime, endTime);

        // 构造分页构造器
        Page<OrdersEntity> pageInfo = new Page<>(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<OrdersEntity> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加过滤条件：根据订单号模糊查询
        queryWrapper.like(number != null && !number.isEmpty(), OrdersEntity::getNumber, number);
        
        // 添加时间范围查询
        queryWrapper.ge(beginTime != null && !beginTime.isEmpty(), OrdersEntity::getOrderTime, beginTime);
        queryWrapper.le(endTime != null && !endTime.isEmpty(), OrdersEntity::getOrderTime, endTime);
        
        // 按下单时间倒序排列
        queryWrapper.orderByDesc(OrdersEntity::getOrderTime);

        // 执行查询
        ordersService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 更新订单状态（派送、完成）
     * @param ordersEntity 包含订单ID和状态
     */
    @PutMapping
    public R<String> updateStatus(@RequestBody OrdersEntity ordersEntity) {
        log.info("更新订单状态 - 订单ID:{}, 状态:{}", ordersEntity.getId(), ordersEntity.getStatus());
        
        if (ordersEntity.getId() == null) {
            return R.error("订单ID不能为空");
        }
        
        if (ordersEntity.getStatus() == null) {
            return R.error("订单状态不能为空");
        }
        
        // 查询订单是否存在
        OrdersEntity existOrder = ordersService.getById(ordersEntity.getId());
        if (existOrder == null) {
            return R.error("订单不存在");
        }
        
        // 更新订单状态
        existOrder.setStatus(ordersEntity.getStatus());
        ordersService.updateById(existOrder);
        
        return R.success("订单状态更新成功");
    }

    /**
     * 再来一单
     * 将历史订单的商品重新添加到购物车
     */
    @PostMapping("/again")
    public R<String> again(@RequestBody OrdersEntity ordersEntity) {
        log.info("再来一单，订单ID：{}", ordersEntity.getId());
        
        if (ordersEntity.getId() == null) {
            return R.error("订单ID不能为空");
        }
        
        // 获取当前用户ID
        Long userId = BaseContextCommon.getCurrentUserId();
        
        // 查询订单是否存在
        OrdersEntity existOrder = ordersService.getById(ordersEntity.getId());
        if (existOrder == null) {
            return R.error("订单不存在");
        }
        
        // 验证订单是否属于当前用户
        if (!existOrder.getUserId().equals(userId)) {
            return R.error("无权操作该订单");
        }
        
        // 查询订单详情
        LambdaQueryWrapper<OrderDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetailEntity::getOrderId, ordersEntity.getId());
        List<OrderDetailEntity> orderDetails = orderDetailService.list(queryWrapper);
        
        if (orderDetails == null || orderDetails.isEmpty()) {
            return R.error("订单详情不存在");
        }
        
        // 将订单详情转换为购物车项并添加到购物车
        List<ShoppingCartEntity> shoppingCartList = orderDetails.stream().map(orderDetail -> {
            ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
            shoppingCart.setUserId(userId);
            shoppingCart.setName(orderDetail.getName());
            shoppingCart.setImage(orderDetail.getImage());
            shoppingCart.setDishId(orderDetail.getDishId());
            shoppingCart.setSetmealId(orderDetail.getSetmealId());
            shoppingCart.setDishFlavor(orderDetail.getDishFlavor());
            shoppingCart.setNumber(orderDetail.getNumber());
            shoppingCart.setAmount(orderDetail.getAmount());
            return shoppingCart;
        }).collect(Collectors.toList());
        
        // 批量添加到购物车
        shoppingCartService.saveBatch(shoppingCartList);
        
        return R.success("操作成功");
    }
}
