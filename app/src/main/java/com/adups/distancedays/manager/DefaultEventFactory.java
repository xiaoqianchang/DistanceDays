package com.adups.distancedays.manager;

import android.content.Context;

import com.adups.distancedays.db.DBHelper;
import com.adups.distancedays.db.dao.EventDao;
import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.utils.AppConstants;
import com.adups.distancedays.utils.ContextUtils;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.LunarCalendar;
import com.adups.distancedays.utils.SPUtil;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 默认事件工厂
 * <p>
 * Created by Chang.Xiao on 2019/10/25.
 *
 * @version 1.0
 */
public class DefaultEventFactory {

    private DefaultEventFactory() {
    }

    public static DefaultEventFactory getInstance() {
        return DefaultEventFactoryHolder.INSTANCE;
    }

    private static final class DefaultEventFactoryHolder {
        private static final DefaultEventFactory INSTANCE = new DefaultEventFactory();
    }

    /**
     * 首次使用设置默认数据
     */
    public void initDefaultData(Context context) {
        if (!ContextUtils.checkContext(context)) {
            return;
        }
        boolean isFirstLaunchApp = SPUtil.getValue(AppConstants.SP_KEY.FIRST_LAUNCH_APP, true);
        if (isFirstLaunchApp) {
            // 初始化数据
            ArrayList<EventEntity> eventEntities = new ArrayList<>();
            // 周末
            eventEntities.add(DefaultEventFactory.getInstance().createWeekend());

            // 春节
            eventEntities.add(DefaultEventFactory.getInstance().createNewYear());

            // 今年已过
            eventEntities.add(DefaultEventFactory.getInstance().createThisYearAlready());

            // 情人节 每年2月14日
            eventEntities.add(DefaultEventFactory.getInstance().createLoverDay());

            // 高考 每年6月7日
            eventEntities.add(DefaultEventFactory.getInstance().createCollege());

            EventDao eventDao = DBHelper.getInstance(context).getDaoSession().getEventDao();
            eventDao.insertInTx(eventEntities);

            SPUtil.setValue(AppConstants.SP_KEY.FIRST_LAUNCH_APP, false); // 检查首次登录标志位，并置为false
        }
    }

    /**
     * 创建周末事件
     *
     * @return
     */
    public EventEntity createWeekend() {
        EventEntity entity = new EventEntity();
        entity.setEventTitle("周末");
        entity.setCreateDate(DateUtils.getCurrentTimeMillis());
        entity.setTargetDate(DateUtils.getRecentlyWeekend().getTimeInMillis());
        entity.setIsLunarCalendar(false);
        entity.setIsTop(true);
        entity.setRepeatType(1);

        return entity;
    }

    /**
     * 创建春节事件
     *
     * @return
     */
    public EventEntity createNewYear() {
        LunarCalendar lunarCalendar = new LunarCalendar(Calendar.getInstance());
        Calendar calendar = LunarCalendar.lunarToSolarCalendar(lunarCalendar.getYear() + 1, 1, 1, lunarCalendar.isLeapMonth());

        EventEntity entity = new EventEntity();
        entity.setEventTitle("春节");
        entity.setCreateDate(DateUtils.getCurrentTimeMillis());
        entity.setTargetDate(calendar.getTimeInMillis());
        entity.setIsLunarCalendar(false);
        entity.setIsTop(false);
        entity.setRepeatType(3);

        return entity;
    }

    /**
     * 创建今年已过事件
     *
     * @return
     */
    public EventEntity createThisYearAlready() {
        LunarCalendar lunarCalendar = new LunarCalendar(Calendar.getInstance());
//        Calendar calendarAlready = LunarCalendar.lunarToSolarCalendar(lunarCalendar.getYear(), 1, 1, lunarCalendar.isLeapMonth()); // 以阴历正月初一算已过时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, lunarCalendar.getYear());
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);

        EventEntity entity = new EventEntity();
        entity.setEventTitle(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        entity.setCreateDate(DateUtils.getCurrentTimeMillis());
        entity.setTargetDate(calendar.getTimeInMillis());
        entity.setIsLunarCalendar(false);
        entity.setIsTop(false);
        entity.setRepeatType(3);

        return entity;
    }

    /**
     * 创建情人节事件 每年2月14日
     *
     * @return
     */
    public EventEntity createLoverDay() {
        Calendar calendar = Calendar.getInstance();
        int targetYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDate = calendar.get(Calendar.DATE);
        if (currentMonth > 2) {
            targetYear = targetYear + 1;
        } else if (currentMonth == 2 && currentDate > 14) {
            targetYear = targetYear + 1;
        }
        calendar.set(Calendar.YEAR, targetYear);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 14);

        EventEntity entity = new EventEntity();
        entity.setEventTitle("情人节");
        entity.setCreateDate(DateUtils.getCurrentTimeMillis());
        entity.setTargetDate(calendar.getTimeInMillis());
        entity.setIsLunarCalendar(false);
        entity.setIsTop(false);
        entity.setRepeatType(3);

        return entity;
    }

    /**
     * 创建高考事件 每年6月7日
     *
     * @return
     */
    public EventEntity createCollege() {
        Calendar calendar = Calendar.getInstance();
        int targetYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDate = calendar.get(Calendar.DATE);
        if (currentMonth > 6) {
            targetYear = targetYear + 1;
        } else if (currentMonth == 2 && currentDate > 7) {
            targetYear = targetYear + 1;
        }
        calendar.set(Calendar.YEAR, targetYear);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DATE, 7);

        EventEntity entity = new EventEntity();
        entity.setEventTitle("情人节");
        entity.setCreateDate(DateUtils.getCurrentTimeMillis());
        entity.setTargetDate(calendar.getTimeInMillis());
        entity.setIsLunarCalendar(false);
        entity.setIsTop(false);
        entity.setRepeatType(3);

        return entity;
    }
}
