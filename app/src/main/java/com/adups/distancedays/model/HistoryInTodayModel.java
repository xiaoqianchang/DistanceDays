package com.adups.distancedays.model;

/**
 * 历史上的今天页面接口model
 * <p>
 * Created by Chang.Xiao on 2019/10/17.
 *
 * @version 1.0
 */
public class HistoryInTodayModel {

    private long id; // 时间ID
    private String title; // 事件标题
    private String pic; //  图片
    private int year; // 年份
    private int month; // 月份
    private int day; // 日
    private String des; // 描述
    private String lunar; // 阴历

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }
}
