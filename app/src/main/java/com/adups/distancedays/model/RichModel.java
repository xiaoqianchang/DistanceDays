package com.adups.distancedays.model;

/**
 * 富文本本地组装模型
 * <p>
 * Created by Chang.Xiao on 2019/10/17.
 *
 * @version 1.0
 */
public class RichModel {

    private String title;
    private String author;
    private String content;

    public RichModel(String content) {
        this.content = content;
    }

    public RichModel(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
