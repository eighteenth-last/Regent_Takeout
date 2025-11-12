package com.gpt.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpt.Dto.DishDto;
import com.gpt.Entity.DishEntity;
import com.gpt.Entity.DishFlavorEntity;
import com.gpt.Mapper.DishMapper;
import com.gpt.Service.DishFlavorService;
import com.gpt.Service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  13:18
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, DishEntity> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    
    // 新增菜品，同时保存口味数据
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto){
        this.save(dishDto);// 保存菜品的基本信息

        // 菜品id
        Long dishId = dishDto.getId();

        // 菜品口味
        List<DishFlavorEntity> flavors = dishDto.getFlavors();
        flavors=flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        // 保存菜品口味
        dishFlavorService.saveBatch(flavors);
    }

    // 根据id查询菜品信息和对应的口味信息
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        // 查询才菜品基本信息
        DishEntity dish = this.getById(id);

        // 拷贝普通属性
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        // 查询菜品口味信息
        LambdaQueryWrapper<DishFlavorEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavorEntity::getDishId, dish.getId());
        List<DishFlavorEntity> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // 更新dish表信息
        this.updateById(dishDto);

        // 清理当前菜品对应口味信息----dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavorEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavorEntity::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // 添加当前提交过来的口味数据----dish_flavor表的insert操作
        List<DishFlavorEntity> flavors = dishDto.getFlavors();
        flavors=flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
    
    @Override
    @Transactional
    public void removeWithFlavor(Long id) {
        // 删除菜品口味信息
        LambdaQueryWrapper<DishFlavorEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavorEntity::getDishId, id);
        dishFlavorService.remove(queryWrapper);
        
        // 删除菜品基本信息
        this.removeById(id);
    }
    
    @Override
    @Transactional
    public void removeWithFlavor(List<Long> ids) {
        // 批量删除菜品口味信息
        LambdaQueryWrapper<DishFlavorEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(DishFlavorEntity::getDishId, ids);
        dishFlavorService.remove(queryWrapper);
        
        // 批量删除菜品基本信息
        this.removeByIds(ids);
    }
}
