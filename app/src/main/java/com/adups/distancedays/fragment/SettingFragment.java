package com.adups.distancedays.fragment;

import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;

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

}
