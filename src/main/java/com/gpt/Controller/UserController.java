package com.gpt.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gpt.Common.R;
import com.gpt.Entity.UserEntity;
import com.gpt.Service.UseService;
import com.gpt.Utils.SMSUtils;
import com.gpt.Utils.ValidateCodeUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-10  17:08
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UseService useService;

    /**
     * 生成随机用户名
     * 格式：新用户 + 6位随机字母数字组合
     */
    private String generateRandomUsername() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder("新用户");
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @GetMapping("/code")
    public R<String> sendMsg(String phone, HttpSession session){
        log.info("获取验证码，手机号：{}", phone);
        
        // 验证手机号格式
        if(StringUtils.isNotEmpty(phone)){
            // 生成随机的验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            log.info("验证码:{}",code);  // 控制台查看验证码
            
            // 调用阿里云的短信服务API完成发送短信
            // SMSUtils.sendMessage("瑞吉外卖","",phone,code);
            
            // 需要将生成的验证码保存到session
            session.setAttribute(phone,code);

            return R.success("手机验证码发送成功");
        }
        return R.error("手机验证码发送失败");
    }

    // 移动端用户登录
    @PostMapping("/login")
    public R<UserEntity> login(@RequestBody Map<String, String> map, HttpSession session){
        log.info("用户登录：{}", map);
        
        // 获取手机号
        String phone = map.get("phone");
        // 获取验证码
        String code = map.get("code");
        
        // 从session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);
        
        // 进行验证码的比对（页面提交的验证码和session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){

            // 如果能够比对成功，说明登录成功
            LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserEntity::getPhone, phone);
            
            UserEntity user = useService.getOne(queryWrapper);
            if(user == null){
                // 判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new UserEntity();
                user.setPhone(phone);
                user.setName(generateRandomUsername()); // 生成随机用户名
                user.setStatus(1);
                useService.save(user);
                log.info("新用户注册成功，用户名：{}", user.getName());
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }

    // 获取当前登录用户信息
    @GetMapping("/info")
    public R<UserEntity> getUserInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        if(userId == null) {
            return R.error("用户未登录");
        }
        
        UserEntity user = useService.getById(userId);
        if(user != null) {
            return R.success(user);
        }
        return R.error("用户信息不存在");
    }

    // 移动端用户退出
    @PostMapping("/loginout")
    public R<String> logout(HttpSession session) {
        log.info("用户退出登录");
        
        // 清除session中的用户信息
        session.removeAttribute("user");
        
        return R.success("退出成功");
    }
}
