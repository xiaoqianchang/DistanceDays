package com.adups.distancedays.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.adups.distancedays.MainActivity;
import com.adups.distancedays.activity.SplashActivity;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.litre.clock.utils.AppRunningStatusCallbacks.java
 * @author: gz
 * @date: 2019-11-06 13:51
 */
public class AppRunningStatusCallbacks implements Application.ActivityLifecycleCallbacks  {
    private boolean mMainOnPaused = false;
    private boolean mMainOnResumed = false;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mMainOnResumed = (activity instanceof MainActivity);
        if (mMainOnPaused && mMainOnResumed) {
            // 应用从桌面或者其他地方回来
            // 可以做一些回调
            SplashActivity.startActivity(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        mMainOnPaused = (activity instanceof MainActivity);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
