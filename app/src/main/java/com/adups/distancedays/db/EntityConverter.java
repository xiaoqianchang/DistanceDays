package com.adups.distancedays.db;

import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.utils.DateUtils;

import java.util.Calendar;

/**
 * 数据库表entity与应用层model数据模型转换
 * <p>
 * Created by Chang.Xiao on 2019/10/15.
 *
 * @version 1.0
 */
public class EntityConverter {

    public static EventModel convertToEventModel(EventEntity entity) {
        if (entity == null) {
            return null;
        }

        EventModel model = new EventModel();
        model.setEventTitle(entity.getEventTitle());
        model.setCreateTime(entity.getCreateDate());
        model.setTargetTime(entity.getTargetDate());
        model.setLunarCalendar(entity.getIsLunarCalendar());
        model.setTop(entity.getIsTop());
        model.setRepeatType(entity.getRepeatType());

        model.setEventId(entity.getId());
        Calendar targetDate = Calendar.getInstance();
        Calendar todayDate = Calendar.getInstance();
        targetDate.setTimeInMillis(entity.getTargetDate());
        int days = (int) DateUtils.getDateOffset(targetDate, todayDate);
        model.setOutOfTargetDate(days < 0);
        model.setTargetDate(targetDate.getTime());
        model.setDays(Math.abs(days));
        model.setLunarCalendar(entity.getIsLunarCalendar());
        model.setRepeatType(entity.getRepeatType());
        return model;
    }

    public static EventEntity convertToEventEntity(EventModel model) {
        if (model == null) {
            return null;
        }

        EventEntity entity = new EventEntity();
        entity.setId(model.getEventId());
        entity.setEventTitle(model.getEventTitle());
        entity.setCreateDate(model.getCreateTime());
        entity.setTargetDate(model.getTargetTime());
        entity.setIsLunarCalendar(model.isLunarCalendar());
        entity.setIsTop(model.isTop());
        entity.setRepeatType(model.getRepeatType());
        return entity;
    }
}
