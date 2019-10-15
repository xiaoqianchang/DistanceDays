package com.adups.distancedays.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.adups.distancedays.R;
import com.adups.distancedays.model.EventModel;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/15.
 *
 * @version 1.0
 */
public class FormatHelper {

    public static String getDateCardTitle(String title, boolean isToday, boolean isOutOfDate, boolean hasEnded, Context context) {
        return getDateCardTitle(title, isToday, isOutOfDate, hasEnded, context, PreferenceManager.getDefaultSharedPreferences(context).getBoolean("preference_key_hide_description", false));
    }

    public static String getDateCardTitle(EventModel data, Context context) {
        boolean z = false;
        boolean isHideDescription = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("preference_key_hide_description", false);
        String title = data.getEventTitle();
        if (data.getDays() == 0) {
            z = true;
        }
        return getDateCardTitle(title, z, data.isOutOfTargetDate(), data.isHasEndDate(), context, isHideDescription);
    }

    public static String getDateCardTitle(EventModel data, boolean isHideDescription, Context context) {
        return getDateCardTitle(data.getEventTitle(), data.getDays() == 0, data.isOutOfTargetDate(), data.isHasEndDate(), context, isHideDescription);
    }

    public static String getDateCardTitle(String title, boolean isToday, boolean isOutOfDate, boolean hasEnded, Context context, boolean isHideDescription) {
        return isHideDescription ? title : a(a(context, title), isToday, isOutOfDate, hasEnded, context);
    }

    private static String a(String title, boolean isToday, boolean isOutOfDate, boolean hasEnded, Context context) {
        if (isToday) {
            return MessageFormat.format(context.getResources().getString(R.string.is_today), new Object[]{title}).trim();
        } else if (!isOutOfDate) {
            return MessageFormat.format(context.getResources().getString(R.string.till), new Object[]{title}).trim();
        } else if (hasEnded) {
            return MessageFormat.format(context.getResources().getString(R.string.total), new Object[]{title}).trim();
        } else {
            return MessageFormat.format(context.getResources().getString(R.string.already), new Object[]{title}).trim();
        }
    }

    public static String getDateCardTitlePartBold(EventModel data, Context context) {
        boolean z = false;
        boolean isHideDescription = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("preference_key_hide_description", false);
        String title = data.getEventTitle();
        if (data.getDays() == 0) {
            z = true;
        }
        return getDateCardTitlePartBold(title, z, data.isOutOfTargetDate(), data.isOutOfTargetDate(), context, isHideDescription);
    }

    public static String getDateCardTitlePartBold(String title, boolean isToday, boolean isOutOfDate, boolean hasEnded, Context context, boolean isHideDescription) {
        return isHideDescription ? title : a("<b>" + a(context, title) + "</b>", isToday, isOutOfDate, hasEnded, context);
    }

    public static String getDateCardDueDateText(Calendar dueDate, Calendar endDate, boolean isOutOfDate, boolean hasEnded, boolean isLunarCalendar, Context context) {
        String dueDateString = DateUtils.getFormatedDate(context, dueDate, 2, isLunarCalendar);
        String endDateString = null;
        if (endDate != null) {
            endDateString = DateUtils.getFormatedDate(context, endDate, 2, isLunarCalendar);
        }
        if (!isOutOfDate) {
            return context.getResources().getString(R.string.ends_date) + " " + dueDateString;
        }
        if (!hasEnded || endDateString == null) {
            return context.getResources().getString(R.string.starts_date) + " " + dueDateString;
        }
        return dueDateString + "~" + endDateString;
    }

    public static String getDateCardDueDateText(Date dueDate, Date endDate, boolean isOutOfDate, boolean hasEnded, boolean isLunarCalendar, Context context) {
        Calendar calendarDueDate = Calendar.getInstance();
        Calendar calendarEndDate = null;
        calendarDueDate.setTime(dueDate);
        if (endDate != null) {
            calendarEndDate = Calendar.getInstance();
            calendarEndDate.setTime(endDate);
        }
        return getDateCardDueDateText(calendarDueDate, calendarEndDate, isOutOfDate, hasEnded, isLunarCalendar, context);
    }

    public static String getDateCardShortDueDateText(Date dueDate, Date endDate, boolean isOutOfDate, boolean hasEnded, boolean isLunarCalendar, Context context) {
        if (endDate == null || !hasEnded) {
            return DateUtils.getFormatedDate(context, dueDate, 2, isLunarCalendar);
        }
        return getDateCardDueDateText(dueDate, endDate, isOutOfDate, hasEnded, isLunarCalendar, context);
    }

    private static String a(Context context, String title) {
        if (title == null) {
            return null;
        }
        String locale = context.getResources().getConfiguration().locale.getCountry();
        int i = -1;
        switch (locale.hashCode()) {
            case 2155:
                if (locale.equals("CN")) {
                    i = 0;
                    break;
                }
                break;
            case 2307:
                if (locale.equals("HK")) {
                    i = 2;
                    break;
                }
                break;
            case 2691:
                if (locale.equals("TW")) {
                    i = 1;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
                int length = title.length();
                int c = title.charAt(0);
                if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)) {
                    title = " " + title;
                }
                c = title.charAt(length - 1);
                if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)) {
                    title = title + " ";
                }
                return title;
            default:
                return " " + title.trim() + " ";
        }
    }

    public static boolean isLanguageZh(Context context) {
        String country = context.getResources().getConfiguration().locale.getCountry();
        return country.equals("CN") || country.equals("TW") || country.equals("HK");
    }

    public static String getDaysTextByDayNum(int days) {
        if (days == 1) {
            return "Day";
        }
        return "Days";
    }
}
