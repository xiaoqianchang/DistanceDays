package com.adups.distancedays.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.adups.distancedays.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Glide加载工具类
 */
public class GlideUtil {

  public static void loadImage(Context context, byte[] bytes, ImageView imageView,
      RequestOptions options) {
    if (ContextUtils.checkContext(context) && bytes != null && imageView != null && options != null) {
      Glide.with(context).load(bytes).apply(options).into(imageView);
    }
  }

  public static void loadImage(Context context, String url, ImageView imageView, RequestOptions
      options) {
    if (ContextUtils.checkContext(context) && imageView != null && options != null) {
      Glide.with(context).load(url).apply(options).into(imageView);
    }
  }

  public static void loadImage(Context context, String url, ImageView imageView, RequestOptions
      options, RequestListener requestListener) {
    if (ContextUtils.checkContext(context)
        && imageView != null && options != null && null != requestListener) {
      Glide.with(context).load(url).apply(options).listener(requestListener).into(imageView);
    }
  }

  public static void loadImage(Fragment fragment, String url, ImageView imageView, RequestOptions
      options) {
    if (fragment == null) {
      if (BuildConfig.DEBUG) {
        throw new NullPointerException("Glide loadImage, fragment is null.");
      }
      if (imageView != null) {
        Glide.with(CommonUtil.getApplication()).load(url).apply(options).into(imageView);
      }
    } else {
      if (fragment.isAdded() && imageView != null) {
        Glide.with(fragment).load(url).apply(options).into(imageView);
      }
    }
  }

  public static void loadImage(Context context, int resourceId, ImageView imageView, RequestOptions options) {
    if (ContextUtils.checkContext(context) && imageView != null && options != null) {
      Glide.with(context).load(resourceId).apply(options).into(imageView);
    }
  }

  public static void loadImage(Context context, int resourceId, ImageView imageView) {
    if (ContextUtils.checkContext(context) && imageView != null) {
      Glide.with(context).load(resourceId).into(imageView);
    }
  }

  public static void loadImage(Context context, Drawable drawable, ImageView imageView, RequestOptions
      options) {
    if (ContextUtils.checkContext(context) && null != drawable && imageView != null && options != null) {
      Glide.with(context).load(drawable).apply(options).into(imageView);
    }
  }

  public static void loadImage(Context context, byte[] bytes, ImageView imageView) {
    if (ContextUtils.checkContext(context) && null != bytes && imageView != null) {
      Glide.with(context).load(bytes).apply(getDefaultOptions()).into(imageView);
    }
  }

  public static void loadImage(Context context, String url, ImageView imageView) {
    if (ContextUtils.checkContext(context) && imageView != null) {
      Glide.with(context).load(url).apply(getDefaultOptions()).into(imageView);
    }
  }

  /**
   * 加载图片，原始大小，没有默认图
   */
  public static void loadImageNoCropOptions(Context context, String url, ImageView imageView) {
    if (ContextUtils.checkContext(context) && imageView != null) {
      RequestOptions options = new RequestOptions();
      options.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
      Glide.with(context).load(url).apply(options).into(imageView);
    }
  }

  /**
   * 加载图片，原始大小，没有默认图
   */
  public static void loadImageWithCropOriginalOptions(Context context, String url, ImageView imageView, RequestOptions options) {
    if (ContextUtils.checkContext(context) && imageView != null && options != null) {
      options.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
      Glide.with(context).load(url).apply(options).into(imageView);
    }
  }

  /**
   * 加载图片，回调方式设置图片，需手动设置
   */
  public static void loadImage(Context context, String url, final ImageCallback callback) {
    if (ContextUtils.checkContext(context)) {
      Glide.with(context).load(url).into(new SimpleTarget<Drawable>() {

        @Override
        public void onResourceReady(@NonNull Drawable resource,
            @Nullable Transition<? super Drawable> transition) {
          if (callback != null) {
            callback.onResourceReady(resource);
          }
        }
      });
    }
  }

  /**
   * 预下载图片
   */
  public static void preload(Context context, String url) {
    if (ContextUtils.checkContext(context)) {
      Glide.with(context).load(url).preload();
    }
  }

  public static void preload(Context context, String url, final ImageCallback callback) {
    if (ContextUtils.checkContext(context)) {
      Glide.with(context).load(url).listener(new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
          return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
          if (callback != null) {
            callback.onResourceReady(resource);
          }
          return false;
        }
      }).preload();
    }
  }

  public static void loadImage(Fragment fragment, String url, ImageView imageView) {
    if (fragment == null) {
      if (BuildConfig.DEBUG) {
        throw new NullPointerException("Glide loadImage, fragment is null.");
      }
      if (imageView != null) {
        Glide.with(CommonUtil.getApplication()).load(url).apply(getDefaultOptions()).into(imageView);
      }
    } else {
      if (fragment.isAdded() && imageView != null) {
        Glide.with(fragment).load(url).apply(getDefaultOptions()).into(imageView);
      }
    }
  }

  public static void loadImage(Context context, String url, BaseTarget target) {
    if (ContextUtils.checkContext(context)) {
      Glide.with(context).load(url).into(target);
    }
  }

  public static void loadImageAsBitmap(Context context, String url, BaseTarget target) {
    if (ContextUtils.checkContext(context)) {
      Glide.with(context).asBitmap().load(url).into(target);
    }
  }

  /**
   * 加载图片url，自定义配置，基础对象
   */
  public static void loadImage(Context context, String url, RequestOptions options, BaseTarget target) {
    if (ContextUtils.checkContext(context) && null != options) {
      Glide.with(context).asBitmap().load(url).apply(options).into(target);
    }
  }

  /**
   * 加载图片url，自定义配置，基础对象
   */
  public static void loadImage(Context context, int resource, RequestOptions options, BaseTarget target) {
    if (ContextUtils.checkContext(context) && null != options) {
      Glide.with(context).asBitmap().load(resource).apply(options).into(target);
    }
  }

  public static void loadImage(Context context, int id, BaseTarget target) {
    if (ContextUtils.checkContext(context) && null != target) {
      Glide.with(context).asBitmap().load(id).into(target);
    }
  }

  public static void loadImage(Context context, File file, ImageView target) {
    if (ContextUtils.checkContext(context) && file != null && null != target) {
      Glide.with(context).load(file).into(target);
    }
  }

  public static void loadImage(Context context, File file, ImageView target, RequestOptions
      options) {
    if (ContextUtils.checkContext(context) && file != null && null != target && null != options) {
      Glide.with(context).load(file).apply(options).into(target);
    }
  }

  public static void loadImageBitmap(Context context, int id, ImageView target) {
    if (ContextUtils.checkContext(context) && null != target) {
      Glide.with(context).asBitmap().load(id).into(target);
    }
  }

  public static void loadImageBitmap(Context context, Bitmap bitmap, ImageView target) {
    if (ContextUtils.checkContext(context) && null != target) {
      Glide.with(context).load(bitmap).into(target);
    }
  }

  public static void loadImageBitmap(Context context, Bitmap bitmap, ImageView target
      , RequestListener requestListener
      , RequestOptions
      options) {
    if (ContextUtils.checkContext(context) && null != target
        && null != requestListener
        && null != options) {
      Glide.with(context).load(bitmap).apply(options)
          .listener(requestListener)
          .into(target);
    }
  }

  public static void loadGif(Context context, String filePath, ImageView target) {
    if (ContextUtils.checkContext(context) && target != null) {
      Glide.with(context).asGif().load(filePath).into(target);
    }
  }

  public static RequestOptions getDefaultOptions() {
    return getDynamicOptions(R.mipmap.ic_article_default_bg, R.mipmap.ic_article_default_bg);
  }

  public static RequestOptions getNoDefaultOptions() {
    return getDynamicOptions(-1, -1);
  }

  public static RequestOptions getDynamicOptions(int placeHoldRes, int errorRes) {
    return getDynamicOptions(false, placeHoldRes, errorRes);
  }

  public static RequestOptions getDynamicOptions(boolean isNeedCircle, int placeHoldRes, int errorRes) {
//通过RequestOptions扩展功能
    RequestOptions options = isNeedCircle ? RequestOptions.circleCropTransform() : new RequestOptions();
    if (placeHoldRes > 0) {
      options.placeholder(placeHoldRes); //R.color.common_default_color_bg
    }
    if (errorRes > 0) {
      options.error(errorRes); //R.color.common_default_color_bg
    }
    options.skipMemoryCache(false);
    options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    return options;
  }

  public static RequestOptions getDynamicOptions(int radius, int placeHoldRes, int errorRes) {
    RequestOptions options = getDynamicOptions(false, placeHoldRes, errorRes);
    if (radius > 0) {
      options.transform(new RoundedCorners(radius));
    }
    return options;
  }

  public static void init(Application application) {
    Glide.get(application);
  }
}
