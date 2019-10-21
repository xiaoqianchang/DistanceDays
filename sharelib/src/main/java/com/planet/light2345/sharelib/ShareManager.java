package com.planet.light2345.sharelib;

import android.app.Activity;
import android.app.Application;
import com.planet.light2345.sharelib.bean.ShareObject;
import com.planet.light2345.sharelib.constant.ShareConstant.SharePlatform;
import com.planet.light2345.sharelib.helper.ShareController;
import com.planet.light2345.sharelib.listener.ShareListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 对外暴露的管理类
 * Created by zhangk on 2018/8/21.
 */

public class ShareManager {

  private static volatile ShareManager mInstance;

  private ShareManager() {
  }

  public static ShareManager getInstance() {
    if (mInstance == null) {
      synchronized (ShareManager.class) {
        if (mInstance == null) {
          mInstance = new ShareManager();
        }
      }
    }
    return mInstance;
  }

  /**
   * 初始化分享sdk
   */
  public static void initShare(Application application, String weixinId, String weixinSecret, String qqId, String qqSecret) {

    if (!UMConfigure.getInitStatus()) {
      UMConfigure.init(application
          , null, null, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    // 配置三方平台的appkey
    PlatformConfig.setWeixin(weixinId, weixinSecret);
    // manifest同步配置
    PlatformConfig.setQQZone(qqId, qqSecret);

  }


  /**
   * 暴露给外面调用的方法
   */
  public void shareTo(Activity activity, ShareObject shareObject, ShareListener shareListener) {
    mShareListener = shareListener;
    new ShareController().startShare(activity, shareObject);
  }

  /**
   * 判断分享平台是否已安装
   *
   * @param activity
   * @param platform
   * @return
   */
  public boolean isInstall(Activity activity, int platform) {
    if (activity == null) {
      return false;
    }
    SHARE_MEDIA share_media = null;
    switch (platform) {
      case SharePlatform.QQ:
        share_media = SHARE_MEDIA.QQ;
        break;
      case SharePlatform.WX:
        share_media = SHARE_MEDIA.WEIXIN;
        break;
      case SharePlatform.WX_TIMELINE:
        share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
        break;
    }
    if (share_media == null) {
      return false;
    }
    return UMShareAPI.get(activity).isInstall(activity, share_media);
  }

  private ShareListener mShareListener;

  public ShareListener getShareListener() {
    return mShareListener;
  }

  public void release(){
    mShareListener = null;
  }

}
