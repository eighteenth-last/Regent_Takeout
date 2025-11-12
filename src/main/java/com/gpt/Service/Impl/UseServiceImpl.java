package com.gpt.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpt.Entity.UserEntity;
import com.gpt.Mapper.UseMapper;
import com.gpt.Service.UseService;
import org.springframework.stereotype.Service;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-10  17:08
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Service
public class UseServiceImpl extends ServiceImpl<UseMapper, UserEntity> implements UseService {
}
