package com.gpt.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpt.Common.CustomException;
import com.gpt.Dto.SetmealDto;
import com.gpt.Entity.SetmealDishEntity;
import com.gpt.Entity.SetmealEntity;
import com.gpt.Mapper.SetmealMapper;
import com.gpt.Service.SetmealDishService;
import com.gpt.Service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  13:17
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,SetmealEntity> implements SetmealService{

    @Autowired
    private SetmealDishService setmealDishService;

    // 新增套餐，同时保存套餐跟菜品的关系
//    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐的基本信息，操作setmeal表，执行insert操作
        this.save(setmealDto);
        List<SetmealDishEntity> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(String.valueOf(setmealDto.getId()));
            return item;
        }).toList();
        // 保存套餐跟菜品的关系，操作setmeal_dish表，执行insert操作
        setmealDishService.saveBatch(setmealDishes);  // saveBatch批量插入
    }

    // 删除套餐
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // 查询套餐状态，是否是停售
        LambdaQueryWrapper<SetmealEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealEntity::getId,ids);
        queryWrapper.eq(SetmealEntity::getStatus,1);
        // 使用的sql脚本是：select count(*) from setmeal where id in (1,2,3) and status =1;
        int count = Math.toIntExact(this.count(queryWrapper));
        if(count>0){
            // 如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖，不可删除");
        }

        // 如果可以，先删除套餐表中的数据
        this.removeByIds(ids);

        // 删除关系表中的数据
        LambdaQueryWrapper<SetmealDishEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDishEntity::getSetmealId,ids);

        setmealDishService.remove(lambdaQueryWrapper);
    }
    
    // 修改套餐，同时更新套餐跟菜品的关系
    @Override
    @Transactional
    public void updateWithDish(SetmealDto setmealDto) {
        // 更新套餐表基本信息
        this.updateById(setmealDto);
        
        // 清理当前套餐对应菜品数据
        LambdaQueryWrapper<SetmealDishEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDishEntity::getSetmealId, setmealDto.getId());
        setmealDishService.remove(queryWrapper);
        
        // 添加当前提交过来的菜品数据
        List<SetmealDishEntity> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(String.valueOf(setmealDto.getId()));
            return item;
        }).collect(Collectors.toList());
        
        setmealDishService.saveBatch(setmealDishes);
    }
    
    // 根据id查询套餐信息和对应的菜品信息
    @Override
    public SetmealDto getByIdWithDish(Long id) {
        // 查询套餐基本信息，从setmeal表查询
        SetmealEntity setmeal = this.getById(id);
        
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        
        // 查询当前套餐对应的菜品信息，从setmeal_dish表查询
        LambdaQueryWrapper<SetmealDishEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDishEntity::getSetmealId, id);
        List<SetmealDishEntity> setmealDishes = setmealDishService.list(queryWrapper);
        
        setmealDto.setSetmealDishes(setmealDishes);
        
        return setmealDto;
    }
}
