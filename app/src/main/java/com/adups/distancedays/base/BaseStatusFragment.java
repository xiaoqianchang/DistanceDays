package com.adups.distancedays.base;

import android.view.View;
import android.widget.TextView;

import com.adups.distancedays.R;

/**
 * 公共实现包含加载、无内容、无网络状态的Fragment
 * <p>
 * Created by Chang.Xiao on 2019/10/23.
 *
 * @version 1.0
 */
public abstract class BaseStatusFragment extends BaseFragment {

    @Override
    protected View getLoadingView() {
        return null;
    }

    @Override
    protected View getNetworkErrorView() {
        boolean showButton = setNetworkErrorButtonVisiblity();

        View networkErrorView = View.inflate(getActivity(),
                R.layout.view_no_net_layout, null);

        if (showButton) {
            TextView networkErrorViewBtnTextView = (TextView) networkErrorView
                    .findViewById(R.id.btn_no_net);
            if (networkErrorViewBtnTextView != null) {
                networkErrorViewBtnTextView.setVisibility(View.VISIBLE);
                networkErrorViewBtnTextView
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                onPageLoadingCompleted(LoadCompleteType.LOADING);
                                loadData();
                            }
                        });
            }
        }
        return networkErrorView;
    }

    @Override
    protected View getNoContentView() {
        return null;
    }

    /**
     * 设置网络异常页面button是否可见
     *
     * @return true:可见
     */
    protected boolean setNetworkErrorButtonVisiblity() {
        return false;
    }
}
