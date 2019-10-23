package com.color.distancedays.sharelib.helper;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.color.distancedays.sharelib.ShareManager;
import com.color.distancedays.sharelib.bean.ShareObject;
import com.color.distancedays.sharelib.channel.BaseChannelActivity;
import com.color.distancedays.sharelib.channel.qq.QQChannelActivity;
import com.color.distancedays.sharelib.channel.wx.WXChannelActivity;
import com.color.distancedays.sharelib.constant.ShareConstant.ErrorType;
import com.color.distancedays.sharelib.constant.ShareConstant.SharePlatform;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by zhangk on 2018/8/21.
 */

public class ShareController {

  public void startShare(Activity activity, ShareObject shareObject) {
    if (shareObject == null) {
      if (ShareManager.getInstance().getShareListener() != null) {
        ShareManager.getInstance().getShareListener().onError(0, ErrorType.ERROR_TYPE_SHARE_OBJECT_NOT_NULL, new Throwable("ShareObject不能是null"));
      }
      return;
    }
    int platform = shareObject.sharePlatform;
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
    if (activity == null) {
      if (ShareManager.getInstance().getShareListener() != null) {
        ShareManager.getInstance().getShareListener().onError(platform, ErrorType.ERROR_TYPE_ACTIVITY_NOT_NULL, new Throwable("Activity 不能为null"));
      }
      return;
    }
    if (share_media == null) {
      if (ShareManager.getInstance().getShareListener() != null) {
        ShareManager.getInstance().getShareListener().onError(platform, ErrorType.ERROR_TYPE_SELECT_PLATFORM_FIRST, new Throwable("请选择分享平台"));
      }
      return;
    }
    boolean install = UMShareAPI.get(activity).isInstall(activity, share_media);
    if (!install) {
      if (ShareManager.getInstance().getShareListener() != null) {
        ShareManager.getInstance().getShareListener().onError(platform, ErrorType.ERROR_TYPE_NOT_INSTALLED, new Throwable("未安装"));
      }
      return;
    }
    Class<?> channelActivity = null;
    if (!TextUtils.isEmpty(shareObject.shareWindow)) {
      try {
        channelActivity = Class.forName(shareObject.shareWindow);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    if (channelActivity == null) {
      switch (platform) {
        case SharePlatform.QQ:
        case SharePlatform.QQ_ZONE:
          channelActivity = QQChannelActivity.class;
          break;
        case SharePlatform.WX:
        case SharePlatform.WX_TIMELINE:
          channelActivity = WXChannelActivity.class;
          break;
      }
    }

    if (channelActivity != null) {
      Intent intent = BaseChannelActivity.newIntent(activity, shareObject, channelActivity);
      activity.startActivity(intent);
    } else {
      if (ShareManager.getInstance().getShareListener() != null) {
        ShareManager.getInstance().getShareListener().onError(platform, ErrorType.ERROR_TYPE_SELECT_PLATFORM_FIRST, new Throwable("请选择分享平台"));
      }
    }
  }
}
