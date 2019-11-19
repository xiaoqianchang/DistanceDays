package com.common.adlib.bean;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.common.adlib.bean.RequestBean.java
 * @author: gz
 * @date: 2019-11-12 16:49
 */
public class RequestBean {

    //请求广告的应用ID，有可能是sdk的appid
    private String appid;
    //请求广告的广告位id,有可能是sdk广告位
    private String zoneid;

    private String sdkType;
    //请求广告的条数
    private int count = 1;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getZoneid() {
        return zoneid;
    }

    public void setZoneid(String zoneid) {
        this.zoneid = zoneid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSdkType() {
        return sdkType;
    }

    public void setSdkType(String sdkType) {
        this.sdkType = sdkType;
    }
}
