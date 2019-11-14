package com.adups.distancedays.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.adups.distancedays.R;
import com.adups.distancedays.adapter.CommonAdapter;
import com.adups.distancedays.base.BaseDialogFragment;
import com.adups.distancedays.manager.AppShareManager;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.model.ShareModel;
import com.adups.distancedays.statistics.StatisticsEventConstant;
import com.adups.distancedays.statistics.StatisticsUtil;
import com.adups.distancedays.utils.BundleConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分享入口
 * 
 * Created by Chang.Xiao on 2019/10/21.
 *
 * @version 1.0
 */
public class ShareDialogFragment extends BaseDialogFragment {

    public static final String TAG = "ShareDialogFragment";

    @BindView(R.id.share_grid)
    GridView mGridView;

    private ShareDialogAdapter mAdapter;
    private int bgResId;
    private String title;
    private String day;
    private String dueDate;

    public static ShareDialogFragment newInstance(EventModel eventModel) {
        ShareDialogFragment dialogFragment = new ShareDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleConstants.KEY_MODEL, eventModel);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public static ShareDialogFragment newInstance(int bgResId, String title, String day, String dueDate) {
        ShareDialogFragment dialogFragment = new ShareDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BundleConstants.KEY_RES_ID, bgResId);
        bundle.putString(BundleConstants.KEY_TITLE, title);
        bundle.putString(BundleConstants.KEY_DAY, day);
        bundle.putString(BundleConstants.KEY_DUE_DATE, dueDate);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    protected void getExtraArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bgResId = bundle.getInt(BundleConstants.KEY_RES_ID);
            title = bundle.getString(BundleConstants.KEY_TITLE);
            day = bundle.getString(BundleConstants.KEY_DAY);
            dueDate = bundle.getString(BundleConstants.KEY_DUE_DATE);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_share_dialog;
    }

    @Override
    protected void initPresenter() {
    }

    private ShareModel buildData(int shareType) {
        ShareModel shareModel = new ShareModel();
        shareModel.setShareType(shareType);
        if (shareType == AppShareManager.TYPE_SHARE_TXT) { // text 目前为测试数据
            shareModel.setTitle("测试 title");
            shareModel.setContent("测试 content");
            shareModel.setContentUrl("https://www.baidu.com/");
            return shareModel;
        }
        if (shareType == AppShareManager.TYPE_SHARE_IMG) {
            shareModel.setBgResId(bgResId);
            shareModel.setEventTitle(title);
            shareModel.setDay(day);
            shareModel.setDueDate(dueDate);
        }
        return shareModel;
    }

    @Override
    public void bindView(View view) {
        List<AppShareManager.ShareToType> shareModelList = getShareModelList();
        mAdapter = new ShareDialogAdapter(mContext, R.layout.item_panel_share_grid, shareModelList);
        mGridView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ShareDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, AppShareManager.ShareToType t, int position) {
                handleShareStatistics(t);
                shareTo(mAdapter.getItem(position), buildData(AppShareManager.TYPE_SHARE_IMG));
                dismissAllowingStateLoss();
            }
        });
    }

    private void shareTo(AppShareManager.ShareToType shareToType, ShareModel shareModel) {
        AppShareManager.getInstance().share(getActivity(), shareToType, shareModel);
    }

    /**
     * 组装分享渠道数据
     *
     * @return
     */
    private List<AppShareManager.ShareToType> getShareModelList() {
        List<AppShareManager.ShareToType> shareModels = new ArrayList<>();
        shareModels.add(AppShareManager.ShareToType.TO_WEIXIN_FRIEND);
        shareModels.add(AppShareManager.ShareToType.TO_WEIXIN_GROUP);
        shareModels.add(AppShareManager.ShareToType.TO_QQ);
        return shareModels;
    }

    @OnClick(R.id.tv_dismiss)
    public void onDismissClick() {
        dismissAllowingStateLoss();
    }

    /**
     * 分享渠道统计
     *
     * @param t
     */
    private void handleShareStatistics(AppShareManager.ShareToType t) {
        switch (t) {
            case TO_WEIXIN_FRIEND:
                StatisticsUtil.onEvent(mContext, StatisticsEventConstant.SHARE_POP_WEIXIN);
                break;
            case TO_WEIXIN_GROUP:
                StatisticsUtil.onEvent(mContext, StatisticsEventConstant.SHARE_POP_WECHAT_CIRCLE);
                break;
            case TO_QQ:
                StatisticsUtil.onEvent(mContext, StatisticsEventConstant.SHARE_POP_QQ);
                break;
        }
    }

    /**
     * 分享框adapter
     */
    public static class ShareDialogAdapter extends CommonAdapter<AppShareManager.ShareToType> {

        private OnItemClickListener onItemClickListener;

        public ShareDialogAdapter(Context context, int layoutId, List<AppShareManager.ShareToType> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(com.adups.distancedays.adapter.ViewHolder holder, AppShareManager.ShareToType shareModel) {
            holder.setImageDrawer(R.id.iv_share_icon, mContext.getResources().getDrawable(shareModel.getIconResId()));
            holder.setText(R.id.tv_share_text, mContext.getString(shareModel.getTitleResId()));
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder.getConvertView(), shareModel, holder.getCurrentPosition());
                    }
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public interface OnItemClickListener {

            void onItemClick(View view, AppShareManager.ShareToType t, int position);
        }
    }

}
