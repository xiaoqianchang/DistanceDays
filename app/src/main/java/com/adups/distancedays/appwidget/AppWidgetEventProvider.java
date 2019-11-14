package com.adups.distancedays.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.adups.distancedays.MainActivity;
import com.adups.distancedays.R;
import com.adups.distancedays.db.DBHelper;
import com.adups.distancedays.db.EntityConverter;
import com.adups.distancedays.db.dao.EventDao;
import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.utils.CommonUtil;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.FormatHelper;
import com.adups.distancedays.utils.ToolUtil;

import java.util.Calendar;
import java.util.List;

/**
 * 单个事件 widget
 *
 * 最重要的方法有两种onReceive, onUpdate.
 * onReceive，自定义的点击事件（即发送自定义的广播，在这里接收并处理）。
 * onUpdate, 小部件被加载到桌面
 *
 * 流程:
 * 添加小部件->onEnabled->onReceive(android.appwidget.action.APPWIDGET_ENABLED)->onUpdate->onReceive(android.appwidget.action.APPWIDGET_UPDATE)
 * 删除小部件->onDeleted->onReceive(android.appwidget.action.APPWIDGET_DELETED)->onDisabled->onReceive(android.appwidget.action.APPWIDGET_DISABLED)
 * <p>
 * Created by Chang.Xiao on 2019-11-12.
 *
 * @version 1.0
 */
public class AppWidgetEventProvider extends BaseAppWidgetProvider {

    private static final String TAG = "AppWidgetEventProvider";

    private EventDao mEventDao;

    /**
     * 接收窗口小部件点击时发送的广播
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive : action = " + intent.getAction());
    }

    @Override
    protected void onInitUI(Context context) {
        updateWidgetUI(context);
    }

    private void updateWidgetUI(Context context) {
        try {
            RemoteViews views = getRemoteViews(context);
            if (views != null) {
                setDataToView(context, views);
                commitUpdate(context, views);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDataToView(Context context, RemoteViews views) {
        if (context == null || views == null) {
            return;
        }
        try {
            EventEntity eventEntity = null;
            List<EventEntity> list = getEventDao().queryBuilder().where(EventDao.Properties.IsTop.eq(true)).list();
            if (!ToolUtil.isEmptyCollects(list)) {
                eventEntity = list.get(0);
            } else {
                List<EventEntity> entities = getEventDao().loadAll();
                if (!ToolUtil.isEmptyCollects(entities)) {
                    eventEntity = entities.get(0);
                }
            }

            EventModel eventModel = EntityConverter.convertToEventModel(eventEntity);
            if (eventModel == null) {
                return;
            }

            String title = FormatHelper.getDateCardTitle(eventModel, context);
            views.setTextViewText(R.id.title, title);
            views.setTextViewText(R.id.date, String.valueOf(eventModel.getDays()));
            Calendar instance = Calendar.getInstance();
            instance.setTime(eventModel.getTargetDate());
            views.setTextViewText(R.id.due_date, context.getString(R.string.string_target_date, DateUtils.getFormatedDate(context, instance, 2, eventModel.isLunarCalendar())));
            if (eventModel.isOutOfTargetDate()) {
                views.setInt(R.id.title, "setBackgroundResource", R.drawable.bg_widget_small_title_passed);
            } else {
                views.setInt(R.id.title, "setBackgroundResource", R.drawable.bg_widget_small_title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 EventDao
     *
     * @return
     */
    private EventDao getEventDao() {
        if (mEventDao == null) {
            mEventDao = DBHelper.getInstance(CommonUtil.getApplication()).getDaoSession().getEventDao();
        }
        return mEventDao;
    }

    private void commitUpdate(Context context, RemoteViews views) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        ComponentName component = new ComponentName(context, getClass());
        if (manager != null && views != null) {
            manager.updateAppWidget(component, views);
        }
    }

    /**
     * 每次窗口小部件被点击更新都调用一次该方法
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate");
        try {
            for (int appWidgetId : appWidgetIds) {
                onWidgetUpdate(context, appWidgetManager, appWidgetId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 窗口小部件更新
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    public void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "onWidgetUpdate : appWidgetId = " + appWidgetId);
        RemoteViews views = getRemoteViews(context);
        if (views != null) {
            Intent openIntent = new Intent(context, MainActivity.class);
            openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent openPendingIntent = PendingIntent.getActivity(context, appWidgetId, openIntent, 0);
            views.setOnClickPendingIntent(R.id.widget_content, openPendingIntent);

            setDataToView(context, views);
            // 更新部件
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private RemoteViews getRemoteViews(Context context) {
        if (context == null) {
            return null;
        }
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_event_layout);
        return views;
    }

    /**
     * 每删除一次窗口小部件就调用一次
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "onDeleted");
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法，注意是最后一个
     *
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "onDisabled");
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法，可添加多次但只第一次调用
     *
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.d(TAG, "onRestored");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d(TAG, "onAppWidgetOptionsChanged");
        appWidgetManager.updateAppWidget(appWidgetId, getRemoteViews(context));
    }

    public int getResourceId(Context context, String name, String className) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier(name, className,
                context.getPackageName());
    }
}
