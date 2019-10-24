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
   * 合成分享图片
   *  @param context
   * @param bgResId
   * @param title
   * @param day
   * @param dueDateStr
   */
  public static void getShareMergeResultImage(Context context, int bgResId, String title, String day, String dueDateStr, OnImageResultListener onImageResultListener) {
    Observable.create((ObservableEmitter<Bitmap> emitter) -> {
      Bitmap mergeBitmap = mergeResultBitmap(context, bgResId, title, day, dueDateStr);

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

  private static Bitmap mergeResultBitmap(Context context, int bgResId, String title, String day, String dueDateStr) {
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), bgResId);

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
