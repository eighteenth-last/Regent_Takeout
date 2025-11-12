package com.gpt.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gpt.Common.R;
import com.gpt.Dto.DishDto;
import com.gpt.Entity.*;
import com.gpt.Common.CustomException;
import com.gpt.Service.CategoryService;
import com.gpt.Service.DishFlavorService;
import com.gpt.Service.DishService;
import com.gpt.Service.SetmealDishService;
import com.gpt.Service.SetmealService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-08  16:43
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    // 新增菜品
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    // 分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        // 构造分页构造器
        Page<DishEntity>pageInfo=new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage=new Page<>();

        // 条件构造器
        LambdaQueryWrapper<DishEntity> queryWrapper=new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(name !=null,DishEntity::getName,name);

        //添加排序条件
        queryWrapper.orderByDesc(DishEntity::getUpdateTime);

        // 执行分页查询
        dishService.page(pageInfo,queryWrapper);

        // 对象拷贝,拷贝属性
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        // 获取records
        List<DishEntity> records = pageInfo.getRecords();
        // 处理records
        List<DishDto> list=records.stream().map((item)->{
            DishDto dishDto=new DishDto();

            BeanUtils.copyProperties(item,dishDto); //再次拷贝，拷贝普通属性

            Long categoryId = item.getCategoryId();  //分类id

            // 根据id查询分类对象
            CategoryEntity category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return  dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    // 修改菜品 根据id查询菜品的信息与口味
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    // 更新菜品信息
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    // 修改菜品销售状态
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, @RequestParam List<Long> ids){
        log.info("修改菜品状态：status={}, ids={}", status, ids);

        // 如果是停售操作（status=0），需要检查菜品是否被套餐关联
        if (status == 0) {
            // 检查菜品是否被套餐关联
            LambdaQueryWrapper<SetmealDishEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SetmealDishEntity::getDishId, ids.stream().map(String::valueOf).collect(Collectors.toList()));
            
            List<SetmealDishEntity> setmealDishes = setmealDishService.list(queryWrapper);
            if (!setmealDishes.isEmpty()) {
                // 获取关联的套餐ID
                List<String> setmealIds = setmealDishes.stream()
                    .map(SetmealDishEntity::getSetmealId)
                    .distinct()
                    .collect(Collectors.toList());
                
                // 检查关联的套餐是否在售
                LambdaQueryWrapper<SetmealEntity> setmealQueryWrapper = new LambdaQueryWrapper<>();
                setmealQueryWrapper.in(SetmealEntity::getId, setmealIds.stream().map(Long::valueOf).collect(Collectors.toList()));
                setmealQueryWrapper.eq(SetmealEntity::getStatus, 1); // 在售状态
                
                long onSaleSetmealCount = setmealService.count(setmealQueryWrapper);
                if (onSaleSetmealCount > 0) {
                    throw new CustomException("选中的菜品正在被套餐关联，不能停售");
                }
            }
        }

        // 批量更新菜品状态
        List<DishEntity> dishList = ids.stream().map(id -> {
            DishEntity dish = new DishEntity();
            dish.setId(id);
            dish.setStatus(status);
            return dish;
        }).collect(Collectors.toList());

        dishService.updateBatchById(dishList);

        return R.success("菜品状态修改成功");
    }

    // 菜品删除
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> id) {
        log.info("删除菜品，ids={}", id);
        
        // 使用带有口味数据删除的方法
        dishService.removeWithFlavor(id);
        
        return R.success("菜品删除成功");
    }

    /**
     // 根据条件查询菜品数据
     @GetMapping("/list")
     public R<List<DishEntity>>list(DishEntity dishEntity){
     // 构造查询条件对象
     LambdaQueryWrapper<DishEntity> queryWrapper = new LambdaQueryWrapper<>();
     queryWrapper.eq(dishEntity.getCategoryId()!=null,DishEntity::getCategoryId,dishEntity.getCategoryId());
     // 添加查询条件
     queryWrapper.eq(DishEntity::getStatus,1);
     // 添加排序条件
     queryWrapper.orderByAsc(DishEntity::getSort).orderByDesc(DishEntity::getUpdateTime);

     List<DishEntity> list = dishService.list(queryWrapper);
     return R.success(list);
     }
     */

    // 根据条件查询菜品数据
    @GetMapping("/list")
    public R<List<DishDto>>list(DishEntity dishEntity){
        // 构造查询条件对象
        LambdaQueryWrapper<DishEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dishEntity.getCategoryId()!=null,DishEntity::getCategoryId,dishEntity.getCategoryId());
        // 添加查询条件
        queryWrapper.eq(DishEntity::getStatus,1);
        // 添加排序条件
        queryWrapper.orderByAsc(DishEntity::getSort).orderByDesc(DishEntity::getUpdateTime);

        List<DishEntity> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList=list.stream().map((item)->{
            DishDto dishDto=new DishDto();

            BeanUtils.copyProperties(item,dishDto); //再次拷贝，拷贝普通属性

            Long categoryId = item.getCategoryId();  //分类id

            // 根据id查询分类对象
            CategoryEntity category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            // 查询口味数据
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavorEntity>queryWrapper1=new LambdaQueryWrapper<>();
            queryWrapper1.in(DishFlavorEntity::getDishId, dishId);
            List<DishFlavorEntity> dishFlavorEntityList = dishFlavorService.list(queryWrapper1);  // 查出来口味集合
            dishDto.setFlavors(dishFlavorEntityList);

            return  dishDto;

        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }

}
