package com.color.distancedays.sharelib;

import com.color.distancedays.sharelib.listener.OauthListener;

/**
 * 微信授权管理类
 *
 * Created by Chang.Xiao on 2019/2/21.
 *
 * @version 1.0
 */
public class OauthManager {

  private static OauthManager mInstance;

  private OauthListener oauthListener;

  public static OauthManager getInstance() {
    if (mInstance == null) {
      synchronized (OauthManager.class) {
        if (mInstance == null) {
          mInstance = new OauthManager();
        }
      }
    }
    return mInstance;
  }

  public OauthListener getOauthListener() {
    return oauthListener;
  }

  public void setOauthListener(OauthListener oauthListener) {
    this.oauthListener = oauthListener;
  }
}
