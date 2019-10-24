package com.adups.distancedays.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.utils.AppConstants;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;

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

    private int mType = TYPE_CARD;
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.RequestCode.CODE_EVENT_ADD:
                    if (mType == TYPE_CARD && mCurrentFragment instanceof DistanceDaysGridFragment) {
                        ((DistanceDaysGridFragment) mCurrentFragment).refreshUi();
                    } else {
                        ((DistanceDaysListFragment) mCurrentFragment).refreshUi();
                    }
                    break;
            }
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
}
