package com.android.ttlib;

import android.content.Context;
import android.text.TextUtils;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.common.adlib.base.HandlerManager;

/**
 * 可以用一个单例来保存TTAdManager实例，在需要初始化sdk的时候调用
 */
class TTAdManagerHolder {

    private static boolean sInit;
    private static String sAppId;
    private static TTAdManager ttAdManager;

    static TTAdManager getInstance(String appid) {
        if (!sInit || !TextUtils.equals(appid, sAppId) || ttAdManager == null) {
            synchronized (TTAdManagerHolder.class) {
                if (!sInit || !TextUtils.equals(appid, sAppId)) {
                    sAppId = appid;
                    ttAdManager = doInitTTAdManager();
                    sInit = true;
                }
            }
        }
        return ttAdManager;
    }


    //step1:接入网盟广告sdk的初始化操作，详情见接入文档和穿山甲平台说明
    private static TTAdManager doInitTTAdManager() {
        TTAdConfig ttAdConfig = new TTAdConfig.Builder()
                .appId(sAppId)
                .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName(HandlerManager.getInstance().getContext().getString(R.string.app_name))//ADConfigPerference.APP_NAME.get()
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                //.debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                //.globalDownloadListener(new AppDownloadStatusListener(context)) //下载任务的全局监听
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                .supportMultiProcess(false)
                .build(); //是否支持多进程，true支持
        /*if (LogUtils.isDebug()) {
            ttAdConfig.setDebug(true);
        }*/
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdSdk.init(HandlerManager.getInstance().getContext(),
                ttAdConfig);
        //一定要在初始化后才能调用，否则为空
        ttAdManager = TTAdSdk.getAdManager();
        //ttAdManager.requestPermissionIfNecessary(context);
        //如果明确某个进程不会使用到广告SDK，可以只针对特定进程初始化广告SDK的content
        //if (PROCESS_NAME_XXXX.equals(processName)) {
        //   TTAdSdk.init(context, config);
        //}
        return ttAdManager;
    }

}
