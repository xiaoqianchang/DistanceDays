package com.adups.distancedays.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.adups.distancedays.R;
import com.adups.distancedays.adapter.CommonAdapter;
import com.adups.distancedays.adapter.ViewHolder;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.db.DBHelper;
import com.adups.distancedays.db.EntityConverter;
import com.adups.distancedays.db.dao.EventDao;
import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.FormatHelper;
import com.adups.distancedays.utils.ToolUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 网格状态的倒数日
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class DistanceDaysGridFragment extends BaseFragment {

    @BindView(R.id.gv_card_grid)
    GridView gvCardGrid;

    public static DistanceDaysGridFragment newInstance() {
        Bundle bundle = new Bundle();
        DistanceDaysGridFragment fragment = new DistanceDaysGridFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_distance_days_grid;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        EventDao eventDao = DBHelper.getInstance(getContext()).getDaoSession().getEventDao();
        List<EventEntity> entityList = eventDao.loadAll();
        // 转换数据表模型为界面数据模型
        List<EventModel> eventModels = convertToEventModel(entityList);

        gvCardGrid.setAdapter(new CommonAdapter<EventModel>(getContext(), R.layout.view_distance_days_card_layout, eventModels) {
            @Override
            protected void convert(ViewHolder holder, EventModel eventModel) {
                holder.setText(R.id.title, Html.fromHtml(FormatHelper.getDateCardTitlePartBold(eventModel, this.mContext)).toString());
                holder.setText(R.id.date, String.valueOf(eventModel.getDays()));
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(eventModel.getTargetTime());
                holder.setText(R.id.due_date, DateUtils.getFormatedDate(mContext, instance, 2, eventModel.isLunarCalendar()));
                if (eventModel.isOutOfTargetDate()) {
                    holder.getView(R.id.title).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date_passed));
                } else {
                    holder.getView(R.id.title).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date));
                }

            }
        });
    }

    private List<EventModel> convertToEventModel(List<EventEntity> entityList) {
        if (ToolUtil.isEmptyCollects(entityList)) {
            return null;
        }
        List<EventModel> list = new ArrayList<>();
        for (EventEntity entity : entityList) {
            EventModel eventModel = EntityConverter.convertToEventModel(entity);
            if (eventModel != null) {
                list.add(eventModel);
            }
        }
        return list;
    }

    public void refreshUi() {
        loadData();
    }
}
