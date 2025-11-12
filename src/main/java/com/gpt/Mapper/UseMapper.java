package com.gpt.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gpt.Entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-10  17:08
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Mapper
public interface UseMapper extends BaseMapper<UserEntity> {
}
