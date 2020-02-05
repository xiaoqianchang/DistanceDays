package com.adups.distancedays.db;

import android.text.TextUtils;

import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.manager.DefaultEventFactory;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.LunarCalendar;

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

        Calendar dueDate2 = getRepeatedDueDateNew(targetDate, model);

        int days = (int) DateUtils.getDateOffset(dueDate2, todayDate);
        model.setOutOfTargetDate(days < 0);
        model.setTargetDate(dueDate2.getTime());
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

    private static Calendar getRepeatedDueDateNew(Calendar targetDate, EventModel model) {
        if (model == null) {
            return Calendar.getInstance();
        }
        return getRepeatedDueDateNew(targetDate, model.getEventTitle(), model.getRepeatType(), model.isLunarCalendar());
    }

    public static Calendar getRepeatedDueDateNew(Calendar dueDate, String eventTitle, int repeatType, boolean isLunar) {
        return getRepeatedDueDateNew(dueDate, eventTitle, repeatType, 0, 0, isLunar);
    }

    /**
     * 获取事件类型（通过事件类型数组下标转换为事件类型）
     *
     * @param position
     * @return
     */
    public static int getRepeatType(int position) {
        int repeatType = EventModel.TYPE_REPEAT_NONE;
        switch (position) {
            case 1:
                repeatType = EventModel.TYPE_REPEAT_PER_WEEK;
                break;
            case 2:
                repeatType = EventModel.TYPE_REPEAT_PER_MONTH;
                break;
            case 3:
                repeatType = EventModel.TYPE_REPEAT_PER_YEAR;
                break;
            case 4:
                repeatType = EventModel.TYPE_REPEAT_PER_DAY;
                break;
        }
        return repeatType;
    }

    /**
     * 获取重复类型到期日的 Calendar
     *
     * @param dueDate 到期日 即 目标日
     * @param eventTitle 事件标题，目前这个参数是用于判断新年类型的
     * @param repeatType 重复类型
     * @param repeatInterval 重复间隔，比如每一年重复、每两年重复等，默认为0
     * @param isLunar 是否为阴历
     * @return
     */
    public static Calendar getRepeatedDueDateNew(Calendar dueDate, String eventTitle, int repeatType, int repeatInterval, int extra, boolean isLunar) {
        Calendar todayDate = Calendar.getInstance();
        Calendar result = (Calendar) dueDate.clone();
        if (repeatType == EventModel.TYPE_REPEAT_NONE || repeatInterval < 0) {
            return result;
        }
        if (repeatInterval == 0) {
            repeatInterval = 1;
        }
        if (isLunar) {
            LunarCalendar lunarCalendar = new LunarCalendar(dueDate);
            if (lunarCalendar.getYear() == 2099) {
                return dueDate;
            }
            if (repeatType != EventModel.TYPE_REPEAT_PER_MONTH) {
            }
            while (DateUtils.getDateOffset(lunarCalendar.getSolarCalendar(), todayDate) < 0) {
                int i = 0;
                while (true) {
                    if (i < repeatInterval) {
                        if (lunarCalendar.addOneMonth() == null) {
                            return lunarCalendar.getSolarCalendar();
                        }
                        i++;
                    } else {
                        break;
                    }
                }
            }
            if (extra > 0) {
                for (int i2 = 0; i2 < repeatInterval * extra; i2++) {
                    lunarCalendar.addOneMonth();
                }
            }
            return lunarCalendar.getSolarCalendar();
        }
        int field = -1;
        switch (repeatType) {
            case EventModel.TYPE_REPEAT_PER_WEEK: // 每周重复
                field = Calendar.WEEK_OF_YEAR; // 3
                break;
            case EventModel.TYPE_REPEAT_PER_MONTH: // 每月重复
                field = Calendar.MONTH; // 2
                break;
            case EventModel.TYPE_REPEAT_PER_YEAR: // 每年重复
                field = Calendar.YEAR; // 1
                break;
            case EventModel.TYPE_REPEAT_PER_DAY: // 每天重复
                field = Calendar.DAY_OF_YEAR; // 6
                break;
        }
        while (true) {
            if (field != -1 && DateUtils.getDateOffset(result, todayDate) < 0) {
                if (field == Calendar.DAY_OF_YEAR && repeatInterval == 1) {
                    result = Calendar.getInstance();
                } else if (field == Calendar.WEEK_OF_YEAR) {
                    result.add(Calendar.DAY_OF_YEAR, repeatInterval * 7);
                } else {
                    if (field == Calendar.YEAR && TextUtils.equals(eventTitle, DefaultEventFactory.TITLE_NEW_YEAR)) {
                        LunarCalendar lunarCalendar = new LunarCalendar(Calendar.getInstance());
                        result = LunarCalendar.lunarToSolarCalendar(lunarCalendar.getYear() + repeatInterval, 1, 1, lunarCalendar.isLeapMonth());
                    } else {
                        result.set(field, result.get(field) + repeatInterval);
                        int i3 = result.get(field);
                    }
                }
            } else {
                break;
            }
        }
        if (extra > 0) {
            if (field == Calendar.WEEK_OF_YEAR) {
                result.add(Calendar.DAY_OF_YEAR, repeatInterval * 7 * extra);
            } else {
                result.set(field, result.get(field) + (repeatInterval * extra));
                int i4 = result.get(field);
            }
        }
        return result;
    }
}
