package com.adups.distancedays.base;

import android.app.Application;
import android.content.Context;

import com.adups.distancedays.utils.BuildConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * Custom application.
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class DaysApplication extends Application {

    private static final String TAG = "QuickFrame";

    public static Context mAppInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = getApplicationContext();
        LeakCanary.install(this);

        // Logger Settings
        // Note: Use LogLevel.NONE for the release versions.
        Logger.init(TAG)                       // default PRETTYLOGGER or use just init()
                .methodCount(2)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE) // default LogLevel.FULL
                .methodOffset(2);               // default 0
    }

    public static final Context getMyApplicationContext() {
        return mAppInstance;
    }
}
