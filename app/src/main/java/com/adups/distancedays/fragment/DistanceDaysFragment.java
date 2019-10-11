package com.adups.distancedays.fragment;

import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;

/**
 * 距离日
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class DistanceDaysFragment extends BaseFragment {

    public static DistanceDaysFragment newInstance() {
        Bundle bundle = new Bundle();
        DistanceDaysFragment fragment = new DistanceDaysFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_distance_days;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
