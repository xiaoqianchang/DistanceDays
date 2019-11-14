package com.adups.distancedays.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.adups.distancedays.R;
import com.adups.distancedays.base.ToolBarActivity;
import com.adups.distancedays.fragment.ShareDialogFragment;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.statistics.StatisticsEventConstant;
import com.adups.distancedays.statistics.StatisticsUtil;
import com.adups.distancedays.utils.AppConstants;
import com.adups.distancedays.utils.BundleConstants;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.FormatHelper;
import com.adups.distancedays.utils.ToastUtil;
import com.adups.distancedays.view.ScalableTextView;

import java.util.Calendar;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 事件详情
 * <p>
 * Created by Chang.Xiao on 2019/10/15.
 *
 * @version 1.0
 */
public class EventDetailActivity extends ToolBarActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_day)
    ScalableTextView tvDay;
    @BindView(R.id.tv_due_date)
    TextView tvDueDate;

    private EventModel mEventModel;
    private boolean isLunarCalendar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        parseBundle();
        refreshUi();
        setMenuTypes(MENU_TYPE_EDIT_EVENT);
    }

    private void parseBundle() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            mEventModel = (EventModel) bundle.get(BundleConstants.KEY_MODEL);
        }
    }

    private void refreshUi() {
        if (mEventModel == null) {
            return;
        }
        if (mEventModel.isOutOfTargetDate()) {
            tvTitle.setBackgroundResource(R.drawable.bg_detail_date_title_orange);
        } else {
            tvTitle.setBackgroundResource(R.drawable.bg_detail_date_title_blue);
        }
        tvTitle.setText(FormatHelper.getDateCardTitle(mEventModel, this.mContext));
        tvDay.setText(getString(R.string.string_day, String.valueOf(mEventModel.getDays())));
        refreshDueDate(mEventModel.isLunarCalendar());
    }

    private void refreshDueDate(boolean isLunarCalendar) {
        if (mEventModel == null) {
            return;
        }
        this.isLunarCalendar = isLunarCalendar;
        Calendar instance = Calendar.getInstance();
        instance.setTime(mEventModel.getTargetDate());
        tvDueDate.setText(getString(R.string.string_target_date, DateUtils.getFormatedDate(mContext, instance, 2, isLunarCalendar)));
    }

    @Override
    public Runnable getMenuEditEventAction() {
        return new Runnable() {
            @Override
            public void run() {
                StatisticsUtil.onEvent(mContext, StatisticsEventConstant.COUNTDOWN_DETAIL_PAGE_EDIT);
                Intent intent = new Intent(mContext, AddEventActivity.class);
                intent.putExtra(BundleConstants.KEY_TYPE, AddEventActivity.TYPE_EDIT);
                intent.putExtra(BundleConstants.KEY_MODEL, mEventModel);
                startActivityForResult(intent, AppConstants.RequestCode.CODE_EVENT_EDIT);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.RequestCode.CODE_EVENT_EDIT:
                    if (null != data) {
                        int type = data.getIntExtra(BundleConstants.KEY_TYPE, AddEventActivity.TYPE_EDIT);
                        switch (type) {
                            case AddEventActivity.TYPE_EDIT:
                                mEventModel = (EventModel) data.getSerializableExtra(BundleConstants.KEY_MODEL);
                                // 刷新界面
                                refreshUi();
                                setResult(RESULT_OK, data);
                                break;
                            case AddEventActivity.TYPE_DELETE:
                                setResult(RESULT_OK, data);
                                finish();
                                break;
                        }
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.fab_button)
    public void onShareClick() {
        int bgResId;
        if (mEventModel.isOutOfTargetDate()) {
            bgResId = R.mipmap.ic_share_orange_bg;
        } else {
            bgResId = R.mipmap.ic_share_blue_bg;
        }
        ShareDialogFragment dialogFragment = ShareDialogFragment.newInstance(bgResId, tvTitle.getText().toString(), tvDay.getText().toString(), tvDueDate.getText().toString());
        dialogFragment.show(getSupportFragmentManager(), ShareDialogFragment.TAG);
        StatisticsUtil.onEvent(mContext, StatisticsEventConstant.SHARE_BUTTON_CLICK);
    }

    /**
     * 天数点击事件
     */
    @OnClick(R.id.tv_day)
    public void onDaysClick() {
        if (tvDay == null || mEventModel == null) {
            return;
        }
        StatisticsUtil.onEvent(mContext, StatisticsEventConstant.CARD_INTERMEDIATE_DATE_CLICK);
        tvDay.setText(DateUtils.getFormatDaysText(mEventModel.getDays(), tvDay.getText().toString()));
    }

    @OnClick(R.id.tv_due_date)
    public void onDueDateClick() {
        StatisticsUtil.onEvent(mContext, StatisticsEventConstant.CARD_BOTTOM_TARGET_DATE_CLICK);
        refreshDueDate(!isLunarCalendar);
    }
}
