package com.adups.distancedays.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adups.distancedays.R;
import com.adups.distancedays.activity.AboutUsActivity;
import com.adups.distancedays.activity.FeedbackActivity;
import com.adups.distancedays.base.BaseFragment;

import butterknife.OnClick;

/**
 * 设置
 *
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class SettingFragment extends BaseFragment {

    public static SettingFragment newInstance() {
        Bundle bundle = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.rl_theme, R.id.rl_feedback, R.id.rl_about_us})
    public void onClick(View view) {
        int vId = view.getId();
        Intent intent;
        switch (vId) {
            case R.id.rl_theme:
                break;
            case R.id.rl_feedback:
                intent = new Intent(getContext(), FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about_us:
                intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
                break;
        }
    }

}
