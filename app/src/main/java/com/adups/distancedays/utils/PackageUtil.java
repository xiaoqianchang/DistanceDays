package com.adups.distancedays.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Package util
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class PackageUtil {

    /**
     * 获取渠道
     *
     * @param context 上下文环境
     * @return 元数据值
     */
    public static String getChannel(Context context) {
        return getMetaData(context, "UMENG_CHANNEL");
    }

    /**
     * 获取元数据
     *
     * @param context 上下文环境
     * @param key 元数据key
     * @return 元数据值
     */
    public static String getMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return "";
        }
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (Exception e) {
            return "";
        }

        if (appInfo != null && appInfo.metaData != null && appInfo.metaData.containsKey(key)) {
            String metaData = appInfo.metaData.getString(key);
            if (!TextUtils.isEmpty(metaData)) {
                return metaData;
            } else {
                metaData = appInfo.metaData.getInt(key) + "";
            }
            return metaData;
        } else {
            return "";
        }
    }

    private static PackageInfo getPackageInfo(Context pContext) {
        return getPackageInfo(pContext, 0);
    }

    private static PackageInfo getPackageInfo(Context context, int flags) {
        PackageInfo packageInfo = null;
        if (context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                String packageName = context.getPackageName();
                packageInfo = packageManager.getPackageInfo(packageName, flags);
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

        return packageInfo;
    }

    public static String getPackageName(Context context) {
        if (context == null) {
            return "";
        } else {
            try {
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return info.packageName;
            } catch (Exception var2) {
                var2.printStackTrace();
                return "";
            }
        }
    }

    public static int getVersionCode() {
        PackageInfo pi = getPackageInfo(CommonUtil.getApplication());
        return pi != null ? pi.versionCode : 0;
    }

    public static String getVersionCodeString() {
        int versionCode = getVersionCode();
        return versionCode == 0 ? "" : String.valueOf(versionCode);
    }

    public static String getVersionName() {
        PackageInfo pi = getPackageInfo(CommonUtil.getApplication());
        return pi != null ? pi.versionName : "";
    }
}
