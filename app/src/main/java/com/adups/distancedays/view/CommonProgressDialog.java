package com.adups.distancedays.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adups.distancedays.R;

import androidx.annotation.NonNull;


/**
 * 适用于异步过程中需要展示loading弹窗，延迟显示
 * 这个类不要检查isShowing()再dismiss
 */
public class CommonProgressDialog extends ProgressDialog {
    private boolean indeterminate = false;
    private CharSequence message;
    private View layout;
    private boolean isDismiss = false;
    private Handler mHandler;
    private Runnable mRunnable;
    private Context mContext;
    private OnCancelListener onCancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            isDismiss = true;
            onDestroy();
        }
    };

    public CommonProgressDialog(Context activity) {
        super(activity);
        this.mContext = activity;
    }


    public CommonProgressDialog(@NonNull Context activity, int resId) {
        super(activity, resId);
        this.mContext = activity;
    }


    @Override
    public void setMessage(CharSequence message) {
        this.message = message;
    }

    @SuppressLint("NewApi")
    @Override
    public void show() {
        try {
            setOnCancelListener(onCancelListener);
            super.show();
            layout = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
            getWindow().setContentView(layout);
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//            layoutParams.width = BaseUtil.dp2px(mContext, 105);
//            layoutParams.height = BaseUtil.dp2px(mContext, 105);
            getWindow().setAttributes(layoutParams);
            getWindow().setBackgroundDrawableResource(R.drawable.bg_progress_dialog);
            getWindow().setGravity(Gravity.CENTER);
            if (!TextUtils.isEmpty(message)) {
                ((TextView) layout.findViewById(R.id.msg_tv)).setText(message);
                ((TextView) layout.findViewById(R.id.msg_tv)).setVisibility(View.VISIBLE);
            }
            ((ProgressBar) layout.findViewById(R.id.progress_bar)).setIndeterminate(indeterminate);
            if (Build.VERSION.SDK_INT >= 21) {
                ((ProgressBar) layout.findViewById(R.id.progress_bar)).setIndeterminateTintList(ColorStateList.valueOf(0xFFFC5832));
            }
            if (Build.VERSION.SDK_INT >= 14) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setDimAmount(0.1f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delayShow() {
        synchronized (CommonProgressDialog.class) {
            removeDelay();

            isDismiss = false;

            mRunnable = new Runnable() {
                @Override
                public void run() {
                    if (!isDismiss) {
                        show();
                    }
                }
            };

            if (Looper.myLooper() != Looper.getMainLooper()) {
                mHandler = new Handler(Looper.getMainLooper());
            } else {
                mHandler = new Handler();
            }
            mHandler.postDelayed(mRunnable, 500);
        }
    }

    private void removeDelay() {
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
            mHandler = null;
            mRunnable = null;
        }
    }

    @Override
    public void dismiss() {
        synchronized (CommonProgressDialog.class) {
            isDismiss = true;
            try {
                super.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onDestroy() {
        setOnCancelListener(null);
        removeDelay();
//        ViewUtil.flushStackLocalLeaks(Looper.getMainLooper());
        if (layout != null && layout.getParent() != null) {
            ((ViewGroup) layout.getParent()).removeAllViews();
        }
        onCancelListener = null;

    }

    public void dismissNoCheckIsShow() {
        dismiss();
    }
}
