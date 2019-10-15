package com.adups.distancedays.model;

import java.util.Date;

/**
 * 应用层事件model
 * <p>
 * Created by Chang.Xiao on 2019/10/15.
 *
 * @version 1.0
 */
public class EventModel {

    private String eventTitle; // 事件title
    private long createTime; // 创建时间戳
    private long targetTime; // 目标时间戳
    private boolean isLunarCalendar; // 是否为阴历
    private boolean isTop; // 是否置顶
    private int repeatType; // 重复类型

    /* 下面为附属属性 */
    private Date targetDate; // 到期日
    private int days; // 距离天数
    private boolean isOutOfTargetDate; // 是否超过到期日
    private Date endDate; // 结束日
    private boolean hasEndDate; // 是否有结束日
    private boolean isOutOfEndDate; // 是否超过结束日

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(long targetTime) {
        this.targetTime = targetTime;
    }

    public boolean isLunarCalendar() {
        return isLunarCalendar;
    }

    public void setLunarCalendar(boolean lunarCalendar) {
        isLunarCalendar = lunarCalendar;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public boolean isOutOfTargetDate() {
        return isOutOfTargetDate;
    }

    public void setOutOfTargetDate(boolean outOfTargetDate) {
        isOutOfTargetDate = outOfTargetDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isHasEndDate() {
        return hasEndDate;
    }

    public void setHasEndDate(boolean hasEndDate) {
        this.hasEndDate = hasEndDate;
    }

    public boolean isOutOfEndDate() {
        return isOutOfEndDate;
    }

    public void setOutOfEndDate(boolean outOfEndDate) {
        isOutOfEndDate = outOfEndDate;
    }
}
