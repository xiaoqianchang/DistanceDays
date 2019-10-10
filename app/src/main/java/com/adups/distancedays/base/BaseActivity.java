package com.adups.distancedays.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * base$
 *
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

  protected final String TAG = BaseActivity.class.getSimpleName();

  protected InputMethodManager mManager;
  private Unbinder bind;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.i(TAG, "onCreate");
    setContentView(getContentViewId());
    bind = ButterKnife.bind(this);
    initPresenter();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
    }
    mManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
  }

  protected abstract int getContentViewId();
  protected abstract void initPresenter();

  @TargetApi(19)
  private void setTranslucentStatus(boolean on) {
    Window win = getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
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
    super.onDestroy();
    if (null != bind) {
      bind.unbind();
    }
    // 取消网络请求
    Log.i(TAG, "onDestroy");
  }
}
