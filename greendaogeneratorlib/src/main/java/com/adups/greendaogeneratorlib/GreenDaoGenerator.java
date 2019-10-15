package com.adups.greendaogeneratorlib;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * 数据库配置类
 * <p>
 * Created by Chang.Xiao on 2019/10/12.
 *
 * @version 1.0
 */
public class GreenDaoGenerator {

    public static void main(String[] args) throws Exception {
        /*
        定义一个 Schema,第一个参数为版本号,第二个参数为包名
         */
        Schema schema = new Schema(1, "com.adups.distancedays.db.entity");
        schema.setDefaultJavaPackageDao("com.adups.distancedays.db.dao");

        Entity entity = addEventTable(schema);

        /*
        最后生成实体类
        第一个参数为 schema
        第二个参数为文件生成路径
         */
        new DaoGenerator().generateAll(schema, "../DistanceDays/app/src/main/java-gen");
    }

    private static Entity addEventTable(Schema schema) {
        // 一个实体类对应一张数据库表，此处表名为 EVENTENTITY （即类名）
        Entity entity = schema.addEntity("EventEntity");
        // 也可以重新命名表名(entity.setTableName("people");已过时)
        entity.setDbName("event");
        entity.setClassNameDao("EventDao");
        entity.addIdProperty(); // 定义一个主键
        entity.addStringProperty("eventTitle");
        entity.addLongProperty("createDate");
        entity.addLongProperty("targetDate");
        entity.addBooleanProperty("isLunarCalendar"); // 是否为阴历
        entity.addBooleanProperty("isTop"); // 是否置顶
        entity.addIntProperty("repeatType"); // 重复类型 0:不重复 1：每周重复 2：每月重复 3：每年重复
        return entity;
    }
}
