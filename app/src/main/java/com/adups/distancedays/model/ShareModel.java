package com.adups.distancedays.model;

/**
 * Share model.
 * <p>
 * Created by Chang.Xiao on 2019/10/21.
 *
 * @version 1.0
 */
public class ShareModel {

    private String title;
    private String content; // 分享事物内容
    private String picUrl; // 事物图片分享url
    private String shareLink; // 事物对外分享url
    private String contentUrl; // 事物内容分享url
    private byte[] thumbData; // 事物图片数据
    private int shareType; // 分享类型，文字/图片

    // 额外扩展属性
    private String eventTitle;
    private String day;
    private String dueDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public byte[] getThumbData() {
        return thumbData;
    }

    public void setThumbData(byte[] thumbData) {
        this.thumbData = thumbData;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
