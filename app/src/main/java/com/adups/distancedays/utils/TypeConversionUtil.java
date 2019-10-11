package com.adups.distancedays.utils;

import android.text.TextUtils;

/**
 * 类型转换工具类
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class TypeConversionUtil {

    public static int parseInt(String value) {
        return parseInt(value, 0);
    }

    public static int parseInt(String value, int defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (Exception var3) {
                var3.printStackTrace();
                return defaultValue;
            }
        }
    }
}
