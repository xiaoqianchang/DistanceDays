package com.adups.distancedays.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.adups.distancedays.R;
import com.adups.distancedays.manager.AppShareManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @desc 分享图片处理
 * @date 2018/8/21
 */
public class ShareImageHelper {

  public static final String TEMP_FILE_NAME = ".temp";//临时文件名

  public static final int DEFAULT_IMG_WIDTH = 1080;//默认分享图片宽
  public static final int DEFAULT_IMG_HEIGHT = 1920;//默认分享图片高
  public static final double DEFAULT_SCALE = (double) DEFAULT_IMG_WIDTH / (double) DEFAULT_IMG_HEIGHT;//分享图片固定宽高比

  public static final double COEFFICIENT_DEFAULT = 1d;//默认缩放系数 1代表不缩放

  /**
   * 邀请分享二维码尺寸：
   */
  public static final int SHARE_QRCODE_IMG_SIZE = 420;
  public static final int SHARE_QRCODE_TOP = 1128;//二维码高度
  public static final int SHARE_CODE_TOP = 1590;//邀请码显示高度
  public static final int SHARE_LOGO_SIZE = 120;//邀请LOGO默认大小
  public static final int SHARE_CODE_TEXT_SIZE = 33;//邀请码字体大小
  public static final int SHOW_CODE_TEXT_COLOR = Color.WHITE;//邀请码颜色。

  /**
   * 邀请召回分享相关尺寸
   */
  public static final int SHARE_REMIND_PORTRAIT_SIZE = 86;//分享召回头像尺寸
  public static final int SHARE_REMIND_PORTRAIT_TOP = 321;//分享召回头像距图像顶部尺寸
  public static final int SHARE_REMIND_TEXT_SIZE = 46;//分享召回顶部字体大小
  public static final int SHARE_REMIND_TEXT_COLOR = Color.parseColor("#666666");//分享召回顶部字体颜色
  public static final int SHARE_REMIND_TEXT_TOP = 380;//分享召回顶部字体距图像顶部尺寸
  public static final int SHARE_REMIND_COIN_SIZE = 160;//分享召回金额字体大小
  public static final int SHARE_REMIND_COIN_UNIT_SIZE = 66;//分享召回金额单位字体大小
  public static final int SHARE_REMIND_COIN_TOP = 680;//分享召回金额字体距图像顶部尺寸
  public static final int SHARE_REMIND_COIN_UNIT_TOP = 764;//分享召回金额单位距图像顶部尺寸
  public static final int SHARE_REMIND_COIN_TEXT_COLOR = Color.parseColor("#ff0000");//分享召回金额字体颜色。

  /**
   * 晒收入分享二维码尺寸：
   */
  public static final int SHARE_INCOME_QRCODE_IMG_SIZE = 246;//二维码尺寸
  public static final int SHARE_INCOME_QRCODE_IMG_SCALE_SIZE = 260;//生成二维码尺寸
  public static final int SHARE_INCOME_QRCODE_TOP = 1599;//二维码顶边距
  public static final int SHARE_INCOME_QRCODE_LEFT = 162;//二维码左边距
  public static final int SHARE_INCOME_QRCODE_BG_SIZE = 300;//二维码底图大小
  public static final int SHARE_INCOME_QRCODE_BG_TOP = 1572;//二维码背景图顶边距
  public static final int SHARE_INCOME_QRCODE_BG_LEFT = 135;//二维码背景图左边距
  public static final int SHARE_INCOME_CODE_TEXT_TOP = 742;//金额顶边距
  public static final int SHARE_INCOME_CODE_TEXT_SIZE = 200;//金额字体大小
  public static final int SHARE_INCOME_CODE_UNIT_TOP = 832;//金额顶边距
  public static final int SHARE_INCOME_LOGO_SIZE = 64;//晒收入LOGO默认大小
  public static final int SHARE_INCOME_CODE_TEXT_UNIT_SIZE = 100;//金额字体大小
  public static final int SHOW_INCOME_CODE_TEXT_COLOR = Color.parseColor("#e44231");//金额字体颜色。

  /**
   * 图片合成失败的原因
   */
  public static final String DEV_SHARE_IMAGE_ERROR_DATA_INVAILD = "data invaild";
  public static final String DEV_SHARE_IMAGE_ERROR_BG = "background error";
  public static final String DEV_SHARE_IMAGE_ERROR_MERGE = "merge error";
  public static final String DEV_SHARE_IMAGE_ERROR_SAVE = "save error";
  public static final String DEV_SHARE_IMAGE_ERROR_RENAME = "rename error";
  public static final String DEV_SHARE_IMAGE_ERROR_QRCODE = "qrcode error";

  /**
   * 合成分享图片
   *
   * @param context
   * @param title
   * @param day
   * @param dueDateStr
   */
  public static void getShareMergeResultImage(Context context, String title, String day, String dueDateStr, OnImageResultListener onImageResultListener) {
    Observable.create((ObservableEmitter<Bitmap> emitter) -> {
      Bitmap mergeBitmap = mergeResultBitmap(context, title, day, dueDateStr);

      Bitmap bitmap = BitmapUtil.qualityCompress(mergeBitmap, 80);
      if (bitmap != null) {
        emitter.onNext(bitmap);
      } else {
        emitter.onNext(null);
      }
      BitmapUtil.recycleBitmap(mergeBitmap);
//      BitmapUtil.recycleBitmap(bitmap);
      emitter.onComplete();
    }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Bitmap>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(Bitmap bitmap) {
                if (null == bitmap) {
                  if (null != onImageResultListener) {
                    onImageResultListener.onImageFail();
                  } else {
                    onFail(context);
                  }
                  return;
                }
                if (null != onImageResultListener) {
                  onImageResultListener.onImageReady(bitmap);
                } else {
                  onSuccess(context, bitmap);
                }
              }

              @Override
              public void onError(Throwable e) {
                if (null != onImageResultListener) {
                  onImageResultListener.onImageFail();
                } else {
                  onFail(context);
                }
              }

              @Override
              public void onComplete() {

              }
            });
  }

  private static Bitmap mergeResultBitmap(Context context, String title, String day, String dueDateStr) {
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_share_bg);

    try {
      int width = bitmap.getWidth();
      int height = bitmap.getHeight();
      Bitmap newBmp = Bitmap.createBitmap(width, height, bitmap.getConfig());
      Canvas canvas = new Canvas(newBmp);
      canvas.drawBitmap(bitmap, 0, 0, null);
      canvas.save();
      Paint paint = new Paint();
      paint.setStrokeWidth(50);
      paint.setColor(Color.WHITE);
      paint.setAlpha(0xff);
      paint.setTextSize(UIUtils.dip2px(context, 15));
      float lengthTtile = paint.measureText(title);
      canvas.drawText(title, (width - lengthTtile) / 2, UIUtils.dip2px(context, 60), paint);

      paint.setColor(Color.parseColor("#FF323232"));
      paint.setTextSize(UIUtils.dip2px(context, 60));
      float lengthDay = paint.measureText(day);
      canvas.drawText(day, (width - lengthDay) / 2, UIUtils.dip2px(context, 140), paint);

      paint.setColor(Color.parseColor("#FF565656"));
      paint.setTextSize(UIUtils.dip2px(context, 11));
      float lengthDueDate = paint.measureText(dueDateStr);
      canvas.drawText(dueDateStr, (width - lengthDueDate) / 2, UIUtils.dip2px(context, 202), paint);
      canvas.restore();
      return newBmp;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 根据屏幕实际尺寸对小屏机器进行图片尺寸等比缩放
   *
   * @return 计算后图片实际尺寸
   */
  private static int calculateRealSize() {
    int screenHeight = UIUtils.getScreenHeight();//屏幕高度
    if (screenHeight >= DEFAULT_IMG_HEIGHT) {//屏幕高度大于底图高度
      return DEFAULT_IMG_HEIGHT;
    }
    return screenHeight;
  }


  /**
   * 二维码生成回调
   */
  public interface OnImageResultListener {

    void onImageReady(Bitmap bitmap);

    void onImageFail();
  }

  /**
   * 合成成功
   */
  private static void onSuccess(Context mContext, Bitmap bitmap) {
    if (bitmap != null) {
      AppShareManager.getInstance().umengShareAction(
              AppShareManager.getInstance().getType(), null, bitmap);
    } else {
      onFail(mContext);
    }
  }

  /**
   * 合成失败
   */
  private static void onFail(Context context) {
    ToastUtil.showToast(context, R.string.share_error_msg);
    AppShareManager.getInstance().dismissLoading();
  }
}
