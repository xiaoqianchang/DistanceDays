package com.adups.distancedays.activity;

/**
 * 启动页
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.adups.distancedays.MainActivity;
import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 500);
    }
}
