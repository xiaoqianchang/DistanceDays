package com.color.distancedays.sharelib.constant;


/**
 * Created by zhangk on 2018/8/20.
 */

public class ShareConstant {

  ////////////////分享平台
  public static class SharePlatform {

    public static final int PLATFORM_INVALID = -1; //无效的分享平台
    public static final int QQ = 1;   //QQ
    public static final int QQ_ZONE = 2;  //QQ空间

    public static final int WX = 3;  //微信
    public static final int WX_TIMELINE = 4;  //朋友圈

    public static final int SAVE_PIC = 5;  //保存
  }

  /////////////////分享的类型
  public static class ShareType {

    public static final int TYPE_TEXT = 100;    //纯文本
    public static final int TYPE_IMAGE = 101;       //     纯图片
    public static final int TYPE_FILE = 102;        //     文件
    public static final int TYPE_WEB_PAGE = 103;     //网页

  }


  /////////////////分享平台的包名
  public static class PlatformPackageName {

    public static final String QQ = "com.tencent.mobileqq";

    public static final String WCHAT = "com.tencent.mm";

  }

  ///////////////////错误类型
  public static class ErrorType {

    public static final int ERROR_TYPE_OTHER = 0;
    public static final int ERROR_TYPE_NOT_INSTALLED = 1;
    public static final int ERROR_TYPE_SELECT_PLATFORM_FIRST = 2;
    public static final int ERROR_TYPE_SHARE_OBJECT_NOT_NULL = 3;
    public static final int ERROR_TYPE_ACTIVITY_NOT_NULL = 4;
    public static final int ERROR_TYPE_AUTHORIZE_FAILED = 5;
    public static final int ERROR_TYPE_REQUEST_USER_PROFILE_FAILED = 6;
    public static final int ERROR_TYPE_USER_CANCEL = 7;
  }


}
