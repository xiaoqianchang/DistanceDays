package com.planet.light2345.sharelib.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.planet.light2345.sharelib.bean.ShareImageObject;
import com.planet.light2345.sharelib.bean.ShareObject;
import com.planet.light2345.sharelib.bean.ShareTextObject;
import com.planet.light2345.sharelib.channel.Controller.ControllerListener;
import com.planet.light2345.sharelib.constant.ShareConstant.ErrorType;
import com.planet.light2345.sharelib.constant.ShareConstant.SharePlatform;
import com.planet.light2345.sharelib.constant.ShareConstant.ShareType;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import java.io.File;

/**
 * 处理友盟分享相关 Created by zhangk on 2018/8/21.
 */

public class UMShareUtil {

  private ControllerListener mControllerListener;


  /**
   * 注册分享的结果回调，如果不需要register则不用注册
   */
  public void registerOnActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    if (activity == null) {
      return;
    }
    UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
  }

  /**
   * 开始友盟分享
   */
  public void startUmengShare(Activity context, ShareObject shareObject, ControllerListener shareListener) {
    if (context == null) {
      return;
    }
    if (shareObject == null) {
      return;
    }
    mControllerListener = shareListener;
    SHARE_MEDIA share_media = null;
    switch (shareObject.sharePlatform) {
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
      if (mControllerListener != null) {
        mControllerListener.onHandleError(ErrorType.ERROR_TYPE_SELECT_PLATFORM_FIRST, new Throwable("请拓展分享平台"));
        return;
      }
    }
    switch (shareObject.shareType) {
      case ShareType.TYPE_TEXT:
        if (shareObject instanceof ShareTextObject) {
          ShareTextObject shareTextObject = (ShareTextObject) shareObject;
          UMWeb web = new UMWeb(shareTextObject.targetUrl);
          web.setTitle(TextUtils.isEmpty(shareTextObject.title) ? " " : shareTextObject.title);//标题
          UMImage image = null;
          if (shareTextObject.thumbFile != null) {
            //本地缩略图
            Bitmap bitmap = BitmapUtil.decodeImageFromFile(shareTextObject.thumbFile.getPath());
            image = new UMImage(context, bitmap);
          } else if (!TextUtils.isEmpty(shareTextObject.thumbnailUrl)) {
            //网络缩略图
            image = new UMImage(context, shareTextObject.thumbnailUrl);
          }
          if(image != null){
            web.setThumb(image);  //设置缩略图
          }
          web.setDescription(TextUtils.isEmpty(shareTextObject.content) ? " " : shareTextObject.content);//描述
          new ShareAction(context)
              .setPlatform(share_media)//传入平台
              .withMedia(web)
              .setCallback(mUMShareListener)//回调监听器
              .share();
          break;
        }
      case ShareType.TYPE_IMAGE:
        if (shareObject instanceof ShareImageObject) {
          ShareImageObject shareImageObject = (ShareImageObject) shareObject;
          File file = new File(shareImageObject.imageLocalPath);
          UMImage image = new UMImage(context, file);
          Bitmap bitmap = BitmapUtil.compressImage(BitmapUtil.decodeImageFromFile(file.getPath()));
          if (bitmap == null) {
            image.setThumb(new UMImage(context, file));
          } else {
            image.setThumb(new UMImage(context, bitmap));
          }
          image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
          //压缩格式设置
          image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
          new ShareAction(context)
              .setPlatform(share_media)//传入平台
              .withMedia(image)
              .setCallback(mUMShareListener)//回调监听器
              .share();
          break;
        }
    }
  }


  public void release(Activity activity) {
    if (activity == null) {
      return;
    }
    UMShareAPI.get(activity).release();
  }

  private UMShareListener mUMShareListener = new UMShareListener() {

    @Override
    public void onStart(SHARE_MEDIA share_media) {
      if (mControllerListener != null) {
        mControllerListener.onHandleStart();
      }
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
      if (mControllerListener != null) {
        mControllerListener.onHandleResult();
        com.umeng.socialize.utils.ContextUtil.getContext();
      }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
      if (mControllerListener != null) {
        mControllerListener.onHandleError(ErrorType.ERROR_TYPE_OTHER, throwable);
      }
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
      if (mControllerListener != null) {
        mControllerListener.onHandleCancel();
      }
    }
  };

}
