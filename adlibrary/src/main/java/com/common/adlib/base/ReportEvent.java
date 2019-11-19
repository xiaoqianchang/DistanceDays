package com.common.adlib.base;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.common.adlib.base.Report.java
 * @author: gz
 * @date: 2019-11-13 14:27
 */
public enum ReportEvent {

    SHOW("show"),
    CLICK("click"),
    REQUEST("request"),
    PREFILL("prefill"),
    DOWNLOADING("downloading"),
    INSTALL("install"),
    DOWNLOADED("downloaded"),
    ACTIVE("active"),
    OPENAPP("openapp");

    private String event;

    ReportEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return this.event;
    }

}
