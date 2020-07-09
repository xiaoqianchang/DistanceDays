package com.adups.distancedays.ad;

import com.adups.distancedays.R;
import com.adups.distancedays.statistics.StatisticsUtil;
import com.adups.distancedays.utils.CommonUtil;
import com.adups.distancedays.utils.PackageUtil;
import com.litre.openad.LitreAdConfig;
import com.litre.openad.LitreAdSdk;
import com.litre.openad.data.IHttpConnector;
import com.litre.openad.data.IReporter;
import com.orhanobut.logger.Logger;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Map;

/**
 * 说明：移动广告工具类
 * <p>
 * Created by Chang.Xiao on 2020-01-16.
 *
 * @version 1.0
 */
public class MobileAdsUtil {

    private static final String TAG = "MobileAdsUtil";

    public static void init() {
        LitreAdSdk.init(CommonUtil.getApplication(), new LitreAdConfig.Builder()
                //穿山甲的appId,使用穿山甲sdk必填
                .ttAppId(AdConfig.APPID)
                //广点通的aapId，使用广点通sdk必填
//                .gdtAppId(AdConfig.GDT_APPID)
                //百青藤的appid
//                .bqtAppId("bqt1234")
                //渠道号,如果广告需要分渠道配置必须填写
                .channelId(PackageUtil.getChannel(CommonUtil.getApplication()))
                //应用名称，必填
                .appName(CommonUtil.getApplication().getString(R.string.app_name))
                //选填，是否广告请求打印日志
                .showLog(true)
                //选填，本地默认广告配置（服务器接口获取失败或者未配置的广告位，会取本地默认配置）,具体配置见2.2
//                .configRes(R.xml.default_ad)
                .httpConnector(new IHttpConnector() {
                    @Override
                    public String remoteConfig() {
                        try {
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("AdConfig");
                            ParseObject parseObject = query.get("MaBnqGJ1bw");
                            if (parseObject != null) {
                                String adConfig = parseObject.getString("adConfig");
                                if (CommonUtil.isDebug()) {
                                    Logger.t("LitreAD").e(adConfig);
                                }
                                return adConfig;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                        }
                        return null;
                    }
                })
                //选填，广告事件流程回调
                .reporter(new IReporter() {
                    @Override
                    public void report(String placement, Map<String, String> map) {
                        StatisticsUtil.onEvent(CommonUtil.getApplication(), placement, map);
                    }
                })
                .build());

    }
}
