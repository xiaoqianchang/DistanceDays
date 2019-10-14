package com.adups.distancedays.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment的基类
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    protected View mRootView;
    private Unbinder mUnBinder;
    protected boolean hasLoadData = false; // 是否加载了数据
    protected boolean mVisibleToUser = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mRootView = inflater.inflate(getContentViewId(), container, false);
        mUnBinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isAdded()) {
            return;
        }
        if (isVisibleToUser && !hasLoadData) {
            hasLoadData = true;
            loadData();
            Logger.d(this + "__setUserVisibleHint_loadData");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mVisibleToUser = true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mVisibleToUser = !hidden;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            hasLoadData = true;
            loadData();
            Logger.d(this + "__onActivityCreated_loadData");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        hideLoadingDialog();
        mUnBinder.unbind();
    }

    protected abstract int getContentViewId();
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 在该方法中进行数据初始化，当NetworkError，点击NetworkErrorView,会自动重新执行该方法
     */
    protected void loadData() {
    }

    /**
     * 用于判断fragment是否获取到了父activity实例
     */
    protected boolean isActivityEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return isAdded() && (mActivity != null) && !mActivity.isFinishing() && !mActivity.isDestroyed();
        } else {
            return isAdded() && (mActivity != null) && !mActivity.isFinishing();
        }
    }
}
