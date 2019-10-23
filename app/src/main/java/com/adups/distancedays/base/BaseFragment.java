package com.adups.distancedays.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    private Unbinder mUnBinder;
    protected boolean hasLoadData = false; // 是否加载了数据
    protected boolean mVisibleToUser = false;
    private ViewGroup.LayoutParams lp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();

        ViewGroup.LayoutParams vgLp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View loadingView = null, networkErrorView = null, noContentView = null;
        loadingView = getLoadingView();
        if (loadingView == null) {
            networkErrorView = getNetworkErrorView();
            if (networkErrorView == null) {
                noContentView = getNoContentView();
            }
        }
        if (loadingView != null || networkErrorView != null
                || noContentView != null) {

            ViewGroup parentContainer = new FrameLayout(getActivity());
            mContainerView = inflater.inflate(getContentViewId(),
                    parentContainer, true);

            mContainerView.setLayoutParams(vgLp);
            mContainerView.setClickable(true);

            lp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ((FrameLayout.LayoutParams) lp).gravity = Gravity.CENTER;
        } else {
            mContainerView = inflater.inflate(getContentViewId(),
                    container, false);
        }

        mUnBinder = ButterKnife.bind(this, mContainerView);
        init(savedInstanceState);
        return mContainerView;
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

    public View mContainerView;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mNoContentView;

    public enum LoadCompleteType {
        OK, NETWOEKERROR, NOCONTENT, LOADING
    }

    protected View getLoadingView() {
        return null;
    }
    protected View getNetworkErrorView() {
        return null;
    }
    protected View getNoContentView() {
        return null;
    }

    /**
     * 页面数据载入成功之后调用该方法，可以控制页面的loadingview的隐藏或者NetworkErrorView的显示
     * 注意：如果是分页加载数据，仅且只在第一页数据加载完成之后调用
     *
     * @param loadCompleteType
     */
    public void onPageLoadingCompleted(LoadCompleteType loadCompleteType) {

        if (!canUpdateUi())
            return;
        ViewGroup parentView = null;

        switch (loadCompleteType) {
            case NETWOEKERROR:

                if (mLoadingView != null) {
                    parentView = (ViewGroup) mLoadingView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mLoadingView);
                    }
                    mLoadingView = null;
                }
                if (mNoContentView != null) {
                    parentView = (ViewGroup) mNoContentView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mNoContentView);
                    }
                    mNoContentView = null;
                }

                if (mNetworkErrorView == null) {
                    mNetworkErrorView = getNetworkErrorView();
                    if (mNetworkErrorView != null && mContainerView != null) {
                        ((ViewGroup) mContainerView).addView(mNetworkErrorView, lp);
                    }
                } else {
                    mNetworkErrorView.bringToFront();
                }

                if (mNetworkErrorView != null) {
                    if (isNetworkErrorViewClickable) {
                        mNetworkErrorView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                onPageLoadingCompleted(LoadCompleteType.LOADING);
                                loadData();
                            }
                        });
                    } else {
                        mNetworkErrorView.setOnClickListener(null);
                    }
                }
                break;
            case NOCONTENT:
                if (mNetworkErrorView != null) {
                    parentView = (ViewGroup) mNetworkErrorView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mNetworkErrorView);
                    }
                    mNetworkErrorView = null;
                }
                if (mLoadingView != null) {
                    parentView = (ViewGroup) mLoadingView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mLoadingView);
                    }
                    mLoadingView = null;
                }

                if (mNoContentView == null) {
                    mNoContentView = getNoContentView();
                    if (mNoContentView != null && mContainerView != null) {
                        ((ViewGroup) mContainerView).addView(mNoContentView, lp);
                    }
                } else {
                    mNoContentView.bringToFront();
                }
                break;
            case OK:
                if (mNetworkErrorView != null) {
                    parentView = (ViewGroup) mNetworkErrorView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mNetworkErrorView);
                    }
                    mNetworkErrorView = null;
                }
                if (mLoadingView != null) {
                    parentView = (ViewGroup) mLoadingView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mLoadingView);
                    }
                    mLoadingView = null;
                }
                if (mNoContentView != null) {
                    parentView = (ViewGroup) mNoContentView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mNoContentView);
                    }
                    mNoContentView = null;
                }
                break;
            case LOADING:
                if (mNetworkErrorView != null) {
                    parentView = (ViewGroup) mNetworkErrorView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mNetworkErrorView);
                    }
                    mNetworkErrorView = null;
                }

                if (mNoContentView != null) {
                    parentView = (ViewGroup) mNoContentView.getParent();
                    if (parentView != null) {
                        parentView.removeView(mNoContentView);
                    }
                    mNoContentView = null;
                }

                if (mLoadingView == null) {
                    mLoadingView = getLoadingView();
                    if (mLoadingView != null && mContainerView != null) {
                        mLoadingView.setVisibility(View.VISIBLE);
                        ((ViewGroup) mContainerView).addView(mLoadingView, lp);
                    }
                } else {
                    mLoadingView.bringToFront();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 异步请求结束之后是否可以更新ui
     *
     * @return
     */
    public boolean canUpdateUi() {
        if (!isAdded() || isRemoving() || isDetached()
                || getActivity() == null || mActivity == null
                || getContext() == null) {
            return false;
        }
        return true;
    }

    private boolean isNetworkErrorViewClickable = true;

    public void setNetworkErrorViewClickable(boolean isClick) {
        this.isNetworkErrorViewClickable = isClick;
    }
}
