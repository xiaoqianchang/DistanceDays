package com.adups.distancedays.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/22.
 *
 * @version 1.0
 */
public class UIUtils {

    public UIUtils() {
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int dip2px(@NonNull Context context, float dpValue) {
        if (context == null) {
            return 0;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5F);
        }
    }

    public static int px2dip(@NonNull Context context, float pxValue) {
        if (context == null) {
            return 0;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5F);
        }
    }

    public static int sp2px(@NonNull Context context, float spValue) {
        if (context == null) {
            return 0;
        } else {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5F);
        }
    }

    public static int px2sp(@NonNull Context context, float pxValue) {
        if (context == null) {
            return 0;
        } else {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5F);
        }
    }

    public static int getViewWidth(@NonNull View view) {
        if (view == null) {
            return 0;
        } else {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(1073741823, -2147483648);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1073741823, -2147483648);
            view.measure(widthMeasureSpec, heightMeasureSpec);
            return view.getMeasuredWidth();
        }
    }

    public static int getViewHeight(@NonNull View view) {
        if (view == null) {
            return 0;
        } else {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(1073741823, -2147483648);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1073741823, -2147483648);
            view.measure(widthMeasureSpec, heightMeasureSpec);
            return view.getMeasuredHeight();
        }
    }

    public static int getStringWidth(String string, float textSize) {
        if (TextUtils.isEmpty(string)) {
            return 0;
        } else {
            Paint paint = new Paint();
            paint.setTextSize(textSize);
            Rect rect = new Rect();
            paint.getTextBounds(string, 0, string.length(), rect);
            return rect.width();
        }
    }

    public static int getDisplaySizeDensityDpi(@NonNull Context context) {
        if (context == null) {
            return 0;
        } else {
            try {
                DisplayMetrics mDisplayMetrics = new DisplayMetrics();
                WindowManager wm = (WindowManager) context.getSystemService("window");
                wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
                return mDisplayMetrics.densityDpi;
            } catch (Exception var3) {
                var3.printStackTrace();
                return 0;
            }
        }
    }

    public static void setLargeTouchDelegate(final View view, final int top, final int left, final int right, final int bottom) {
        if (view != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    int width = view.getWidth();
                    if (width > 0) {
                        Rect delegateArea = new Rect();
                        Context context = view.getContext();
                        view.getHitRect(delegateArea);
                        delegateArea.bottom += UIUtils.dip2px(context, (float) bottom);
                        delegateArea.right += UIUtils.dip2px(context, (float) right);
                        delegateArea.top -= UIUtils.dip2px(context, (float) top);
                        delegateArea.left -= UIUtils.dip2px(context, (float) left);
                        TouchDelegate touchDelegate = new TouchDelegate(delegateArea, view);
                        ViewParent parent = view.getParent();
                        if (View.class.isInstance(parent)) {
                            ((View) parent).setTouchDelegate(touchDelegate);
                        }

                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                }
            });
        }
    }

    public static void startAnimationDrawable(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
        }

    }

    public static Spanned fromHtml(String textHtml) {
        Spanned textSpanned = null;
        if (!TextUtils.isEmpty(textHtml)) {
            if (Build.VERSION.SDK_INT > 24) {
                textSpanned = Html.fromHtml(textHtml, 0);
            } else {
                textSpanned = Html.fromHtml(textHtml);
            }
        }

        return textSpanned;
    }

    public static void avoidRepeatClick(View view) {
        avoidRepeatClick(view, 100);
    }

    public static void avoidRepeatClick(final View view, int delayMillis) {
        if (view != null) {
            if (delayMillis > 500) {
                delayMillis = 100;
            }

            view.setEnabled(false);
            view.postDelayed(new Runnable() {
                public void run() {
                    if (view != null) {
                        view.setEnabled(true);
                    }

                }
            }, (long) delayMillis);
        }
    }
}
