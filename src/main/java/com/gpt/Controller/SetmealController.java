package com.gpt.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gpt.Common.R;
import com.gpt.Dto.DishDto;
import com.gpt.Dto.SetmealDto;
import com.gpt.Entity.CategoryEntity;
import com.gpt.Entity.DishEntity;
import com.gpt.Entity.SetmealEntity;
import com.gpt.Service.CategoryService;
import com.gpt.Service.SetmealDishService;
import com.gpt.Service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-09  08:45
 * @BelongsProject: Regent_Takeout
 * @Description: 套餐管理
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    // 新增套餐
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息:{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    // 套餐分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        // 构造分页构造器
        Page<SetmealEntity>pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage=new Page<>();

        // 条件构造器
        LambdaQueryWrapper<SetmealEntity> queryWrapper = new LambdaQueryWrapper<>();

        // 添加查询条件
        queryWrapper.like(name != null, SetmealEntity::getName, name);

        // 添加排序条件,根据时间降序排列
        queryWrapper.orderByDesc(SetmealEntity::getCreateTime);

        // 执行分页查询
        setmealService.page(pageInfo,queryWrapper);

        // 对象的拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        // 获取records
        List<SetmealEntity> records = pageInfo.getRecords();
        // 处理records
        List<SetmealDto> list=records.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();

            BeanUtils.copyProperties(item,setmealDto); //再次拷贝，拷贝普通属性

            Long categoryId = item.getCategoryId();  //分类id

            // 根据id查询分类对象
            CategoryEntity category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    // 套餐删除
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids:{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");
    }

    // 套餐修改 - 根据id查询套餐信息和对应的菜品信息
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }

    // 套餐修改 - 更新套餐信息
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info("修改套餐信息:{}", setmealDto);
        setmealService.updateWithDish(setmealDto);
        return R.success("修改套餐成功");
    }

    // 套餐停售/起售
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status, @RequestParam List<Long> ids) {
        log.info("status:{}, ids:{}", status, ids);
        
        LambdaQueryWrapper<SetmealEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealEntity::getId, ids);
        
        List<SetmealEntity> list = setmealService.list(queryWrapper);
        for (SetmealEntity setmeal : list) {
            setmeal.setStatus(status);
        }
        
        setmealService.updateBatchById(list);
        return R.success("套餐状态修改成功");
    }

    // 根据条件查询套餐数据  -- 用户端显示
    @GetMapping("/list")
    public R<List<SetmealEntity>> list(SetmealEntity setmeal){
        LambdaQueryWrapper<SetmealEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,SetmealEntity::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,SetmealEntity::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(SetmealEntity::getUpdateTime);

        List<SetmealEntity> list = setmealService.list(queryWrapper);

        return R.success(list);
    }
}
