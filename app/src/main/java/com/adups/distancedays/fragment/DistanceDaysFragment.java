package com.adups.distancedays.fragment;

import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.event.EditEventSuccess;
import com.adups.distancedays.utils.EventUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 距离日
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class DistanceDaysFragment extends BaseFragment {

    private static final int TYPE_LIST = 1; // 列表视图
    private static final int TYPE_CARD = 2; // 卡片视图

    private int mType = TYPE_LIST;
    private Fragment mCurrentFragment;

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
        switchView(false);
        EventUtil.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addEventSuccess(EditEventSuccess event) {
        // 刷新视图
        if (mType == TYPE_CARD && mCurrentFragment instanceof DistanceDaysGridFragment) {
            ((DistanceDaysGridFragment) mCurrentFragment).refreshUi();
        } else {
            ((DistanceDaysListFragment) mCurrentFragment).refreshUi();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventUtil.unregister(this);
    }

    public void switchView(boolean isSwitch) {
        if (isSwitch) {
            if (mType == TYPE_CARD) {
                mType = TYPE_LIST;
            } else {
                mType = TYPE_CARD;
            }
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        if (mType == TYPE_CARD) {
            mCurrentFragment = DistanceDaysGridFragment.newInstance();
        } else {
            mCurrentFragment = DistanceDaysListFragment.newInstance();
        }
        transaction.replace(R.id.fragment_container, mCurrentFragment);
        transaction.commitAllowingStateLoss();
    }
}
