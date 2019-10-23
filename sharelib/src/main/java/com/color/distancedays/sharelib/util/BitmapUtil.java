package com.color.distancedays.sharelib.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class BitmapUtil {

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

}