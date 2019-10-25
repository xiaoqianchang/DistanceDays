package com.adups.distancedays.fragment;


import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adups.distancedays.MainActivity;
import com.adups.distancedays.R;
import com.adups.distancedays.activity.EventDetailActivity;
import com.adups.distancedays.adapter.ItemModel;
import com.adups.distancedays.adapter.ItemViewTypeConstant;
import com.adups.distancedays.adapter.MultiViewTypeAdapter;
import com.adups.distancedays.adapter.MultiViewTypeSupport;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.db.DBHelper;
import com.adups.distancedays.db.EntityConverter;
import com.adups.distancedays.db.dao.EventDao;
import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.model.FooterModel;
import com.adups.distancedays.provider.RowAdapterProvider;
import com.adups.distancedays.provider.RowFooterAdapterProvider;
import com.adups.distancedays.utils.AppConstants;
import com.adups.distancedays.utils.BundleConstants;
import com.adups.distancedays.utils.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private MultiViewTypeAdapter mAdapter;

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
        Map<Integer, MultiViewTypeSupport> map = new HashMap<Integer, MultiViewTypeSupport>() {
            {
                put(ItemViewTypeConstant.VIEW_TYPE_ROW_DATA, new RowAdapterProvider(DistanceDaysGridFragment.this));
                put(ItemViewTypeConstant.VIEW_TYPE_FOOTER, new RowFooterAdapterProvider(DistanceDaysGridFragment.this));
            }
        };
        mAdapter = new MultiViewTypeAdapter(getContext(), map);
        gvCardGrid.setAdapter(mAdapter);

        gvCardGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemModel item = mAdapter.getItem(position);
                if (item != null && item.getModel() != null) {
                    if (item.getModel() instanceof FooterModel) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).getMenuAddEventAction().run();
                        }
                    } else if (item.getModel() instanceof EventModel) {
                        Intent intent = new Intent(getContext(), EventDetailActivity.class);
                        Bundle bundle = new Bundle();
                        EventModel model = (EventModel) item.getModel();
                        bundle.putSerializable(BundleConstants.KEY_MODEL, model);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, AppConstants.RequestCode.CODE_EVENT_DETAIL);
                    }
                }
            }
        });
    }

    @Override
    protected void loadData() {
        EventDao eventDao = DBHelper.getInstance(getContext()).getDaoSession().getEventDao();
        List<EventEntity> entityList = eventDao.loadAll();
        // 转换数据表模型为界面数据模型
        List<EventModel> eventModels = convertToEventModel(entityList);

        if (mAdapter != null) {
            mAdapter.clear();
            mAdapter.addAll(eventModels, ItemViewTypeConstant.VIEW_TYPE_ROW_DATA);
            mAdapter.add(new FooterModel(R.mipmap.ic_distance_days_card_footer, ""), ItemViewTypeConstant.VIEW_TYPE_FOOTER);
        }
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
