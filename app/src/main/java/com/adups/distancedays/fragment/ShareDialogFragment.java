package com.adups.distancedays.fragment;


import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adups.distancedays.R;
import com.adups.distancedays.adapter.CommonAdapter;
import com.adups.distancedays.base.BaseDialogFragment;
import com.adups.distancedays.manager.ShareManager;
import com.adups.distancedays.model.BaseShareModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    private BaseShareModel mShareModel;

    public static ShareDialogFragment newInstance() {
        ShareDialogFragment dialogFragment = new ShareDialogFragment();
        return dialogFragment;
    }

    @Override
    protected void getExtraArguments() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_share_dialog;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void bindView(View view) {
        List<ShareManager.ShareToType> shareModelList = getShareModelList();
        mAdapter = new ShareDialogAdapter(mContext, R.layout.item_panel_share_grid, shareModelList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shareTo(mAdapter.getItem(position), mShareModel);
            }
        });
    }

    private void shareTo(ShareManager.ShareToType shareToType, BaseShareModel mShareModel) {
        switch (shareToType){
//            case TO_WEIXIN_FRIEND:
//                ShareManager.getInstance(mActRef.get()).shareToWX(shareModel,shareToType);
//                break;
//            case TO_WEIXIN_GROUP:
//                ShareManager.getInstance(mActRef.get()).shareToWX(shareModel,shareToType);
//                break;
//            case TO_QQ:
//                ShareManager.getInstance(mActRef.get()).shareToSinaWeibo(shareModel);
//                break;
        }
    }

    /**
     * 组装分享渠道数据
     *
     * @return
     */
    private List<ShareManager.ShareToType> getShareModelList() {
        List<ShareManager.ShareToType> shareModels = new ArrayList<>();
        shareModels.add(ShareManager.ShareToType.TO_WEIXIN_FRIEND);
        shareModels.add(ShareManager.ShareToType.TO_WEIXIN_GROUP);
        shareModels.add(ShareManager.ShareToType.TO_QQ);
        return shareModels;
    }

    /**
     * 分享框adapter
     */
    public static class ShareDialogAdapter extends CommonAdapter<ShareManager.ShareToType> {

        public ShareDialogAdapter(Context context, int layoutId, List<ShareManager.ShareToType> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(com.adups.distancedays.adapter.ViewHolder holder, ShareManager.ShareToType shareModel) {
            holder.setImageDrawer(R.id.iv_share_icon, mContext.getResources().getDrawable(shareModel.getIconResId()));
            holder.setText(R.id.tv_share_text, mContext.getString(shareModel.getTitleResId()));
        }
    }

}
