package com.cskt.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * mybatis-plus自动填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private final Logger log = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "creationDate", LocalDate::now, LocalDate.class);
        this.strictInsertFill(metaObject, "modifyDate", LocalDate::now, LocalDate.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictInsertFill(metaObject, "modifyDate", LocalDate::now, LocalDate.class);
    }
}
