package com.adups.distancedays.model;

/**
 * Description：app 配置 model
 * <p>
 * Created by Chang.Xiao on 2020-04-02.
 *
 * @version 1.0
 */
public class AppConfigModel {

    private String appVersion;
    private String channel; // 渠道
    private boolean enable; // 该条配置是否开启（有效）
    private boolean showSplashAd; // 显示开屏广告

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isShowSplashAd() {
        return showSplashAd;
    }

    public void setShowSplashAd(boolean showSplashAd) {
        this.showSplashAd = showSplashAd;
    }
}
