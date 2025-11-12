package com.gpt.Common;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  13:16
 * @BelongsProject: Regent_Takeout
 * @Description: 自定义业务异常类
 * @Version: 1.0
 */
public class CustomException extends RuntimeException {
    
    public CustomException(String message) {
        super(message);
    }
}