package com.adups.distancedays.manager;

import com.adups.distancedays.model.AppConfigModel;
import com.adups.distancedays.utils.AppConstants;
import com.adups.distancedays.utils.CommonUtil;
import com.adups.distancedays.utils.PackageUtil;
import com.adups.distancedays.utils.SPUtil;
import com.adups.distancedays.utils.ToolUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Description: 全局配置管理
 * <p>
 * Created by Chang.Xiao on 2020-07-09.
 *
 * @version 1.0
 */
public class GlobalConfigManager {

    private static final String TAG = "GlobalConfigManager";

    private static GlobalConfigManager instance;

    private AppConfigModel mAppConfigModel;

    private GlobalConfigManager() {
    }

    public static GlobalConfigManager getInstance() {
        if (instance == null) {
            synchronized (GlobalConfigManager.class) {
                if (instance == null) {
                    instance = new GlobalConfigManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取app配置
     */
    public void fetchAppConfig(FetchAppConfigCallback callback) {
        try {
            String channel = PackageUtil.getChannel(CommonUtil.getApplication());
            if (channel == null) {
                channel = "";
            } else {
                channel = channel.toLowerCase();
            }
            ParseQuery<ParseObject> query = ParseQuery.getQuery("AppConfig");
            query.whereEqualTo("enable", true); // 已经开启
            query.whereEqualTo("appVersion", PackageUtil.getVersionName());
            query.whereEqualTo("channel", channel);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        // object will be your game score
                        if (ToolUtil.isEmptyCollects(objects)) {
                            Logger.t(TAG).d("Retrieved " + 0 + "条数据");
                            clearAppConfig();
                            if (callback != null) {
                                callback.onFetchFail();
                            }
                            return;
                        }
                        Logger.t(TAG).d("Retrieved " + objects.size() + "条数据");
                        ParseObject parseObject = objects.get(objects.size() - 1);// 取最后一条
                        if (parseObject != null) {
                            mAppConfigModel = new AppConfigModel();
                            mAppConfigModel.setAppVersion(parseObject.getString("appVersion"));
                            mAppConfigModel.setChannel(parseObject.getString("channel"));
                            mAppConfigModel.setEnable(parseObject.getBoolean("enable"));
                            mAppConfigModel.setShowSplashAd(parseObject.getBoolean("showSplashAd"));

                            SPUtil.setValue(AppConstants.SP_KEY.APP_CONFIG2, new Gson().toJson(mAppConfigModel));
                            if (callback != null) {
                                callback.onFetchSuccess(mAppConfigModel);
                            }
                        } else {
                            if (callback != null) {
                                callback.onFetchFail();
                            }
                        }
                    } else {
                        // something went wrong (包括I/O 无网络)
                        Logger.t(TAG).d("Error: " + e.getMessage());
                        if (callback != null) {
                            callback.onFetchFail();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onFetchFail();
            }
        }
    }

    /**
     * 清除配置缓存
     */
    public void clearAppConfig() {
        SPUtil.remove(AppConstants.SP_KEY.APP_CONFIG2);
        mAppConfigModel = null;
    }

    public interface FetchAppConfigCallback {

        void onFetchSuccess(AppConfigModel model);
        void onFetchFail();
    }
}
