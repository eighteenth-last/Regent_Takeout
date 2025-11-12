package com.gpt.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpt.Entity.EmployeeEntity;
import com.gpt.Mapper.EmployeeMapper;
import com.gpt.Service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-04  13:35
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements EmployeeService {
}
