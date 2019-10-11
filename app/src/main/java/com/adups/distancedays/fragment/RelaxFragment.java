package com.adups.distancedays.fragment;

import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;

/**
 * 轻松一刻
 * 
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class RelaxFragment extends BaseFragment {

    public static RelaxFragment newInstance() {
        Bundle bundle = new Bundle();
        RelaxFragment fragment = new RelaxFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_relax;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

}
