package com.gpt.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gpt.Common.R;
import com.gpt.Entity.CategoryEntity;
import com.gpt.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  11:51
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(("/category"))
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody CategoryEntity categoryEntity) {
        log.info("添加分类{}", categoryEntity);
        categoryService.save(categoryEntity);
        return R.success("新增分类成功");
    }

    // 分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){

        // 分页构造器
        Page<CategoryEntity> pageInfo = new Page<>(page,pageSize);

        // 条件构造器
        LambdaQueryWrapper<CategoryEntity> queryWrapper = new LambdaQueryWrapper<>();

        // 添加排序条件根据sort
        queryWrapper.orderByDesc(CategoryEntity::getSort);

        // 进行分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    // 分类删除
    @DeleteMapping
    public R<String> delete(Long id) {
        // 不想去src/main/resources/backend/api/category.js修改delete的id的话，可用在Long前面加@RequestParam("ids")
        log.info("删除分类的id为{}",id);
        categoryService.remove(id);
        // categoryService.removeById(id);
        return R.success("分类删除成功");
    }

    // 分类修改
    @PutMapping
    public R<String> update(@RequestBody CategoryEntity categoryEntity) {
        log.info("修改分类信息{}", categoryEntity);
        categoryService.updateById(categoryEntity);
        return R.success("修改分类成功");
    }

    // 根据条件查询分类数据
    @GetMapping("/list")
    public R<List<CategoryEntity>> list(CategoryEntity categoryEntity) {
        // 条件构造器
        LambdaQueryWrapper<CategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        // 添加条件
        queryWrapper.eq(categoryEntity.getType()!=null,CategoryEntity::getType,categoryEntity.getType());
        // 添加排序条件
        queryWrapper.orderByDesc(CategoryEntity::getSort).orderByDesc(CategoryEntity::getUpdateTime);

        List<CategoryEntity> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
