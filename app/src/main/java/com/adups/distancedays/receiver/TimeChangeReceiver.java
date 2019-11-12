package com.adups.distancedays.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.adups.distancedays.event.TimeChangeEvent;
import com.adups.distancedays.utils.EventUtil;

/**
 * 监听系统时间改变
 * <p>
 * Created by Chang.Xiao on 2019-11-12.
 *
 * @version 1.0
 */
public class TimeChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "TimeChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        String action = intent.getAction();

        if (TextUtils.equals(action, Intent.ACTION_TIME_CHANGED)
                || TextUtils.equals(action, Intent.ACTION_TIMEZONE_CHANGED)) {
            // 系统时间被改变
            EventUtil.post(new TimeChangeEvent());
        }
    }
}
