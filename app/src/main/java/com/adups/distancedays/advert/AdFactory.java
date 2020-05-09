package com.adups.distancedays.advert;

import android.content.Context;
import android.view.ViewGroup;

import com.android.ttlib.ToutiaoAd;
import com.common.adlib.base.BaseAd;
import com.common.adlib.bean.RequestBean;


/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.android.adlib.AdFactory.java
 * @author: gz
 * @date: 2019-11-12 10:03
 */
public class AdFactory {

    public static BaseAd newAd(Context context, RequestBean requestBean, ViewGroup adLayout) {
        //if ("baidu".equals(requestBean.getSdkType())) {
            //return new BaiduAd(requestBean).context(context).layout(adLayout);
        //} else if ("toutiao".equals(requestBean.getSdkType())) {
            return new ToutiaoAd(requestBean).context(context).layout(adLayout);
        /*}else {
            return null;
        }*/
    }
}
