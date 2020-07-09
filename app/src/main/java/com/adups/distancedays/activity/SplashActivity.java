package com.adups.distancedays.activity;

/**
 * 启动页
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.adups.distancedays.MainActivity;
import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseActivity;
import com.adups.distancedays.manager.GlobalConfigManager;
import com.adups.distancedays.model.AppConfigModel;
import com.litre.openad.ad.LitreSplashAd;
import com.litre.openad.para.LitreError;
import com.litre.openad.para.LitreRequest;
import com.litre.openad.stamp.splash.LitreSplashListener;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_container)
    FrameLayout splashContainer;
    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;
    private static final int AD_TIME_OUT = 5000;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        GlobalConfigManager.getInstance().fetchAppConfig(new GlobalConfigManager.FetchAppConfigCallback() {
            @Override
            public void onFetchSuccess(AppConfigModel model) {
                if (model == null || model.isShowSplashAd()) {
                    loadAd();
                } else {
                    jumpToMain();
                }
            }

            @Override
            public void onFetchFail() {
                loadAd();
            }
        });
    }

    private void loadAd() {
        LitreRequest adRequest = new LitreRequest.Builder()
                //必须项，必须是activity对象
                .Contenxt(this)
                //必须项,加载广告的父布局
                .adRoot(splashContainer)
                //必须项,广告宽高尺寸，单位px
                .size(new int[]{1080, 1920})
                //必须项,配置的广告位名称
                .position("peak_splash")
                .build();
        LitreSplashAd litreSplashAd = new LitreSplashAd(adRequest);
        litreSplashAd.setListener(new LitreSplashListener() {
            @Override
            public void onLoaded(View view) {
                //广告加载成功回调,返回的view不可靠，可能为空
            }

            @Override
            public void onError(LitreError error) {
                // 报错、无网络
                jumpToMain();
            }

            @Override
            public void onTimeOut() {
                jumpToMain();
                //广告加载超时
            }

            @Override
            public void onAdTimeOver() {
                jumpToMain();
                //开屏广告倒计时时间结束
            }

            @Override
            public void onAdSkip() {
                super.onAdSkip();
                jumpToMain();
                //用户点击了跳过按钮
            }
        });
        //请求广告
        litreSplashAd.load();
    }

    private void jumpToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, SplashActivity.class);
        activity.startActivity(intent);
    }
}
