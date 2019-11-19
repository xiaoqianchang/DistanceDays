package com.common.adlib.base;

import android.content.Context;
import android.view.ViewGroup;

import com.common.adlib.bean.NativeBean;
import com.common.adlib.bean.RequestBean;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.common.adlib.base.BaseAd.java
 * @author: gz
 * @date: 2019-11-12 15:57
 */
public abstract class BaseAd {

    protected Context context;
    protected RequestBean requestBean;
    protected ViewGroup layout;
    protected String sdkType;  //SDK类型
    protected int scaleWidth = 640;
    protected int scaleHeight = 320;

    public BaseAd context(Context context){
        this.context = context;
        return this;
    }

    public BaseAd size(int width, int height) {
        this.scaleWidth = width;
        this.scaleHeight = height;
        return this;
    }

    public BaseAd layout(ViewGroup layout) {
        this.layout = layout;
        return this;
    }

    public BaseAd(RequestBean requestBean) {
        this.requestBean = requestBean;
        this.requestBean.setAppid(requestBean.getAppid());
        this.requestBean.setCount(requestBean.getCount());
        this.requestBean.setZoneid(requestBean.getZoneid());
        this.requestBean.setSdkType(requestBean.getSdkType());
    }

    public void requestBannerAd(BannerAdListener listener){

    }

    public void requestSplashAd(SplashAdListener listener,int adTimeOut){

    }

    public void requestNativeAd(NativeAdListener listener){

    }

    public interface SplashAdListener{
        void onAdClose();
        void onAdTimeOver();
        void onAdShow();
        void onAdClick();
        void onLoaded();
        void onAdSkip();
        void onAdError(int code, String msg);
    }

    public interface BannerAdListener{
        void onAdError(int code, String msg);
        void onAdShow();
        void onAdClick();
        void onAdClose();
        void onLoaded();
    }

    public interface NativeAdListener{
        void onAdError(int code, String msg);
        void onLoaded(NativeBean nativeBean);
    }

}
