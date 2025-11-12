package com.gpt.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpt.Dto.DishDto;
import com.gpt.Entity.DishEntity;

import java.util.List;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  13:17
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

public interface DishService extends IService<DishEntity> {
    // 新增菜品，同时插入菜品对应的口味数据 同时操作dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    // 根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    // 更新菜品信息与口味信息
    public void updateWithFlavor(DishDto dishDto);

    // 根据id删除菜品，同时删除关联的口味数据
    public void removeWithFlavor(Long id);
    
    // 批量删除菜品，同时删除关联的口味数据
    public void removeWithFlavor(List<Long> ids);
}
