package com.adups.distancedays.model;

/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/15.
 *
 * @version 1.0
 */
public class EventModel {

    private String eventContent;
    private long createDate;

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(long targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isHasEndDate() {
        return hasEndDate;
    }

    public void setHasEndDate(boolean hasEndDate) {
        this.hasEndDate = hasEndDate;
    }

    public boolean isLunarCalendar() {
        return isLunarCalendar;
    }

    public void setLunarCalendar(boolean lunarCalendar) {
        isLunarCalendar = lunarCalendar;
    }

    public boolean isOnTop() {
        return isOnTop;
    }

    public void setOnTop(boolean onTop) {
        isOnTop = onTop;
    }

    public boolean isOutOfDate() {
        return isOutOfDate;
    }

    public void setOutOfDate(boolean outOfDate) {
        isOutOfDate = outOfDate;
    }

    public boolean isOutOfEndDate() {
        return isOutOfEndDate;
    }

    public void setOutOfEndDate(boolean outOfEndDate) {
        isOutOfEndDate = outOfEndDate;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public int getRequestCodeToday() {
        return requestCodeToday;
    }

    public void setRequestCodeToday(int requestCodeToday) {
        this.requestCodeToday = requestCodeToday;
    }

    public int getRequestCodeYesterday() {
        return requestCodeYesterday;
    }

    public void setRequestCodeYesterday(int requestCodeYesterday) {
        this.requestCodeYesterday = requestCodeYesterday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private long targetDate;
    private boolean top;
    private int repeatType;
    private int days;
    private long dueDate;
    private long endDate;
    private String eventId;
    private boolean hasEndDate;
    private boolean isLunarCalendar;
    private boolean isOnTop;
    private boolean isOutOfDate;
    private boolean isOutOfEndDate;
    private int repeatInterval;
    private int requestCodeToday;
    private int requestCodeYesterday;
    private String title;


}
