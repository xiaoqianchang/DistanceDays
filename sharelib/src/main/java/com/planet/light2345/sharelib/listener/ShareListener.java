package com.planet.light2345.sharelib.listener;

/**
 * Created by zhangk on 2018/8/21.
 */

public interface ShareListener {

  void onStart(int platform);

  void onResult(int platform);

  void onError(int platform, int errorType, Throwable throwable);

  void onCancel(int platform);

}
