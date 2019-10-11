package com.adups.distancedays.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;

/**
 * 历史上今天
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class HistoryFragment extends BaseFragment {

    public static HistoryFragment newInstance() {
        Bundle bundle = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

}
