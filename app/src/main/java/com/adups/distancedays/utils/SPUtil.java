package com.adups.distancedays.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SPUtil
 * <p>
 * Created by Chang.Xiao on 2019/10/23.
 *
 * @version 1.0
 */
public class SPUtil {

    private static final String DEFAULT_NAME = "distancedays";
    private static ConcurrentHashMap<String, SharedPreferences> sMap = new ConcurrentHashMap();

    private static SharedPreferences getSharedPreferences(String name) {
        if (CommonUtil.getApplication() != null && !TextUtils.isEmpty(name)) {
            if (!sMap.containsKey(name)) {
                SharedPreferences sharedPreferences = CommonUtil.getApplication().getSharedPreferences(name, 0);
                sMap.put(name, sharedPreferences);
            }

            return sMap.get(name);
        } else {
            return null;
        }
    }

    private static Editor getEditor(String name) {
        SharedPreferences sp = getSharedPreferences(name);
        return sp == null ? null : sp.edit();
    }

    private static void handleEdit(Editor edit) {
        if (edit != null) {
            if (Build.VERSION.SDK_INT >= 14) {
                edit.apply();
            } else {
                edit.commit();
            }

        }
    }

    private static String resIdToKey(int resKey) {
        try {
            return CommonUtil.getApplication().getString(resKey);
        } catch (Exception var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static void setValue(String key, boolean value) {
        setValue(DEFAULT_NAME, key, value);
    }

    public static void setValue(int resKey, boolean value) {
        setValue(resIdToKey(resKey), value);
    }

    public static void setValue(String key, float value) {
        setValue(DEFAULT_NAME, key, value);
    }

    public static void setValue(int resKey, float value) {
        setValue(resIdToKey(resKey), value);
    }

    public static void setValue(String key, int value) {
        setValue(DEFAULT_NAME, key, value);
    }

    public static void setValue(int resKey, int value) {
        setValue(resIdToKey(resKey), value);
    }

    public static void setValue(String key, long value) {
        setValue(DEFAULT_NAME, key, value);
    }

    public static void setValue(int resKey, long value) {
        setValue(resIdToKey(resKey), value);
    }

    public static void setValue(String key, String value) {
        setValue(DEFAULT_NAME, key, value);
    }

    public static void setValue(String key, HashMap<String, String> value) {
        JSONObject ret = new JSONObject(value);
        setValue(key, ret.toString());
    }

    public static void setValue(int resKey, String value) {
        setValue(resIdToKey(resKey), value);
    }

    public static boolean getValue(String key, boolean defaultValue) {
        return getValue(DEFAULT_NAME, key, defaultValue);
    }

    public static boolean getValue(int resKey, boolean defaultValue) {
        return getValue(resIdToKey(resKey), defaultValue);
    }

    public static float getValue(String key, float defaultValue) {
        return getValue(DEFAULT_NAME, key, defaultValue);
    }

    public static float getValue(int resKey, float defaultValue) {
        return getValue(resIdToKey(resKey), defaultValue);
    }

    public static int getValue(String key, int defaultValue) {
        return getValue(DEFAULT_NAME, key, defaultValue);
    }

    public static int getValue(int resKey, int defaultValue) {
        return getValue(resIdToKey(resKey), defaultValue);
    }

    public static long getValue(String key, long defaultValue) {
        return getValue(DEFAULT_NAME, key, defaultValue);
    }

    public static long getValue(int resKey, long defaultValue) {
        return getValue(resIdToKey(resKey), defaultValue);
    }

    public static String getValue(String key) {
        return getValue(key, "");
    }

    public static String getValue(String key, String defaultValue) {
        return getValue(DEFAULT_NAME, key, defaultValue);
    }

    public static HashMap<String, String> getMapValue(String key) {
        String mapStr = getValue(key, "{}");
        HashMap<String, String> ret = new HashMap();
        JSONObject mapJson = null;

        try {
            mapJson = new JSONObject(mapStr);
        } catch (Exception var7) {
            return ret;
        }

        if (mapJson != null) {
            Iterator it = mapJson.keys();

            while (it.hasNext()) {
                String theKey = (String) it.next();
                String theValue = mapJson.optString(theKey);
                ret.put(theKey, theValue);
            }
        }

        return ret;
    }

    public static String getValue(int resKey, String defaultValue) {
        return getValue(resIdToKey(resKey), defaultValue);
    }

    public static boolean contains(String key) {
        return contains(DEFAULT_NAME, key);
    }

    public static void remove(String key) {
        remove(DEFAULT_NAME, key);
    }

    public static void clear() {
        clear(DEFAULT_NAME);
    }

    public static void setValue(String name, String key, boolean value) {
        if (!TextUtils.isEmpty(key)) {
            Editor edit = getEditor(name);
            if (edit != null) {
                edit.putBoolean(key, value);
                handleEdit(edit);
            }
        }
    }

    public static void setValue(String name, int resKey, boolean value) {
        setValue(name, resIdToKey(resKey), value);
    }

    public static void setValue(String name, String key, float value) {
        if (!TextUtils.isEmpty(key)) {
            Editor edit = getEditor(name);
            if (edit != null) {
                edit.putFloat(key, value);
                handleEdit(edit);
            }
        }
    }

    public static void setValue(String name, int resKey, float value) {
        setValue(name, resIdToKey(resKey), value);
    }

    public static void setValue(String name, String key, int value) {
        if (!TextUtils.isEmpty(key)) {
            Editor edit = getEditor(name);
            if (edit != null) {
                edit.putInt(key, value);
                handleEdit(edit);
            }
        }
    }

    public static void setValue(String name, int resKey, int value) {
        setValue(name, resIdToKey(resKey), value);
    }

    public static void setValue(String name, String key, long value) {
        if (!TextUtils.isEmpty(key)) {
            Editor edit = getEditor(name);
            if (edit != null) {
                edit.putLong(key, value);
                handleEdit(edit);
            }
        }
    }

    public static void setValue(String name, int resKey, long value) {
        setValue(name, resIdToKey(resKey), value);
    }

    public static void setValue(String name, String key, String value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            Editor edit = getEditor(name);
            if (edit != null) {
                edit.putString(key, value.trim());
                handleEdit(edit);
            }
        }
    }

    public static void setValue(String name, int resKey, String value) {
        setValue(name, resIdToKey(resKey), value);
    }

    public static boolean getValue(String name, String key, boolean defaultValue) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(name);
            return sp == null ? false : sp.getBoolean(key, defaultValue);
        } else {
            return false;
        }
    }

    public static boolean getValue(String name, int resKey, boolean defaultValue) {
        return getValue(name, resIdToKey(resKey), defaultValue);
    }

    public static float getValue(String name, String key, float defaultValue) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(name);
            return sp == null ? 0.0F : sp.getFloat(key, defaultValue);
        } else {
            return 0.0F;
        }
    }

    public static float getValue(String name, int resKey, float defaultValue) {
        return getValue(name, resIdToKey(resKey), defaultValue);
    }

    public static int getValue(String name, String key, int defaultValue) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(name);
            return sp == null ? 0 : sp.getInt(key, defaultValue);
        } else {
            return 0;
        }
    }

    public static int getValue(String name, int resKey, int defaultValue) {
        return getValue(name, resIdToKey(resKey), defaultValue);
    }

    public static long getValue(String name, String key, long defaultValue) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(name);
            return sp == null ? 0L : sp.getLong(key, defaultValue);
        } else {
            return 0L;
        }
    }

    public static long getValue(String name, int resKey, long defaultValue) {
        return getValue(name, resIdToKey(resKey), defaultValue);
    }

    public static String getValue(String name, String key, String defaultValue) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(name);
            return sp == null ? defaultValue : sp.getString(key, defaultValue);
        } else {
            return "";
        }
    }

    public static String getValue(String name, int resKey, String defaultValue) {
        return getValue(name, resIdToKey(resKey), defaultValue);
    }

    public static boolean contains(String name, String key) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(name);
            return sp == null ? false : sp.contains(key);
        } else {
            return false;
        }
    }

    public static void remove(String name, String key) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(key)) {
            Editor edit = getEditor(name);
            if (edit != null) {
                edit.remove(key);
                handleEdit(edit);
            }
        }
    }

    public static void clear(String name) {
        if (!TextUtils.isEmpty(name)) {
            Editor edit = getEditor(name);
            if (edit != null) {
                edit.clear();
                handleEdit(edit);
            }
        }
    }
}
