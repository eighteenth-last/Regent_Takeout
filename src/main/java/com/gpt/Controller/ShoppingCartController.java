package com.gpt.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gpt.Common.BaseContextCommon;
import com.gpt.Common.R;
import com.gpt.Entity.ShoppingCartEntity;
import com.gpt.Service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-11  11:04
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    // 添加到购物车
    @PostMapping("/add")
    public R<ShoppingCartEntity> add(@RequestBody ShoppingCartEntity shoppingCartEntity){
        log.info("添加购物车的数据");

        // 设置用户id，知道是那个用户的购物车数据
        long currentUserId = BaseContextCommon.getCurrentUserId();
        shoppingCartEntity.setUserId(currentUserId);

        // 如果用户重复下单，查询该套餐是否已存在该用户的购物车中
        Long dishId = shoppingCartEntity.getDishId();

        LambdaQueryWrapper<ShoppingCartEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCartEntity::getUserId,currentUserId);

        if(dishId!=null){
            // 添加到购物车的是菜品
            queryWrapper.eq(ShoppingCartEntity::getDishId,dishId);
        }else {
            // 添加到购物车的是套餐
            queryWrapper.eq(ShoppingCartEntity::getSetmealId,shoppingCartEntity.getSetmealId());
        }
        ShoppingCartEntity cartServiceOne = shoppingCartService.getOne(queryWrapper);
            if(cartServiceOne != null){
                // 如果有 数量+n   number 字段

                Integer number = cartServiceOne.getNumber();
                cartServiceOne.setNumber(number+1);
                shoppingCartService.updateById(cartServiceOne);
            }else {
                // 如果没有，则添加到购物车，数量默认 +1
                shoppingCartEntity.setNumber(1);
                shoppingCartEntity.setCreateTime(new Date());
                shoppingCartService.save(shoppingCartEntity);
                cartServiceOne=shoppingCartEntity;
            }

        return R.success(cartServiceOne);
    }

    // 查看购物车
    @GetMapping("/list")
    public R<List<ShoppingCartEntity>> list(){
        // 根据userid来查
        log.info("查看购物车信息。。。。。");
        LambdaQueryWrapper<ShoppingCartEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCartEntity::getUserId,BaseContextCommon.getCurrentUserId());
        queryWrapper.orderByAsc(ShoppingCartEntity::getCreateTime);

        List<ShoppingCartEntity> list = shoppingCartService.list(queryWrapper);


        return R.success(list);
    }

    // 清空购物车
    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCartEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCartEntity::getUserId,BaseContextCommon.getCurrentUserId());

        shoppingCartService.remove(queryWrapper);

        return R.success("购物车已清空");
    }

    // 删除购物车中的某一个订单

}
