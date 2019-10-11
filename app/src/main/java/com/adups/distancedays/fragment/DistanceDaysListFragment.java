package com.adups.distancedays.fragment;

import android.os.Bundle;

import butterknife.BindView;

import android.widget.ListView;
import android.widget.TextView;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;

/**
 * 列表状态的倒数日
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class DistanceDaysListFragment extends BaseFragment {

    @BindView(R.id.tv_date_title)
    TextView tvDateTitle;
    @BindView(R.id.tv_date_subtitle)
    TextView tvDateSubtitle;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.lv_list_view)
    ListView mListView;

    public static DistanceDaysListFragment newInstance() {
        Bundle bundle = new Bundle();
        DistanceDaysListFragment fragment = new DistanceDaysListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_distance_days_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mListView.setAdapter();
    }

}
