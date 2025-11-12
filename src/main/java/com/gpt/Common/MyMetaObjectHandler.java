package com.gpt.Common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-05  12:09
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        // 插入自动填充
        log.info("----------公共字段自动填充【insert】----------");
        log.info(metaObject.toString());

        // 只有当字段存在时才填充，避免对没有这些字段的表报错
        if (metaObject.hasSetter("createTime")) {
            metaObject.setValue("createTime", new Date());
        }
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", new Date());
        }
        if (metaObject.hasSetter("createUser")) {
            metaObject.setValue("createUser", BaseContextCommon.getCurrentUserId());
        }
        if (metaObject.hasSetter("updateUser")) {
            metaObject.setValue("updateUser", BaseContextCommon.getCurrentUserId());
        }
    }

    // 更新自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("----------公共字段自动填充【update】----------");
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为{}:",id);

        // 只有当字段存在时才填充，避免对没有这些字段的表报错
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", new Date());
        }
        if (metaObject.hasSetter("updateUser")) {
            metaObject.setValue("updateUser", BaseContextCommon.getCurrentUserId());
        }
    }
}