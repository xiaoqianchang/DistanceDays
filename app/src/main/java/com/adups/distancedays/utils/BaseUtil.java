package com.adups.distancedays.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Description: Base util.
 * <p>
 * Created by Chang.Xiao on 2020-07-03.
 *
 * @version 1.0
 */
public class BaseUtil {

    /**
     * 是否是主进程
     * @return
     */
    public static boolean isMainProcess(Context context) {
        if (context == null) {
            return false;
        }
        try {
            String defaultProcessName = context.getPackageName();
            return TextUtils.equals(defaultProcessName, getCurProcessName(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getCurProcessName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            int pid = android.os.Process.myPid();
            return getProcessName(context, pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getProcessName(Context context, int pid) {
        if (context == null) {
            return "";
        }
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            if (runningApps == null || runningApps.size() == 0) {
                return "";
            }
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo != null && procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
