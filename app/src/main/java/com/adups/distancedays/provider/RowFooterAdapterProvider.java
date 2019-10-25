package com.adups.distancedays.provider;

import android.content.Context;
import android.view.View;

import com.adups.distancedays.R;
import com.adups.distancedays.adapter.ItemModel;
import com.adups.distancedays.adapter.MultiViewTypeSupport;
import com.adups.distancedays.adapter.ViewHolder;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.fragment.DistanceDaysGridFragment;
import com.adups.distancedays.model.FooterModel;

/**
 * 网课 footer
 * <p>
 * Created by Chang.Xiao on 2019/10/25.
 *
 * @version 1.0
 */
public class RowFooterAdapterProvider implements MultiViewTypeSupport<FooterModel> {

    private BaseFragment fragment;
    private Context mContext;

    public RowFooterAdapterProvider(DistanceDaysGridFragment fragment) {
        this.fragment = fragment;
        this.mContext = fragment.getContext();
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_distance_days_card_footer;
    }

    @Override
    public void convert(ViewHolder holder, ItemModel<FooterModel> t, View convertView, int position) {
        if (t == null || t.getModel() == null) {
            return;
        }
        FooterModel model = t.getModel();
        holder.setImageResource(R.id.iv_add, model.getResId());
    }
}
