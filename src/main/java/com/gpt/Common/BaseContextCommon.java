package com.gpt.Common;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-06  11:31
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */
// 基于ThreadLocal封装工具类，用户保存和获取当前用户的id
public class BaseContextCommon {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // 设置
    public static void setCurrentUserId(long id) {
        threadLocal.set(id);
    }

    // 获取
    public static long getCurrentUserId() {
        return threadLocal.get();
    }

}
