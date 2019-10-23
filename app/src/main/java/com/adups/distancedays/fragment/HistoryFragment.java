package com.adups.distancedays.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import retrofit2.Call;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.adups.distancedays.R;
import com.adups.distancedays.adapter.CommonAdapter;
import com.adups.distancedays.adapter.ViewHolder;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.base.BaseStatusFragment;
import com.adups.distancedays.http.HttpConstant;
import com.adups.distancedays.http.OkHttpWrapper;
import com.adups.distancedays.http.ResponseCallBack;
import com.adups.distancedays.model.BaseModel;
import com.adups.distancedays.model.HistoryInTodayModel;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.GlideUtil;
import com.adups.distancedays.utils.PackageUtil;
import com.adups.distancedays.utils.ToastUtil;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

/**
 * 历史上今天
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class HistoryFragment extends BaseStatusFragment {

    @BindView(R.id.tv_year_first)
    TextView tvYearFirst;
    @BindView(R.id.tv_year_second)
    TextView tvYearSecont;
    @BindView(R.id.tv_year_third)
    TextView tvYearThird;
    @BindView(R.id.tv_year_fourth)
    TextView tvYearFourth;

    @BindView(R.id.tv_month_first)
    TextView tvMonthFirst;
    @BindView(R.id.tv_month_second)
    TextView tvMonthSecond;

    @BindView(R.id.tv_day_first)
    TextView tvDayFirst;
    @BindView(R.id.tv_day_second)
    TextView tvDaySecont;

    @BindView(R.id.lv_list_view)
    ListView mListView;

    public static HistoryFragment newInstance() {
        Bundle bundle = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        refreshHeaderView();
    }

    @Override
    protected void loadData() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Call<BaseModel<List<HistoryInTodayModel>>> call = OkHttpWrapper.getInstance().getNetApiInstance().getTodayInHistory(HttpConstant.URL_HISTORY_IN_TODAY, HttpConstant.DAILY_ARTICLE_KEY, PackageUtil.getVersionName(), month, day);
        call.enqueue(new ResponseCallBack<BaseModel<List<HistoryInTodayModel>>>() {
            @Override
            public void onSuccess(BaseModel<List<HistoryInTodayModel>> listBaseModel) {
                List<HistoryInTodayModel> models = listBaseModel.getResult();
                refreshUi(models);
            }

            @Override
            public void onError(int code, String msg) {
                if (canUpdateUi()) {
                    onPageLoadingCompleted(LoadCompleteType.NETWOEKERROR);
                }
            }
        });
    }

    private void refreshHeaderView() {
        String currentTimeStr = DateUtils.getCurrentTimeStr(DateUtils.TIME_FORMAT1);
        char[] years = currentTimeStr.toCharArray();
        tvYearFirst.setText(String.valueOf(years[0]));
        tvYearSecont.setText(String.valueOf(years[1]));
        tvYearThird.setText(String.valueOf(years[2]));
        tvYearFourth.setText(String.valueOf(years[3]));
        tvMonthFirst.setText(String.valueOf(years[4]));
        tvMonthSecond.setText(String.valueOf(years[5]));
        tvDayFirst.setText(String.valueOf(years[6]));
        tvDaySecont.setText(String.valueOf(years[7]));
    }

    private void refreshUi(List<HistoryInTodayModel> models) {
        mListView.setAdapter(new CommonAdapter<HistoryInTodayModel>(getContext(), R.layout.view_history_in_today_layout, models) {
            @Override
            protected void convert(ViewHolder holder, HistoryInTodayModel historyInTodayModel) {
                holder.setText(R.id.tv_title, historyInTodayModel.getTitle());
                ImageView imgPic = holder.getView(R.id.img_pic);
                GlideUtil.loadImage(HistoryFragment.this, historyInTodayModel.getPic(), imgPic);
                int year = historyInTodayModel.getYear();
                int month = historyInTodayModel.getMonth();
                int day = historyInTodayModel.getDay();
                holder.setText(R.id.tv_date, getString(R.string.string_date, String.valueOf(year), String.valueOf(month), String.valueOf(day)));
                holder.setText(R.id.tv_desc, historyInTodayModel.getDes());
            }
        });
    }
}
