package com.adups.distancedays.utils;

import android.content.Context;
import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import java.util.Map;

/**
 * 统计工具类
 */
public class StatisticsUtil {

  private static final String TAG = StatisticsUtil.class.getSimpleName();


  /**
   * 设置调试模式
   *
   * @param debugMode true:调试模式 false:正式版
   */
  public static void setDebugMode(boolean debugMode) {
    MobclickAgent.setDebugMode(debugMode);
  }

  /**
   * 启动账号统计
   */
  public static void onProfileSignIn(String userId) {
    if (TextUtils.isEmpty(userId)) {
      return;
    }
    try {
      MobclickAgent.onProfileSignIn(userId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void onProfileSignOff() {
    try {
      MobclickAgent.onProfileSignOff();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void onResume(Context context) {
    if (context == null) {
      return;
    }
    try {
      MobclickAgent.onResume(context);
    } catch (Throwable e) {
    }
  }

  public static void onPause(Context context) {
    if (context == null) {
      return;
    }
    try {
      MobclickAgent.onPause(context);
    } catch (Throwable e) {
    }
  }

  /**
   * 上报事件至友盟
   *
   * @param context 上下文环境变量
   * @param event 统计事件名称
   */
  public static void onUmengEvent(Context context, String event) {
    if (context == null || TextUtils.isEmpty(event)) {
      return;
    }
    Logger.t(TAG).d("友盟：" + event);
    MobclickAgent.onEvent(context, event);
  }

  /**
   * 上报事件至友盟
   *
   * @param context 上下文环境变量
   * @param event 统计事件名称
   * @param map 参数
   */
  public static void onUmengEvent(Context context, String event, Map<String, String> map) {
    if (context == null || TextUtils.isEmpty(event)) {
      return;
    }
    if (map != null && map.size() > 0) {
      MobclickAgent.onEvent(context, event, map);
    } else {
      MobclickAgent.onEvent(context, event);
    }
  }

  /**
   * 上报事件至友盟、武林榜
   *
   * @param context 上下文环境变量
   * @param event 统计事件名称
   */
  public static void onEvent(Context context, String event) {
    onUmengEvent(context, event);
  }


  /**
   * 事件统计
   *
   * @param context 上下文环境变量
   * @param event 统计事件名称
   * @param map 参数
   */
  public static void onEvent(Context context, String event, Map<String, String> map) {
    onUmengEvent(context, event, map);
  }

  public static void onFragmentPageStart(Context context, String fragmentName) {
    if (context == null || TextUtils.isEmpty(fragmentName)) {
      return;
    }
    try {
      Logger.t(TAG).d("onFragmentPageStart：" + fragmentName);
      MobclickAgent.onPageStart(fragmentName);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void onFragmentPageEnd(Context context, String fragmentName) {
    if (context == null || TextUtils.isEmpty(fragmentName)) {
      return;
    }
    try {
      Logger.t(TAG).d("onFragmentPageEnd: " + fragmentName);
      MobclickAgent.onPageEnd(fragmentName);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 上报错误信息至友盟和武林榜
   */
  public static void reportError(Context context, String tag, String message) {
    reportUmengError(context, message);
  }

  /**
   * 上报错误信息至友盟和武林榜
   */
  public static void reportError(Context context, String tag, Throwable throwable) {
    reportUmengError(context, throwable.getMessage());
  }

  /**
   * 上报错误信息至友盟
   *
   * @param error 错误信息
   */
  public static void reportUmengError(Context context, Throwable error) {
    if (context == null || error == null) {
      return;
    }
    try {
      MobclickAgent.reportError(context, error);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 上报错误信息至友盟
   *
   * @param error 错误信息
   */
  public static void reportUmengError(Context context, String error) {
    if (context == null || TextUtils.isEmpty(error)) {
      return;
    }
    try {
      MobclickAgent.reportError(context, error);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}