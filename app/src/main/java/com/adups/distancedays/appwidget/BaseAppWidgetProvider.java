package com.adups.distancedays.appwidget;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.adups.distancedays.utils.AppConstants;

/**
 * Base AppWidgetProvider
 * <p>
 * Created by Chang.Xiao on 2019-11-13.
 *
 * @version 1.0
 */
public abstract class BaseAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (context == null || intent == null) {
            return;
        }

        String action = intent.getAction();
        if (TextUtils.equals(action, Intent.ACTION_BOOT_COMPLETED)
                || TextUtils.equals(action, AppConstants.Action.ACTION_INIT_UI)
                || TextUtils.equals(action, AppConstants.Action.ACTION_DATE_CHANGED)) {
            onInitUI(context);
        }
    }

    protected abstract void onInitUI(Context context);
}
