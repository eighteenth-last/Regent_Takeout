package com.gpt.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gpt.Entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-04  13:32
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeeEntity> {
}
