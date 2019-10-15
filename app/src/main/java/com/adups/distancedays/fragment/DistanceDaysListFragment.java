package com.adups.distancedays.fragment;

import android.os.Bundle;

import butterknife.BindView;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

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
 * 列表状态的倒数日
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class DistanceDaysListFragment extends BaseFragment {

    @BindView(R.id.tv_date_title)
    TextView tvDateTitle;
    @BindView(R.id.tv_date_subtitle)
    TextView tvDateSubtitle;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.lv_list_view)
    ListView mListView;

    public static DistanceDaysListFragment newInstance() {
        Bundle bundle = new Bundle();
        DistanceDaysListFragment fragment = new DistanceDaysListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_distance_days_list;
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
        EventModel headerViewEventModel = getHeaderViewEventModel(eventModels);
        refreshHeaderView(headerViewEventModel);

        mListView.setAdapter(new CommonAdapter<EventModel>(getContext(), R.layout.view_distance_days_row_layout, eventModels) {
            @Override
            protected void convert(ViewHolder holder, EventModel eventModel) {
                holder.setText(R.id.tv_event_content, Html.fromHtml(FormatHelper.getDateCardTitlePartBold(eventModel, this.mContext)).toString());
                holder.setText(R.id.tv_event_date, String.valueOf(eventModel.getDays()));
//                holder.c.setText(this.mContext.getResources().getQuantityString(R.plurals.plurals_day_in_card, data.getDays()));
                if (eventModel.isOutOfTargetDate()) {
                    holder.getView(R.id.tv_event_date).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date_passed));
                    holder.getView(R.id.tv_day).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_day_passed));
                } else {
                    holder.getView(R.id.tv_event_date).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date));
                    holder.getView(R.id.tv_day).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_day));
                }

            }
        });
    }

    /**
     * header 显示规则：
     * 1. 事件中如果有置顶的就显示置顶数据，
     * 2. 如果没有就显示列表数据第一条，
     * 3. 新增置顶数据时，之前的置顶数据改为非置顶即所有数据中只有一条置顶数据
     *
     * @param eventModels
     */
    private EventModel getHeaderViewEventModel(List<EventModel> eventModels) {
        if (ToolUtil.isEmptyCollects(eventModels))
            return null;
        for (EventModel model : eventModels) {
            if (model.isTop()) {
                return model;
            }
        }
        return eventModels.get(0);
    }

    private void refreshHeaderView(EventModel eventModel) {
        if (eventModel == null) {
            return;
        }
        tvDateTitle.setText(eventModel.getEventTitle());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(eventModel.getTargetTime());
        String formatDate = DateUtils.getFormatedDate(getContext(), calendar, 2, eventModel.isLunarCalendar());
        tvDateSubtitle.setText(getString(R.string.string_target_date, formatDate));
        tvDays.setText(String.valueOf(eventModel.getDays()));
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