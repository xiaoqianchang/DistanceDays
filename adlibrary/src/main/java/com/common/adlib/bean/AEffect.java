package com.common.adlib.bean;

/**
 * Created by JiangZhuyang on 2017/3/24.
 */

public enum AEffect {
    DOWNLOAD("apkdownload"),
    REDIRECT_PAGE("redirectpage"),
    REDIRECT_BROWSER("redirectbrowser"),
    REDIRECT_GP("redirectgp"),
    OPEN_DETAIL("opendetail");

    public String getValue() {
        return value;
    }

    private String value;

    AEffect(String value) {
        this.value = value;
    }

}
