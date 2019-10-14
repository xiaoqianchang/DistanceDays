package com.adups.distancedays.fragment;

import android.os.Bundle;

import butterknife.BindView;

import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.adups.distancedays.R;
import com.adups.distancedays.adapter.CommonAdapter;
import com.adups.distancedays.adapter.ViewHolder;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.db.DBHelper;
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
        refreshHeaderView(eventModels);

        mListView.setAdapter(new CommonAdapter<EventModel>(getContext(), R.layout.view_distance_days_row_layout, eventModels) {
            @Override
            protected void convert(ViewHolder holder, EventModel eventModel) {
                holder.setText(R.id.tv_event_content, Html.fromHtml(FormatHelper.getDateCardTitlePartBold(eventModel, this.mContext)).toString());
                holder.setText(R.id.tv_event_date, String.valueOf(eventModel.getDays()));
//                holder.c.setText(this.mContext.getResources().getQuantityString(R.plurals.plurals_day_in_card, data.getDays()));
                if (eventModel.isOutOfDate()) {
                    holder.getView(R.id.tv_event_date).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date_passed));
                    holder.getView(R.id.tv_day).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_day_passed));
                } else {
                    holder.getView(R.id.tv_event_date).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date));
                    holder.getView(R.id.tv_day).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_day));
                }

            }
        });
    }

    private void refreshHeaderView(List<EventModel> eventModels) {
        if (ToolUtil.isEmptyCollects(eventModels))
            return;
        EventModel eventModel = eventModels.get(0);
        tvDateTitle.setText(eventModel.getTitle());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(eventModel.getDueDate());
        tvDateSubtitle.setText("目标日：" + DateUtils.getFormatedDate(getContext(), calendar, 2, eventModel.isLunarCalendar()));
        tvDays.setText(String.valueOf(eventModel.getDays()));
    }

    private List<EventModel> convertToEventModel(List<EventEntity> entityList) {
        if (ToolUtil.isEmptyCollects(entityList)) {
            return null;
        }
        List<EventModel> list = new ArrayList<>();
        for (EventEntity entity : entityList) {
            EventModel eventModel = convertToEventModel(entity);
            if (eventModel != null) {
                list.add(eventModel);
            }
        }
        return list;
    }

    private EventModel convertToEventModel(EventEntity model) {
        if (model == null) {
            return null;
        }

        EventModel cardItem = new EventModel();
        Calendar dueDate = Calendar.getInstance();
        Calendar todayDate = Calendar.getInstance();
        dueDate.setTimeInMillis(model.getTargetDate());
//        dueDate = getRepeatedDueDateNew(dueDate, model.getRepeatType(), model.getInterval(), model.isLunarCalendar());
        int days = (int) DateUtils.getDateOffset(dueDate, todayDate);
        cardItem.setOutOfDate(days < 0);
//        int requestCodeToday = model.getAlarmRequestCodeToday();
//        int requestCodeYesterday = model.getAlarmRequestCodeYesterDay();
//        cardItem.setRequestCodeToday(requestCodeToday);
//        cardItem.setRequestCodeYesterday(requestCodeYesterday);
        cardItem.setDueDate(model.getTargetDate());
        cardItem.setDays(Math.abs(days));
        cardItem.setTitle(model.getEventContent());
        cardItem.setLunarCalendar(model.getIsLunarCalendar());
        cardItem.setHasEndDate(false);
        cardItem.setRepeatType(model.getRepeatType());
//        int interval = model.getInterval();
//        if (model.getRepeatType() != 0 && interval == 0) {
//            interval = 1;
//        }
//        cardItem.setRepeatInterval(interval);
        return cardItem;
    }

    public void refreshUi() {
        loadData();
    }
}
