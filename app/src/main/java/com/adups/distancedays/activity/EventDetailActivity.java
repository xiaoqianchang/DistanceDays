package com.adups.distancedays.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.adups.distancedays.R;
import com.adups.distancedays.base.ToolBarActivity;
import com.adups.distancedays.fragment.ShareDialogFragment;
import com.adups.distancedays.model.EventModel;
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
        tvTitle.setText(Html.fromHtml(FormatHelper.getDateCardTitlePartBold(mEventModel, this.mContext)).toString());
        tvDay.setText(DateUtils.getFormatDaysText(mEventModel.getDays(), String.valueOf(mEventModel.getDays())));
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(mEventModel.getTargetTime());
        tvDueDate.setText(getString(R.string.string_target_date, DateUtils.getFormatedDate(mContext, instance, 2, mEventModel.isLunarCalendar())));
    }

    @Override
    public Runnable getMenuEditEventAction() {
        return new Runnable() {
            @Override
            public void run() {
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
        ShareDialogFragment dialogFragment = ShareDialogFragment.newInstance(tvTitle.getText().toString(), tvDay.getText().toString(), tvDueDate.getText().toString());
        dialogFragment.show(getSupportFragmentManager(), ShareDialogFragment.TAG);
    }

    /**
     * 天数点击事件
     */
    @OnClick(R.id.tv_day)
    public void onDaysClick() {
        if (tvDay == null || mEventModel == null) {
            return;
        }
        tvDay.setText(DateUtils.getFormatDaysText(mEventModel.getDays(), tvDay.getText().toString()));
    }
}
