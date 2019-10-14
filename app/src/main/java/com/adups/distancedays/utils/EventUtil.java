package com.adups.distancedays.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus工具类
 */
public class EventUtil {

  /**
   * 注册事件
   */
  public static void register(Object context) {
    if (!EventBus.getDefault().isRegistered(context)) {
      EventBus.getDefault().register(context);
    }
  }

  /**
   * 解除
   */
  public static void unregister(Object context) {
    if (EventBus.getDefault().isRegistered(context)) {
      EventBus.getDefault().unregister(context);
    }
  }

  /**
   * 发送事件
   */
  public static void post(Object object) {
    EventBus.getDefault().post(object);
  }

  /**
   * 发送粘性事件
   */
  public static void postSticky(Object object) {
    EventBus.getDefault().postSticky(object);
  }


  /**
   * 中断事件传递
   */
  public static void cancelEvent(Object object) {
    if (null != object) {
      EventBus.getDefault().cancelEventDelivery(object);
    }
  }
}
