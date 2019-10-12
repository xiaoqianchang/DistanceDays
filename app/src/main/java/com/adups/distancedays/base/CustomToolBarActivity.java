package com.adups.distancedays.base;

import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import butterknife.BindView;

import com.adups.distancedays.R;

/**
 * 带Toolbar自定义Title的基类Activity
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public abstract class CustomToolBarActivity extends ToolBarActivity {

    @BindView(R.id.nav_left_text)
    protected TextView mNavLeftText;
    @BindView(R.id.center_title)
    protected TextView mCenterTitle;
    @BindView(R.id.nav_right_text)
    protected TextView mNavRightText;

    protected void initToolBar() {
        super.initToolBar();
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
    }
}
