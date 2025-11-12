package com.gpt.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpt.Entity.CategoryEntity;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  11:50
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

public interface CategoryService extends IService<CategoryEntity> {
    public void remove(Long id);
}
