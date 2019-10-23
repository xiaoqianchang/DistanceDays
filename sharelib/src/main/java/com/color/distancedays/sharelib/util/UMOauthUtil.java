package com.color.distancedays.sharelib.util;

import static com.color.distancedays.sharelib.listener.OauthListener.ACTION_AUTHORIZE;
import static com.color.distancedays.sharelib.listener.OauthListener.ACTION_DELETE;
import static com.color.distancedays.sharelib.listener.OauthListener.ACTION_GET_PROFILE;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.color.distancedays.sharelib.constant.ShareConstant.ErrorType;
import com.color.distancedays.sharelib.constant.ShareConstant.SharePlatform;
import com.color.distancedays.sharelib.listener.OauthListener;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import java.util.Map;

/**
 * @desc 处理友盟授权相关
 * @date 2018/9/27
 */
public class UMOauthUtil {

  private static final String TAG = "UMOauthUtil";

  public static String LISTENRNULL = "[SCH10005]监听器为空";

  public static void getPlatformInfo(final Activity activity, final int platform, final OauthListener oauthListener) {
    if (activity == null) {
      getOauthListener(oauthListener).onError(platform, ACTION_AUTHORIZE, ErrorType.ERROR_TYPE_ACTIVITY_NOT_NULL, new Throwable("Activity 不能为null"));
      return;
    }
    SHARE_MEDIA share_media = null;
    switch (platform) {
      case SharePlatform.QQ:
        share_media = SHARE_MEDIA.QQ;
        break;
      case SharePlatform.WX:
        share_media = SHARE_MEDIA.WEIXIN;
        break;
    }
    if (share_media == null) {
      getOauthListener(oauthListener).onError(platform, ACTION_AUTHORIZE, ErrorType.ERROR_TYPE_SELECT_PLATFORM_FIRST, new Throwable("没有匹配的授权平台"));
      return;
    }
    UMShareAPI.get(activity).getPlatformInfo(activity, share_media, new UMAuthListener() {
      @Override
      public void onStart(SHARE_MEDIA share_media) {
        getOauthListener(oauthListener).onStart(platform);
      }

      @Override
      public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        getOauthListener(oauthListener).onComplete(platform, getActionStep(i), map);
      }

      @Override
      public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        String message = throwable != null ? throwable.getMessage() : "";
        getOauthListener(oauthListener).onError(platform, getActionStep(i), getErrorType(message), throwable);
      }

      @Override
      public void onCancel(SHARE_MEDIA share_media, int i) {
        getOauthListener(oauthListener).onCancel(platform, getActionStep(i));
      }
    });
  }

  public static void doOauthVerify(final Activity activity, final int platform, boolean needAgainOauth, final OauthListener oauthListener) {
    if (activity == null) {
      getOauthListener(oauthListener).onError(platform, ACTION_AUTHORIZE, ErrorType.ERROR_TYPE_ACTIVITY_NOT_NULL, new Throwable("Activity 不能为null"));
      return;
    }
    SHARE_MEDIA share_media = null;
    switch (platform) {
      case SharePlatform.QQ:
        share_media = SHARE_MEDIA.QQ;
        break;
      case SharePlatform.WX:
        share_media = SHARE_MEDIA.WEIXIN;
        break;
    }
    if (share_media == null) {
      getOauthListener(oauthListener).onError(platform, ACTION_AUTHORIZE, ErrorType.ERROR_TYPE_SELECT_PLATFORM_FIRST, new Throwable("没有匹配的授权平台"));
      return;
    }
    if (needAgainOauth && UMShareAPI.get(activity).isAuthorize(activity, share_media)) {
      UMShareAPI.get(activity).deleteOauth(activity, share_media, null);
    }
    UMShareAPI.get(activity).doOauthVerify(activity, share_media, new UMAuthListener() {
      @Override
      public void onStart(SHARE_MEDIA share_media) {
        getOauthListener(oauthListener).onStart(platform);
      }

      @Override
      public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        getOauthListener(oauthListener).onComplete(platform, getActionStep(i), map);
      }

      @Override
      public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        String message = throwable != null ? throwable.getMessage() : "";
        getOauthListener(oauthListener).onError(platform, getActionStep(i), getErrorType(message), throwable);
      }

      @Override
      public void onCancel(SHARE_MEDIA share_media, int i) {
        getOauthListener(oauthListener).onCancel(platform, getActionStep(i));
      }
    });
  }

  /**
   * 通过umeng抛出的授权步骤，映射到本平台
   *
   * @param action
   * @return
   */
  private static int getActionStep(int action) {
    switch (action) {
      case UMAuthListener.ACTION_AUTHORIZE:
        return ACTION_AUTHORIZE;
      case UMAuthListener.ACTION_DELETE:
        return ACTION_DELETE;
      case UMAuthListener.ACTION_GET_PROFILE:
        return ACTION_GET_PROFILE;
      default:
        return ACTION_AUTHORIZE;
    }
  }

  /**
   * 通过umeng抛出的错误，映射到本平台的错误类型
   *
   * @param message
   * @return
   */
  public static int getErrorType(String message) {
    if (TextUtils.isEmpty(message)) {
      return ErrorType.ERROR_TYPE_OTHER;
    }
    for (UmengErrorCode code : UmengErrorCode.values()) {
      if (message.equals(code.getMessage())) {
        switch (code) {
          case UnKnowCode:
            return ErrorType.ERROR_TYPE_OTHER;
          case AuthorizeFailed:
            return ErrorType.ERROR_TYPE_AUTHORIZE_FAILED;
          case RequestForUserProfileFailed:
            return ErrorType.ERROR_TYPE_REQUEST_USER_PROFILE_FAILED;
          case NotInstall:
            return ErrorType.ERROR_TYPE_NOT_INSTALLED;
        }
      }
    }
    return ErrorType.ERROR_TYPE_OTHER;
  }

  /**
   * 获取用户信息时是否需要重新授权
   *
   * @param context
   * @param isNeedAuthOnGetUserInfo
   */
  public static void setNeedAuthOnGetUserInfo(Context context, boolean isNeedAuthOnGetUserInfo) {
    UMShareConfig config = new UMShareConfig();
    config.isNeedAuthOnGetUserInfo(isNeedAuthOnGetUserInfo);
    UMShareAPI.get(context).setShareConfig(config);
  }

  public static void release(Activity activity) {
    if (activity == null) {
      return;
    }
    UMShareAPI.get(activity).release();
  }

  public static OauthListener getOauthListener(OauthListener oauthListener) {
    return oauthListener != null ? oauthListener : new OauthListener() {
      @Override
      public void onStart(int platform) {

      }

      @Override
      public void onComplete(int platform, int action, Map<String, String> var3) {
        Log.e(TAG, LISTENRNULL);
      }

      @Override
      public void onError(int platform, int action, int errorType, Throwable var3) {
        Log.e(TAG, LISTENRNULL);
      }

      @Override
      public void onCancel(int platform, int action) {
        Log.e(TAG, LISTENRNULL);
      }
    };
  }
}
