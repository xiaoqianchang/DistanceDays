package com.adups.distancedays.model;

import com.google.gson.annotations.SerializedName;

/**
 * 每日文章模型
 * <p>
 * Created by Chang.Xiao on 2019/10/17.
 *
 * @version 1.0
 */
public class ArticleModel {

    private ArticleInfo articleInfo;

    public class ArticleInfo {
        @SerializedName("article_author")
        private String articleAuthor;
        private String title;
        @SerializedName("article_text")
        private String articleText;

        public String getArticleAuthor() {
            return articleAuthor;
        }

        public void setArticleAuthor(String articleAuthor) {
            this.articleAuthor = articleAuthor;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArticleText() {
            return articleText;
        }

        public void setArticleText(String articleText) {
            this.articleText = articleText;
        }
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }
}
