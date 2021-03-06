package com.adups.distancedays.utils;

import android.app.Application;

import com.parse.Parse;

/**
 * Description：Parse 初始化工具类
 * <p>
 * Created by Chang.Xiao on 2020-04-20.
 *
 * @version 1.0
 */
public class ParseUtil {

    public static void init(Application application) {
        try {
            // 此处初始化有可能报 Caused by: java.lang.RuntimeException: Could not create ParseKeyValueCache directory. 在 ParseKeyValueCache.initialize()中
            Parse.initialize(new Parse.Configuration.Builder(application)
                    .applicationId("distanceday")
                    // if desired
                    .clientKey("4320dac118e0f361d95f316f8e70d2e6") // read only key
                    .server("http://p.mktask.com/distanceday")
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
