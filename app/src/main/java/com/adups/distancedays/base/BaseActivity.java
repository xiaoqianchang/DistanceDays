package com.adups.distancedays.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.adups.distancedays.utils.StatusBarUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity 顶层基类
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = BaseActivity.class.getSimpleName();

    protected Context mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        StatusBarUtils.initStatusBar(this, isLightStatusBar());
        setContentView(getContentViewId());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        init(savedInstanceState);
    }

    protected abstract int getContentViewId();
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 透明状态栏 默认使用深色状态栏字体、反之为浅色状态栏字体
     */
    protected boolean isLightStatusBar() {
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
            // 最好上报
        }
        Log.i(TAG, "onDestroy");
        StatusBarUtils.destroy(this);
        if (null != mUnBinder) {
            mUnBinder.unbind();
        }
        // 取消网络请求
    }
}
