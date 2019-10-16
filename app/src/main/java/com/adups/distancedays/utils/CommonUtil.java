package com.adups.distancedays.utils;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * 用于 util lib 独立时的初始化注入类
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class CommonUtil {

    private static final String TAG = "DistanceDays";

    private static Application application;
    private static boolean isDebug = false;

    public static void init(Application application, boolean isDebug) {
        if (null == application) {
            throw new RuntimeException("Application is null");
        } else {
            CommonUtil.application = application;
            CommonUtil.isDebug = isDebug;
            initLog();
        }
    }

    private static void initLog() {
        // Logger Settings
        // Note: Use LogLevel.NONE for the release versions.
        Logger.init(TAG)                       // default PRETTYLOGGER or use just init()
                .methodCount(2)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE) // default LogLevel.FULL
                .methodOffset(2);               // default 0
    }

    public static Application getApplication() {
        if (null == application) {
            throw new RuntimeException("You should call init first");
        } else {
            return application;
        }
    }

    public static boolean isDebug() {
        return isDebug;
    }
}
