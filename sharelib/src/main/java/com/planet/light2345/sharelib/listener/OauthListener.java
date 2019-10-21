package com.planet.light2345.sharelib.listener;

import java.util.Map;

/**
 * @desc 友盟授权回调
 * @date 2018/9/27
 */
public interface OauthListener {

  int ACTION_AUTHORIZE = 0;
  int ACTION_DELETE = 1;
  int ACTION_GET_PROFILE = 2;

  void onStart(int platform);

  void onComplete(int platform, int action, Map<String, String> var3);

  void onError(int platform, int action, int errorType, Throwable var3);

  void onCancel(int platform, int action);
}
