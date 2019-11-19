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
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.adups.distancedays.MainActivity;
import com.adups.distancedays.R;
import com.adups.distancedays.advert.AdFactory;
import com.adups.distancedays.base.BaseActivity;
import com.common.adlib.base.BaseAd;
import com.common.adlib.bean.RequestBean;

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
        RequestBean requestBean = new RequestBean();
        requestBean.setCount(1);
        requestBean.setSdkType("toutiao");
        requestBean.setAppid("5035088");
        requestBean.setZoneid("835088081");

        BaseAd baseAd = AdFactory.newAd(SplashActivity.this,requestBean,splashContainer);
        if(baseAd == null){
            autoJump();
            //Toast.makeText(this,"请求广告类型不对， 无" + requestBean.getSdkType(),Toast.LENGTH_SHORT).show();
            return;
        }
        baseAd.requestSplashAd(new BaseAd.SplashAdListener() {
            @Override
            public void onLoaded() {
                //Toast.makeText(SplashActivity.this,"onLoaded",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdShow() {
                //Toast.makeText(SplashActivity.this,"onAdShow",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick() {
                //Toast.makeText(SplashActivity.this,"onAdClick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClose() {
                //Toast.makeText(SplashActivity.this,"onAdClose",Toast.LENGTH_SHORT).show();
                autoJump();
            }

            @Override
            public void onAdError(int code, String msg) {
                //Toast.makeText(SplashActivity.this,"onAdError",Toast.LENGTH_SHORT).show();
                autoJump();
            }

            @Override
            public void onAdSkip() {
                //Toast.makeText(SplashActivity.this,"onAdSkip",Toast.LENGTH_SHORT).show();
                autoJump();
            }

            @Override
            public void onAdTimeOver() {
                autoJump();
            }
        },AD_TIME_OUT);
    }

    private void autoJump() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, SplashActivity.class);
        activity.startActivity(intent);
    }
}
