package com.adups.distancedays.base;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import com.adups.distancedays.R;
import com.adups.distancedays.utils.SystemUtils;
import com.google.android.material.appbar.AppBarLayout;

/**
 * 带Toolbar自定义Title的基类Activity
 *
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public abstract class ToolBarActivity extends BaseActivity {

  @BindView(R.id.app_bar_layout)
  protected AppBarLayout mAppBarLayout;
  @BindView(R.id.status_bar)
  protected View mStatusBar;
  @BindView(R.id.toolBar)
  protected Toolbar mToolbar;

  @BindView(R.id.nav_left_text)
  protected TextView mNavLeftText;
  @BindView(R.id.center_title)
  protected TextView mCenterTitle;
  @BindView(R.id.nav_right_text)
  protected TextView mNavRightText;

  protected boolean isToolBarHiding = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initStatusBar();
    initToolBar();
  }

  protected void initStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      mStatusBar.setVisibility(View.VISIBLE);
      mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this);
      mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
    } else {
      mStatusBar.setVisibility(View.GONE);
    }
  }

  protected void initToolBar() {
    if (null == mAppBarLayout || null == mToolbar) {
      throw new IllegalStateException("The subclass of ToolbarActivity must contain a toolbar.");
    }
    mToolbar.setOnClickListener(v -> onToolbarClick());
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (canBack()) {
      if (null != actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(canBack()); // 设置返回箭头
      }
    } else {
      if (null != actionBar) {
        actionBar.setDisplayShowTitleEnabled(false);
      }
    }
    if (Build.VERSION.SDK_INT >= 21) {
      mAppBarLayout.setElevation(10.6f);
    }
  }

  /**
   * onToolbarClick
   */
  protected void onToolbarClick() {
    // empty
  }

  /**
   * 设置 NavigationButton 是否可见
   *
   * @return
   */
  protected boolean canBack(){
    return false;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        //在Action Bar的最左边，就是Home icon和标题的区域
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  protected void setAppBarAlpha(float alpha) {
    mAppBarLayout.setAlpha(alpha);
  }

  /**
   * Control ToolBar show or hiden
   */
  protected void hideOrShowToolBar() {
    mAppBarLayout.animate()
        .translationY(isToolBarHiding ? 0 : -mAppBarLayout.getHeight())
        .setInterpolator(new DecelerateInterpolator(2))
        .start();
    isToolBarHiding = !isToolBarHiding;
  }
}
