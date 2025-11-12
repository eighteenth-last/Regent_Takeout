package com.gpt.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpt.Dto.SetmealDto;
import com.gpt.Entity.SetmealEntity;

import java.util.List;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  13:17
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

public interface SetmealService extends IService<SetmealEntity> {
    // 新增套餐，同时保存套餐跟菜品的关系
    public void  saveWithDish(SetmealDto setmealDto);

    // 删除套餐，同时删除套餐跟菜品的关系
    public void removeWithDish(List<Long> ids);
    
    // 修改套餐，同时更新套餐跟菜品的关系
    public void updateWithDish(SetmealDto setmealDto);
    
    // 根据id查询套餐信息和对应的菜品信息
    public SetmealDto getByIdWithDish(Long id);
}
