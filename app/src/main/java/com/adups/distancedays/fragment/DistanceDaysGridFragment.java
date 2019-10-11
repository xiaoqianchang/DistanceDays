package com.adups.distancedays.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;

/**
 * 网格状态的倒数日
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class DistanceDaysGridFragment extends BaseFragment {

    public static DistanceDaysGridFragment newInstance() {
        Bundle bundle = new Bundle();
        DistanceDaysGridFragment fragment = new DistanceDaysGridFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_distance_days_grid;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

}
