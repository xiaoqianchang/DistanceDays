package com.adups.distancedays.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.event.TimeChangeEvent;
import com.adups.distancedays.utils.AppConstants;
import com.adups.distancedays.utils.EventUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;

/**
 * 倒数日
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class DistanceDaysFragment extends BaseFragment {

    private static final int TYPE_LIST = 1; // 列表视图
    private static final int TYPE_CARD = 2; // 卡片视图

    private int mType = TYPE_CARD;
    private Fragment mCurrentFragment;
    private boolean mDelayRefresh; // 是否在界面可见时刷新界面

    public static DistanceDaysFragment newInstance() {
        Bundle bundle = new Bundle();
        DistanceDaysFragment fragment = new DistanceDaysFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventUtil.register(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_distance_days;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        switchView(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mDelayRefresh) {
            refreshUi();
            mDelayRefresh = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.RequestCode.CODE_EVENT_ADD:
                    refreshUi();
                    break;
            }
        }
    }

    private void refreshUi() {
        if (mType == TYPE_CARD && mCurrentFragment instanceof DistanceDaysGridFragment) {
            ((DistanceDaysGridFragment) mCurrentFragment).refreshUi();
        } else {
            ((DistanceDaysListFragment) mCurrentFragment).refreshUi();
        }
    }

    /**
     * 系统时间改变 event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeChangeEvent(TimeChangeEvent event) {
        if (!canUpdateUi()) {
            return;
        }
        // 刷新数据
        if (isVisible() && mVisibleToUser) {
            refreshUi();
        } else {
            mDelayRefresh = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    @Override
    public void onDetach() {
        super.onDetach();
        EventUtil.unregister(this);
    }
}
