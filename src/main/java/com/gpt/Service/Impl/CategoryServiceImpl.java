package com.gpt.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpt.Common.CustomException;
import com.gpt.Entity.CategoryEntity;
import com.gpt.Entity.DishEntity;
import com.gpt.Entity.SetmealEntity;
import com.gpt.Mapper.CategoryMapper;
import com.gpt.Service.CategoryService;
import com.gpt.Service.DishService;
import com.gpt.Service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  11:50
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,CategoryEntity> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    // 根据id删除分类，删除之前需要判断分类是否关联菜品
    @Override
    public void remove(Long id){
        LambdaQueryWrapper<DishEntity> dishLambdaQueryWrapper = new LambdaQueryWrapper<DishEntity>();
        // 添加查询条件，分类id
        dishLambdaQueryWrapper.eq(DishEntity::getCategoryId,id);
        int count1 = Math.toIntExact(dishService.count(dishLambdaQueryWrapper));
        // 查询分类是否关联菜品，如果已关联，则抛出异常
        if(count1>0){
            // 已经关联有菜品，抛出业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        // 查询分类是否关联套餐，如果已关联，则抛出业务异常
        LambdaQueryWrapper<SetmealEntity> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        // 添加查询条件，分类id
        setmealLambdaQueryWrapper.eq(SetmealEntity::getCategoryId,id);
        int count2 = Math.toIntExact(setmealService.count(setmealLambdaQueryWrapper));
        if(count2>0){
            // 已经关联有套餐，抛出业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        // 正常删除分类
        super.removeById(id);
    }
}
