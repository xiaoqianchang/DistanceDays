package com.adups.distancedays.provider;

import android.content.Context;
import android.view.View;

import com.adups.distancedays.R;
import com.adups.distancedays.adapter.ItemModel;
import com.adups.distancedays.adapter.MultiViewTypeSupport;
import com.adups.distancedays.adapter.ViewHolder;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.fragment.DistanceDaysGridFragment;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.FormatHelper;

import java.util.Calendar;

/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/25.
 *
 * @version 1.0
 */
public class RowAdapterProvider implements MultiViewTypeSupport<EventModel> {

    private BaseFragment fragment;
    private Context mContext;

    public RowAdapterProvider(DistanceDaysGridFragment fragment) {
        this.fragment = fragment;
        this.mContext = fragment.getContext();
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_distance_days_card_layout;
    }

    @Override
    public void convert(ViewHolder holder, ItemModel<EventModel> t, View convertView, int position) {
        if (t == null || t.getModel() == null) {
            return;
        }
        EventModel eventModel = t.getModel();
        String title = FormatHelper.getDateCardTitle(eventModel, this.mContext);
        holder.setText(R.id.title, title);
        holder.setText(R.id.date, String.valueOf(eventModel.getDays()));
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(eventModel.getTargetTime());
        holder.setText(R.id.due_date, mContext.getString(R.string.string_target_date, DateUtils.getFormatedDate(mContext, instance, 2, eventModel.isLunarCalendar())));
        if (eventModel.isOutOfTargetDate()) {
            holder.getView(R.id.title).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date_passed));
        } else {
            holder.getView(R.id.title).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date));
        }
    }
}
