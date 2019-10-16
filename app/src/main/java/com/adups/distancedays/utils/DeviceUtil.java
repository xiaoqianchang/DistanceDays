package com.adups.distancedays.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 获取设备相关信息工具类
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class DeviceUtil {

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取唯一 imei
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        String imei = "";
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    imei = telephonyManager.getDeviceId();
                }
            } catch (SecurityException var3) {
                var3.printStackTrace();
                imei = "";
            } catch (Exception var4) {
                var4.printStackTrace();
                imei = "";
            }
        }

        return imei;
    }

    /**
     * 获取手机唯一序列号
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        String androidId = "";
        if (context == null) {
            return "";
        } else {
            try {
                androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                if (androidId == null) {
                    androidId = "";
                }
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return androidId;
        }
    }

    public static String getAndroidVersionCode() {
        return Build.VERSION.RELEASE;
    }

    public static int getOsVersion() {
        return Build.VERSION.SDK_INT;
    }
}
