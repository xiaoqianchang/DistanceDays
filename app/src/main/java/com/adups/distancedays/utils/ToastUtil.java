package com.adups.distancedays.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.adups.distancedays.base.DaysApplication;

/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/14.
 *
 * @version 1.0
 */
public class ToastUtil {

    private static final int NORMAL_BG_COLOR = Color.parseColor("#ffffff");
    private static final int NORMAL_TEXT_COLOR = Color.parseColor("#000000");
    private static int sNormalTextColor = NORMAL_TEXT_COLOR;
    private static int sNormalBgColor = NORMAL_BG_COLOR;

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void showToast(String text) {
        showToast(null, text);
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT, sNormalTextColor, sNormalBgColor, -1);
    }

    public static void showToast(int resId) {
        showToast(null, resId);
    }

    public static void showToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT, sNormalTextColor, sNormalBgColor, -1);
    }

    public static void showToast(Context mContext, String text, int duration, int textColor, int bgColor, int icon) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Context ctx = mContext;
        if (ctx == null) {
            ctx = DaysApplication.getMyApplicationContext();
        }

        if (ctx == null) {
            return;
        }

        mHandler.post(new ToastTask(ctx, text, duration));
    }

    public static void showToast(Context mContext, int resId, int duration, int textColor, int bgColor, int icon) {
        if (resId == 0 || resId == -1) {
            return;
        }

        Context ctx = mContext;
        if (ctx == null) {
            ctx = DaysApplication.getMyApplicationContext();
        }

        if (ctx == null) {
            return;
        }
        mHandler.post(new ToastTask(ctx, ctx.getResources().getString(resId), duration));
    }

    private static class ToastTask implements Runnable {
        private Context mCtx;
        private String mContent;
        private int mDuration;

        public ToastTask(Context mContext, String text, int duration) {
            mCtx = mContext;
            mContent = text;
            if (duration == 0) {
                mDuration = Toast.LENGTH_SHORT;
            } else {
                mDuration = Toast.LENGTH_LONG;
            }
        }

        @Override
        public void run() {
            if (mCtx != null && mContent != null)
                Toast.makeText(mCtx, mContent, mDuration).show();
        }

    }
}
