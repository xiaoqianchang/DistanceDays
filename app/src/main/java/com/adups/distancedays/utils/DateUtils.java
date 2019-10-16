package com.adups.distancedays.utils;

import android.content.Context;
import android.text.format.DateFormat;

import com.adups.distancedays.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Date utils used for application.
 * <p>
 * Created by Chang.Xiao on 2019/10/12.
 *
 * @version 1.0
 */
public class DateUtils {

    /**
     * yyyy-MM-dd
     */
    public static final String TIME_FORMAT = "yyyy-MM-dd";

    /**
     * yyyyMMdd
     */
    public static final String TIME_FORMAT1 = "yyyyMMdd";

    /**
     * Returns the current time in milliseconds since January 1, 1970 00:00:00.0 UTC.
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Returns the current time in milliseconds since January 1, 1970 00:00:00.0 UTC by DateFormat.
     *
     * @param format
     * @return
     */
    public static String getCurrentTimeStr(String format) {
        return formatTime(System.currentTimeMillis(), format);
    }

    /**
     * 格式化时间
     *
     * @param time   毫秒
     * @param format format
     * @return
     */
    public static String formatTime(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取日期间的日期
     *
     * @param start
     * @param end
     * @return
     */
    private static List<Date> getDaysBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(Calendar.DAY_OF_YEAR, 1);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 根据日期获取 星期 （2019-05-06 ——> 星期一）
     * https://www.cnblogs.com/BillyYoung/p/10833471.html
     *
     * @param datetime
     * @return
     */
    public static String getWeekByDate(String datetime, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = dateFormat.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 一周的第几天
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getWeekByDate(Date date) {
        return new SimpleDateFormat("EEEE").format(date);
    }

    /**
     * Formatting date object to string of the form 'yyyy-MM-dd'
     *
     * @param date the date object to format
     * @return a string represent date
     */
    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyy-MM-dd").format(date);
    }

    /**
     * Calculating the date interval between current date and target date
     *
     * @param date target date
     * @return the interval between current date and target date
     */
    public static long getDateInterval(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        long duration = date.getTime() - currentDate.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        return days;
    }


    /*导入*/
    public static String getFormatedDate(Context context, Calendar calendar, boolean isLunarCalendar) {
        return getFormatedDate(context, calendar, 0, isLunarCalendar);
    }

    public static String getFormatedDate(Context context, Date date, boolean isLunarCalendar) {
        return getFormatedDate(context, date, 0, isLunarCalendar);
    }

    public static String getFormatedDate(Context context, Date date, int formatType, boolean isLunarCalendar) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getFormatedDate(context, calendar, formatType, isLunarCalendar);
    }

    public static String getFormatedDate(Context context, Calendar calendar, int formatType, boolean isLunarCalendar) {
        if (calendar == null) {
            return null;
        }
        switch (formatType) {
            case 0:
                return DateFormat.format("yyyy-MM-dd", calendar.getTime()).toString();
            case 1:
                String AD = context.getString(R.string.year_ad);
                if (calendar.get(0) == 0) {
                    AD = context.getString(R.string.year_bc);
                }
                return AD + DateFormat.format("yyyy-MM-dd ", calendar.getTime()).toString() + getWeekString(context, calendar.get(7));
            case 2:
                if (isLunarCalendar) {
                    return new LunarCalendar(calendar).toString();
                }
                return DateFormat.format("yyyy-MM-dd ", calendar.getTime()).toString() + getWeekString(context, calendar.get(7));
            case 3:
                return DateFormat.format("yyyy", calendar.getTime()).toString();
            case 4:
                return DateFormat.format("MM", calendar.getTime()).toString();
            case 5:
                return DateFormat.format("dd", calendar.getTime()).toString();
            case 6:
                return DateFormat.format("yyyy.MM.dd HH:mm", calendar.getTime()).toString();
            case 7:
                return DateFormat.format("yyyy_MM_dd_HH:mm", calendar.getTime()).toString();
            case 8:
                String result = DateFormat.format("yyyy.MM.dd ", calendar.getTime()).toString() + getShortWeekString(context, calendar.get(7));
                if (!isLunarCalendar) {
                    return result;
                }
                return new LunarCalendar(calendar).toString() + " / " + result;
            default:
                return null;
        }
    }

    public static String getWeekString(Context context, int dayOfWeek) {
        String[] days = context.getResources().getStringArray(R.array.day_in_week);
        switch (dayOfWeek) {
            case 1:
                return days[6];
            case 2:
                return days[0];
            case 3:
                return days[1];
            case 4:
                return days[2];
            case 5:
                return days[3];
            case 6:
                return days[4];
            case 7:
                return days[5];
            default:
                return null;
        }
    }

    public static String getShortWeekString(Context context, int dayOfWeek) {
        String[] days = context.getResources().getStringArray(R.array.day_in_week_short);
        switch (dayOfWeek) {
            case 1:
                return days[6];
            case 2:
                return days[0];
            case 3:
                return days[1];
            case 4:
                return days[2];
            case 5:
                return days[3];
            case 6:
                return days[4];
            case 7:
                return days[5];
            default:
                return null;
        }
    }

    public static Calendar getTomorrowCalendar() {
        Calendar today = Calendar.getInstance();
        today.set(5, today.get(5) + 1);
        return today;
    }

    public static long getDateOffset(Calendar cal1, Calendar cal2) {
        cal1.set(11, 0);
        cal1.set(12, 0);
        cal1.set(13, 0);
        cal1.set(14, 0);
        cal2.set(11, 0);
        cal2.set(12, 0);
        cal2.set(13, 0);
        cal2.set(14, 0);
        return (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / 86400000;
    }

    public static long getDateOffset(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return getDateOffset(calendar1, calendar2);
    }

    public static void resetClaendar(Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
    }

}
