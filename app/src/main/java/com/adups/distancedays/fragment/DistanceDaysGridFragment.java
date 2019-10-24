package com.adups.distancedays.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adups.distancedays.R;
import com.adups.distancedays.activity.EventDetailActivity;
import com.adups.distancedays.adapter.CommonAdapter;
import com.adups.distancedays.adapter.ViewHolder;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.db.DBHelper;
import com.adups.distancedays.db.EntityConverter;
import com.adups.distancedays.db.dao.EventDao;
import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.utils.AppConstants;
import com.adups.distancedays.utils.BundleConstants;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.FormatHelper;
import com.adups.distancedays.utils.ToolUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

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

    private CommonAdapter mAdapter;

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

        gvCardGrid.setAdapter(mAdapter = new CommonAdapter<EventModel>(getContext(), R.layout.view_distance_days_card_layout, eventModels) {
            @Override
            protected void convert(ViewHolder holder, EventModel eventModel) {
                String title = FormatHelper.getDateCardTitle(eventModel, this.mContext);
                holder.setText(R.id.title, title);
                holder.setText(R.id.date, String.valueOf(eventModel.getDays()));
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(eventModel.getTargetTime());
                holder.setText(R.id.due_date, getString(R.string.string_target_date, DateUtils.getFormatedDate(mContext, instance, 2, eventModel.isLunarCalendar())));
                if (eventModel.isOutOfTargetDate()) {
                    holder.getView(R.id.title).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date_passed));
                } else {
                    holder.getView(R.id.title).setBackground(mContext.getResources().getDrawable(R.drawable.bg_date_card_small_date));
                }

            }
        });
        gvCardGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EventDetailActivity.class);
                Bundle bundle = new Bundle();
                EventModel model = (EventModel) mAdapter.getItem(position);
                bundle.putSerializable(BundleConstants.KEY_MODEL, model);
                intent.putExtras(bundle);
                startActivityForResult(intent, AppConstants.RequestCode.CODE_EVENT_DETAIL);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.RequestCode.CODE_EVENT_DETAIL:
                    refreshUi();
                    break;
            }
        }
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
