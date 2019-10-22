package com.adups.distancedays.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

/**
 * 上下文有效检测
 * <p>
 * Created by Chang.Xiao on 2019/10/22.
 *
 * @version 1.0
 */
public class ContextUtils {

    public static boolean checkContext(Context context) {
        if (null == context) {
            return false;
        } else if (context instanceof Activity) {
            Activity activity = (Activity)context;
            if (Build.VERSION.SDK_INT < 17) {
                return !activity.isFinishing();
            } else {
                return !activity.isDestroyed() && !activity.isFinishing();
            }
        } else {
            return true;
        }
    }

    public static boolean checkActivity(Context context) {
        if (null == context) {
            return false;
        } else if (!(context instanceof Activity)) {
            return true;
        } else {
            Activity activity = (Activity)context;
            return !activity.isDestroyed() && !activity.isFinishing();
        }
    }
}
