package com.adups.distancedays.activity;

import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adups.distancedays.R;
import com.adups.distancedays.base.ToolBarActivity;
import com.adups.distancedays.utils.ToastUtil;

/**
 * 关于我们
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class AboutUsActivity extends ToolBarActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_privacy_policy, R.id.tv_agreement})
    public void onClick(View view) {
        int vId = view.getId();
        Intent intent;
        switch (vId) {
            case R.id.tv_privacy_policy:
                ToastUtil.showToast(mContext, getString(R.string.toast_function_development));
                break;
            case R.id.tv_agreement:
                ToastUtil.showToast(mContext, getString(R.string.toast_function_development));
                break;
        }
    }
}
