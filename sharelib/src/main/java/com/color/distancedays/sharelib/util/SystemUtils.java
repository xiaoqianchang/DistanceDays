package com.color.distancedays.sharelib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;

/**
 * 系统设置助手
 * <p>
 * Created by Chang.Xiao on 2019-11-06.
 *
 * @version 1.0
 */
public class SystemUtils {

    /**
     * 修复 targetSdkVersion > 26 时，在API26的机型上报如下异常问题
     * java.lang.IllegalStateException: Only fullscreen opaque activities can request orientation
     *
     * @param activity
     */
    public static void fixAndroid26OrientationBug(Activity activity) {
        if (!checkContext(activity)) {
            return;
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public static boolean checkContext(Context context) {
        if (null == context) {
            return false;
        } else if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT < 17) {
                return !activity.isFinishing();
            } else {
                return !activity.isDestroyed() && !activity.isFinishing();
            }
        } else {
            return true;
        }
    }
}
