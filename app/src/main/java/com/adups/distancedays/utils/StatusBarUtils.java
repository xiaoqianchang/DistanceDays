package com.adups.distancedays.utils;

import android.app.Activity;
import com.gyf.barlibrary.ImmersionBar;

/**
 * 自定义状态栏工具类
 */
public class StatusBarUtils {

  // 深色字体设置失败时默认兼容状态栏透明度
  private static final float STATUS_ALPHA_DARK = 0.2f;
  // 浅色字体设置失败时默认兼容状态栏透明度
  private static final float STATUS_ALPHA_LIGHT = 0.2f;

  /**
   * 状态栏透明
   *
   * @param activity 需要处理透明的页面
   * @param darkFont 页面字体颜色（支持android 6.0）
   */
  public static void initStatusBar(Activity activity, boolean darkFont) {
    if (activity == null) {
      return;
    }
    initStatusBar(activity, darkFont, darkFont ? STATUS_ALPHA_DARK : STATUS_ALPHA_LIGHT);
  }

  /**
   * 状态栏透明
   *
   * @param activity 需要处理透明的页面
   * @param darkFont 页面字体颜色（支持android 6.0）
   * @param statusAlpha 当字体颜色设置失效时，改变状态栏透明度，兼容式处理
   */
  public static void initStatusBar(Activity activity, boolean darkFont, float statusAlpha) {
    if (activity == null) {
      return;
    }
    if (ImmersionBar.isSupportNavigationIconDark()) {
      ImmersionBar.with(activity).statusBarDarkFont(darkFont, statusAlpha).navigationBarDarkIcon(true).navigationBarColor("#FFFFFF").init();
    } else {
      ImmersionBar.with(activity).statusBarDarkFont(darkFont, statusAlpha).init();
    }
  }

  /**
   * 释放资源
   */
  public static void destroy(Activity activity) {
    if (activity == null) {
      return;
    }
    ImmersionBar.with(activity).destroy();
  }
}
