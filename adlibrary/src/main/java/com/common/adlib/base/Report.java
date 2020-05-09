package com.common.adlib.base;


import com.common.adlib.bean.RequestBean;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;


/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.common.adlib.base.Report.java
 * @author: gz
 * @date: 2019-11-13 14:27
 */
public class Report {

    public static void report(RequestBean requestBean, ReportEvent eventCode){
        Map<String,String> map = new HashMap<>();
        map.put(requestBean.getSdkType(),eventCode.getEvent());
        MobclickAgent.onEvent(HandlerManager.getInstance().getContext(),requestBean.getZoneid(), map);
    }
}
