package com.gpt.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gpt.Common.R;
import com.gpt.Entity.EmployeeEntity;
import com.gpt.Service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-04  13:37
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 员工登录
    @PostMapping("/login")
    public R<EmployeeEntity> login(HttpServletRequest request, @RequestBody EmployeeEntity employeeEntity) {
        // 第一步  将页面提交的密码进行md5加密
        String password = employeeEntity.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());

        // 第二步  根据提交的用户名查询数据库
        LambdaQueryWrapper<EmployeeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EmployeeEntity::getUsername,employeeEntity.getUsername());
        EmployeeEntity emp = employeeService.getOne(queryWrapper);

        // 第三步  如果没有查询到返回到了失败
        if(emp==null){
            return R.error("登录失败");
        }
        // 第四步  密码对比，如果不一致则返回失败
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        // 第五步  查看员工状态，如果status（状态）是禁用状态，则返回禁用结果
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }

        // 第六步  登陆成功，将员工id存入session并返回登陆成功的结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    // 员工退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 清理session中员工的登录id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    // 添加员工
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody EmployeeEntity employee){
        log.info("新增员工信息:{}",employee.toString());

        // 设置初始密码，使用md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // employee.setCreateTime(new Date());
        // employee.setUpdateTime(new Date());

        // 获得当前登录用户id
        //  Long empId = (Long) request.getSession().getAttribute("employee");

        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    // 员工信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page:{},pageSize:{},name:{}",page,pageSize,name);

        // 构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<EmployeeEntity> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.hasText(name),EmployeeEntity::getName,name);
        
        //添加排序条件
        queryWrapper.orderByDesc(EmployeeEntity::getUpdateTime);

        // 执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    // 更改员工状态
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody EmployeeEntity employee){
        log.info(employee.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为{}:",id);
        // Long empId = (Long) request.getSession().getAttribute("employee");
        // employee.setUpdateTime(new Date());  // 更新时间
        // employee.setUpdateUser(empId);  // 更新者
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    // 回显员工信息
    @GetMapping("/{id}")
    public R<EmployeeEntity> getById(@PathVariable Long id){
        log.info("根据id查询员工信息");
        EmployeeEntity employee = employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("为查询到该员工信息");
    }
}
