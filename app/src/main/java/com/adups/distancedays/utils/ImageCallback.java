package com.adups.distancedays.utils;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

/**
 * 图片加载回调
 * @author chenyi
 * @date 2018/9/11
 */
public interface ImageCallback {
  void onResourceReady(@Nullable Drawable resource);
}
