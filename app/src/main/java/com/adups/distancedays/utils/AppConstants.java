package com.adups.distancedays.utils;

/**
 * Global variable for application.
 * <p>
 * Created by Chang.Xiao on 2019/10/21.
 *
 * @version 1.0
 */
public class AppConstants {

    public static class Action {
        public static final String ACTION_INIT_UI = "com.color.distancedays.action.INIT_UI";
        public static final String ACTION_DATE_CHANGED = "com.color.distancedays.action.DATE_CHANGED";
    }

    public static class SP_KEY {

        /**
         * 首次启用app
         */
        public static final String FIRST_LAUNCH_APP = "first_launch_app";
    }

    public static class RequestCode {
        public static final int CODE_EVENT_DETAIL = 0x100; // 事件详情
        public static final int CODE_EVENT_EDIT = 0x101; // 编辑事件
        public static final int CODE_EVENT_ADD = 0x102; // 新增事件
    }
}
