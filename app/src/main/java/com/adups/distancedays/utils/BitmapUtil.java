package com.adups.distancedays.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import com.orhanobut.logger.Logger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

  /**
   * bitmap旋转
   */
  public static Bitmap adjustPhotoRotation(Bitmap b, int degrees, int width) {
    if (degrees != 0 && b != null) {
      Matrix m = new Matrix();
      m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
      float sf = (float) width / (float) b.getWidth();
      m.postScale(sf, sf);
      try {
        Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
        if (b != b2) {
          b.recycle();
          b = b2;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return b;
  }


  public static Bitmap getBmpFromRes(int id, Context context) {
    try {
      return BitmapFactory.decodeResource(context.getResources(), id);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  public static Bitmap getBitmapByView(View view) {
    Bitmap bitmap = null;
    try {
      bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
      final Canvas canvas = new Canvas(bitmap);
      view.draw(canvas);
//            Matrix matrix = null;
//            if (view.getWidth() > 720) {
//                float scale = 720f / view.getWidth();
//                matrix = new Matrix();
//                matrix.postScale(scale, scale);
//            }
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, view.getWidth(), view.getHeight(),
//                matrix, true);
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return bitmap;
  }

  public static void layoutView(View v, int width, int height) {
    // validate view.width and view.height
    int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
    int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

    // validate view.measurewidth and view.measureheight
    v.measure(measuredWidth, measuredHeight);
    v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
  }

  /**
   * @param save 依然保存图片至目录
   * @param maxSize 单位：byte
   * @return 是否压缩，false:未压缩， true：已压缩
   */
  public static boolean compressAndSaveImage(Bitmap bitmap, int maxSize, String imgPath, boolean save) {
    if (bitmap == null) {
      return false;
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // 质量压缩方法,这里100表示不压缩,把压缩后的数据存放到baos中
    bitmap.compress(CompressFormat.JPEG, 100, baos);

    if (!save && (baos.toByteArray().length < maxSize)) {
      return false;
    }

    int options = 100;
    // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
    while (baos.toByteArray().length >= maxSize && options > 10) {
      // 重置baos
      baos.reset();
      //减少计算次数，防止小内存手机因多次循序更容易产生oom
      options = options / 2;
      // 这里压缩options%,把压缩后的数据存放到baos中
      bitmap.compress(CompressFormat.JPEG, options, baos);
    }

    File file = new File(imgPath);
    try {
//      Log.e("test", "******:save--" +", size = "+ baos.toByteArray().length/ 1024);
      FileOutputStream os = new FileOutputStream(file);
      os.write(baos.toByteArray());
      os.flush();
      os.close();
      baos.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }


  public static Bitmap compressImage(Bitmap bitmap) {
    if (bitmap == null) {
      return null;
    }
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      // 质量压缩方法,这里100表示不压缩,把压缩后的数据存放到baos中
      bitmap.compress(CompressFormat.JPEG, 100, baos);
      int options = 100;
      // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
      while (baos.toByteArray().length / 1024 > 100 && options > 10) {
        // 重置baos
        baos.reset();
        //减少计算次数，防止小内存手机因多次循序更容易产生oom
        options = options * 1 / 2;
        // 这里压缩options%,把压缩后的数据存放到baos中
        bitmap.compress(CompressFormat.JPEG, options, baos);
      }
//		Log.e("test", "quality = " + options+", size = "+baos.toByteArray().length / 1024);
      // 把压缩后的数据baos存放到ByteArrayInputStream中
      ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
      // 把ByteArrayInputStream数据生成图片
      bitmap = BitmapFactory.decodeStream(isBm, null, null);
//		Log.e("test", bitmap.getWidth()+"*"+bitmap.getHeight());
      return bitmap;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  // 从Resources中加载图片
  public static Bitmap decodeSampledBitmapFromResource(Resources res,
      int resId, int reqWidth, int reqHeight) {
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resId, options); // 读取图片长款
    options.inSampleSize = calculateInSampleSize(options, reqWidth,
        reqHeight); // 计算inSampleSize
    options.inJustDecodeBounds = false;
    Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
    return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图
  }

  // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
  private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
      int dstHeight) {
    Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
    if (src != dst) { // 如果没有缩放，那么不回收
      src.recycle(); // 释放Bitmap的native像素数组
    }
    return dst;
  }

  private static int calculateInSampleSize(BitmapFactory.Options options,
      int reqWidth, int reqHeight) {
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
      final int halfHeight = height / 2;
      final int halfWidth = width / 2;
      while ((halfHeight / inSampleSize) > reqHeight
          && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
        if (inSampleSize == 0) {
          break;
        }
      }
    }
    return inSampleSize;
  }

  public static Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth,
      int reqHeight) {
    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFileDescriptor(fd, null, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth,
        reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFileDescriptor(fd, null, options);
  }


  /**
   * 把两个位图覆盖合成为一个位图，上下拼接
   *
   * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
   */
  public static Bitmap mergeBitmap(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

    if (topBitmap == null || topBitmap.isRecycled()
        || bottomBitmap == null || bottomBitmap.isRecycled()) {
      return null;
    }
    int width = 0;
    if (isBaseMax) {
      width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth()
          : bottomBitmap.getWidth();
    } else {
      width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth()
          : bottomBitmap.getWidth();
    }
    Bitmap tempBitmapT = topBitmap;
    Bitmap tempBitmapB = bottomBitmap;

    if (topBitmap.getWidth() != width) {
      //tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int)(topBitmap.getHeight()*1f/topBitmap.getWidth()*width), false);
    } else if (bottomBitmap.getWidth() != width) {
      //tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int)(bottomBitmap.getHeight()*1f/bottomBitmap.getWidth()*width), false);
    }

    int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    canvas.drawColor(Color.WHITE);

    Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
    canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
    //canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
    float offset = UIUtils.dip2px(CommonUtil.getApplication(), 15);
    canvas.drawBitmap(tempBitmapB, (topBitmap.getWidth() - bottomBitmap.getWidth()) / 2,
        topBitmap.getHeight() - offset, null);
    return bitmap;
  }

  /**
   * 合成图片
   *
   * @param firstBitmap 底图
   * @param secondBitmap 附属图
   * @return 合成图
   */
  public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, float left, float top) {
    if (firstBitmap == null || secondBitmap == null) {
      return null;
    }
    try {
      Bitmap bitmap = Bitmap
          .createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(), firstBitmap.getConfig());
      Canvas canvas = new Canvas(bitmap);
      canvas.drawBitmap(firstBitmap, 0, 0, null);
      canvas.drawBitmap(secondBitmap, left, top, null);
      return bitmap;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static boolean savePic(Bitmap bmp, String path) {
    try {
      File file = new File(path);
      File parentFile = file.getParentFile();
      if (!parentFile.exists()) {
        if (!parentFile.mkdirs()) {
          return false;
        }
      }
      FileOutputStream out = new FileOutputStream(file);
      bmp.compress(CompressFormat.JPEG, 100, out);
      out.flush();
      out.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 回收Bitmap
   */
  public static void recycleBitmap(Bitmap bitmap) {
    try {
      if (bitmap != null && !bitmap.isRecycled()) {
        bitmap.recycle();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 判断图片是否已经保存了
   */
  private static boolean isImgAlread(File file, String fileName) {
    if (null == file || TextUtils.isEmpty(fileName)) {
      return false;
    }
    if (file.exists()) {
      File[] files = file.listFiles();
      for (File f : files) {
        if (f.getName().equals(fileName)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 绘制水平居中文字
   *
   * @param oldBmp 背景图
   * @param text 文本
   * @param color 颜色
   * @param size 大小
   * @param top 高度
   */
  public static Bitmap DrawHorizonTextToBitmap(Bitmap oldBmp, String text, int color,
      int size, int top, String familyName) {
    if (oldBmp == null || TextUtils.isEmpty(text)) {
      return null;
    }
    try {
      int width = oldBmp.getWidth();
      int height = oldBmp.getHeight();
      Bitmap newBmp = Bitmap.createBitmap(width, height, oldBmp.getConfig());
      Canvas canvas = new Canvas(newBmp);
      canvas.drawBitmap(oldBmp, 0, 0, null);
      canvas.save();
      Paint paint = new Paint();
      paint.setStrokeWidth(50);
      if (TextUtils.isEmpty(familyName)) {
        familyName = "PingFangSC";
      }
      Typeface font = Typeface.create(familyName, Typeface.NORMAL);
      paint.setColor(color);
      paint.setAlpha(0xff);
      paint.setTextSize(size);
      paint.setTypeface(font);
      float length = paint.measureText(text);
      canvas.drawText(text, (width - length) / 2, top + size, paint);
      canvas.restore();
      return newBmp;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 绘制水平居中文字
   */
  public static Bitmap DrawIncomeTextToBitmap(Bitmap oldBmp, String coin, String unit, int color,
      int bigSize, int smallSize, int bigTop, int smallTop, String familyName) {
    if (oldBmp == null || TextUtils.isEmpty(coin) || TextUtils.isEmpty(unit)) {
      return null;
    }
    try {
      int width = oldBmp.getWidth();
      int height = oldBmp.getHeight();
      Bitmap newBmp = Bitmap.createBitmap(width, height, oldBmp.getConfig());
      Canvas canvas = new Canvas(newBmp);
      canvas.drawBitmap(oldBmp, 0, 0, null);
      canvas.save();
      Paint paint = new Paint();
      paint.setStrokeWidth(50);
      if (TextUtils.isEmpty(familyName)) {
        familyName = "PingFangSC";
      }
      Typeface normal = Typeface.create(familyName, Typeface.BOLD);
      Typeface font = normal;
//      if ("SFUI".equals(familyName)) {
//        font = FontTypeUtils.getInstance().getShareFont();
//      }
      paint.setColor(color);
      paint.setAlpha(0xff);
      paint.setTextSize(bigSize);
      if (font != null) {
        paint.setTypeface(font);
      }
      float length = paint.measureText(coin);
      canvas.drawText(coin, (width - length) / 2, bigTop + bigSize, paint);
      paint.setTypeface(normal);
      paint.setTextSize(smallSize);
      canvas.drawText(unit, ((width - length) / 2) + length, smallTop + smallSize, paint);
      canvas.restore();
      return newBmp;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 绘制邀请召回相关控件
   */
  public static Bitmap drawInviteRemindBitmap(String friendStr, String nameStr, Bitmap oldBmp,
      Bitmap headerBitmap, int headerSize, int headerTop, int remindFontSize, int color, int smallTop, String familyName) {
    if (oldBmp == null || TextUtils.isEmpty(friendStr) || TextUtils.isEmpty(nameStr)) {
      return null;
    }
    try {
      int width = oldBmp.getWidth();
      int height = oldBmp.getHeight();
      Bitmap newBmp = Bitmap.createBitmap(width, height, oldBmp.getConfig());
      Canvas canvas = new Canvas(newBmp);
      Paint paint = new Paint();
      paint.setStrokeWidth(50);
      if (TextUtils.isEmpty(familyName)) {
        familyName = "PingFangSC";
      }
      Typeface font = Typeface.create(familyName, Typeface.NORMAL);
      paint.setColor(color);
      paint.setAlpha(0xff);
      paint.setTextSize(remindFontSize);
      paint.setTypeface(font);
      float length = paint.measureText(friendStr);
      canvas.drawText(friendStr, 0, smallTop, paint);
      canvas.save();
      if (null != headerBitmap) {
        canvas.drawBitmap(headerBitmap, length, headerTop, null);
        canvas.save();
      }
      canvas.drawText(nameStr, length + (headerBitmap == null ? 0 : headerSize), smallTop, paint);
      canvas.save();
      canvas.restore();
      float textLength = paint.measureText(friendStr + nameStr);
      float totalLength = textLength + (headerBitmap == null ? 0 : headerSize);
      if (null != headerBitmap) {
        recycleBitmap(headerBitmap);
      }
      return mergeBitmap(oldBmp, newBmp, (width - totalLength) / 2, 0);
    } catch (Exception e) {
      e.printStackTrace();
      return oldBmp;
    }
  }

  /**
   * 3.质量压缩 设置bitmap options属性，降低图片的质量，像素不会减少 第一个参数为需要压缩的bitmap图片对象，第二个参数为压缩后图片保存的位置 设置options 属性0-100，来实现压缩
   */
  public static void qualityCompress(Bitmap bmp, File file) {
    qualityCompress(bmp, file, 90);
  }

  public static boolean qualityCompress(Bitmap bmp, File file, int quality_level) {
    if (bmp == null) {
      return false;
    }
    // 0-100 100为不压缩
    int quality = quality_level;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // 把压缩后的数据存放到baos中
    try {
      bmp.compress(CompressFormat.PNG, quality, baos);
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(baos.toByteArray());
      fos.flush();
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static Bitmap qualityCompress(Bitmap bmp, int quality_level) {
    if (bmp == null) {
      return null;
    }
    // 0-100 100为不压缩
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // 把压缩后的数据存放到baos中
    try {
      bmp.compress(CompressFormat.JPEG, quality_level, baos);
      byte[] bytes = baos.toByteArray();
      return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String saveBitmapAsFile(String name, String path, Bitmap bitmap) {
    if (bitmap == null) {
      return null;
    }
    if (TextUtils.isEmpty(path)) {
      return null;
    }
    File saveFile = new File(path, name);
    FileOutputStream os = null;
    try {
      Logger.d("FileCache" + "Saving File To Cache " + saveFile.getPath());
      os = new FileOutputStream(saveFile);
      bitmap.compress(CompressFormat.JPEG, 100, os);
      os.flush();
      os.close();
      return saveFile.getAbsolutePath();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Bitmap decodeImageFromFile(String filePath) {
    if (TextUtils.isEmpty(filePath)) {
      return null;
    }
    Bitmap bitmap;
    try {
      bitmap = BitmapFactory.decodeFile(filePath);
      return bitmap;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 按新的宽高缩放图片
   */
  public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight) {
    if (bm == null) {
      return null;
    }
    try {
      int width = bm.getWidth();
      int height = bm.getHeight();
      float scaleWidth = ((float) newWidth) / width;
      float scaleHeight = ((float) newHeight) / height;
      Matrix matrix = new Matrix();
      matrix.postScale(scaleWidth, scaleHeight);
      return Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
          true);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}