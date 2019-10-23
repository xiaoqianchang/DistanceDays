package com.adups.distancedays.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.adups.distancedays.R;
import com.adups.distancedays.base.ToolBarActivity;
import com.adups.distancedays.db.DBHelper;
import com.adups.distancedays.db.EntityConverter;
import com.adups.distancedays.db.dao.EventDao;
import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.fragment.LunarPickerDialogFragment;
import com.adups.distancedays.model.EventModel;
import com.adups.distancedays.utils.BitmapUtils;
import com.adups.distancedays.utils.BundleConstants;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.ToastUtil;
import com.adups.distancedays.utils.ToolUtil;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 添加事件
 * <p>
 * Created by Chang.Xiao on 2019/10/12.
 *
 * @version 1.0
 */
public class AddEventActivity extends ToolBarActivity {

    public static final int TYPE_ADD = 1; // 新增事件
    public static final int TYPE_EDIT = 2; // 编辑事件
    public static final int TYPE_DELETE = 3; // 删除事件

    @BindView(R.id.edt_event_name)
    EditText edtEventName;
    @BindView(R.id.tv_target_date)
    TextView tvTargetDate;
    @BindView(R.id.switch_calendar)
    Switch switchCalendar;
    @BindView(R.id.switch_top)
    Switch switchTop;
    @BindView(R.id.spinner_repeat)
    Spinner spinnerRepeat;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_save)
    Button btnSave;

    private int mType = TYPE_ADD; // 默认为新增事件
    private boolean mIsLunarCalendar; // 是否为阴历
    private Calendar mTargetCalendar;
    private EventDao mEventDao;
    private int mRepeatType; // 事件重复类型
    private EventModel mEditEventModel; // 需要编辑的model

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_event;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mEventDao = DBHelper.getInstance(mContext).getDaoSession().getEventDao();
        parseBundle();
        initViews();
        refreshUi();
    }

    private void parseBundle() {
        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getIntExtra(BundleConstants.KEY_TYPE, TYPE_ADD);
            mEditEventModel = (EventModel) intent.getSerializableExtra(BundleConstants.KEY_MODEL);
        }
    }

    private void initViews() {
        String[] repeatTypes = getResources().getStringArray(R.array.repeat_type);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, repeatTypes);
        spinnerRepeat.setAdapter(spinnerAdapter);
        spinnerRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRepeatType = position;
                spinnerRepeat.setPrompt(repeatTypes[position]);
                ((TextView) spinnerRepeat.getSelectedView()).setTextColor(Color.parseColor("#3283D2"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.tv_target_date)
    public void onTargetDateClick(View view) {
        if (switchCalendar.isChecked()) {
            LunarPickerDialogFragment dialog2 = LunarPickerDialogFragment.newInstance(mTargetCalendar);
            dialog2.setOnConfirmClickListener(new LunarPickerDialogFragment.OnConfirmClickListener() {
                public void onConfirmClick(Calendar selectedCalendar) {
                    mTargetCalendar = (Calendar) selectedCalendar.clone();
                    tvTargetDate.setText(DateUtils.getFormatedDate(mContext, mTargetCalendar, 2, switchCalendar.isChecked()));
                }
            });
            dialog2.show(getSupportFragmentManager(), "edit");

        } else {
            DialogFragment datePickerFragment = new DatePickerFragment(this);
            datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            //                Calendar calendar = Calendar.getInstance();
            //                new DatePickerDialog(mContext, DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
            //                    @Override
            //                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //                        calendar.set(year,monthOfYear,dayOfMonth);
            //                    }
            //                }, calendar.get(Calendar.YEAR),
            //                        calendar.get(Calendar.MONTH),
            //                        calendar.get(Calendar.DAY_OF_MONTH)
            //                ).show();
        }
    }

    @OnCheckedChanged(R.id.switch_calendar)
    public void onSwitchCalendarClick() {
        mIsLunarCalendar = switchCalendar.isChecked();
        refreshTargetCalendar();
    }

    /**
     * 添加事件
     */
    @OnClick(R.id.btn_save)
    public void onSaveClick() {
        String eventName = edtEventName.getText().toString().trim();
        if (TextUtils.isEmpty(eventName)) {
            ToastUtil.showToast("请输入事件标题");
            return;
        }
        if (mType == TYPE_EDIT) {
            if (null != mEditEventModel) {
                mEditEventModel.setEventTitle(eventName);
                mEditEventModel.setTargetTime(mTargetCalendar.getTimeInMillis());
                mEditEventModel.setLunarCalendar(switchCalendar.isChecked());
                mEditEventModel.setTop(switchTop.isChecked());
                mEditEventModel.setRepeatType(mRepeatType);
                EventEntity eventEntity = EntityConverter.convertToEventEntity(mEditEventModel);
                mEventDao.update(eventEntity);
                Intent intent = new Intent();
                intent.putExtra(BundleConstants.KEY_TYPE, mType);
                intent.putExtra(BundleConstants.KEY_MODEL, mEditEventModel);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            EventEntity event = new EventEntity();
            event.setEventTitle(eventName);
            event.setCreateDate(DateUtils.getCurrentTimeMillis());
            event.setTargetDate(mTargetCalendar.getTimeInMillis());
            event.setIsLunarCalendar(switchCalendar.isChecked());
            event.setIsTop(switchTop.isChecked());
            event.setRepeatType(mRepeatType);
            // 如果当前插入事件是置顶，把之前置顶数据改为非置顶
            if (switchTop.isChecked()) {
                updateUnTopFromDB();
            }
            long id = mEventDao.insert(event);
            if (id > 0) {
                Intent intent = new Intent();
                intent.putExtra(BundleConstants.KEY_TYPE, mType);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                ToastUtil.showToast(mContext, R.string.toast_add_event_failure);
            }
        }
    }

    /**
     * 删除事件
     */
    @OnClick(R.id.btn_delete)
    public void onDeleteClick() {
        if (mEventDao != null && mEditEventModel != null) {
            mEventDao.delete(EntityConverter.convertToEventEntity(mEditEventModel));
            Intent intent = new Intent();
            intent.putExtra(BundleConstants.KEY_TYPE, TYPE_DELETE);
            intent.putExtra(BundleConstants.KEY_MODEL, mEditEventModel);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 把之前置顶数据改为非置顶
     *
     * @return
     */
    private boolean updateUnTopFromDB() {
        try {
            List<EventEntity> list = mEventDao.queryBuilder().where(EventDao.Properties.IsTop.eq(switchTop.isChecked())).list();
            if (!ToolUtil.isEmptyCollects(list)) {
                for (EventEntity entity : list) {
                    entity.setIsTop(false);
                    mEventDao.update(entity);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void refreshUi() {
        if (mType == TYPE_EDIT) {
            // 编辑模式
            setTitle("编辑事件");
            if (mEditEventModel != null) {
                String eventTitle = mEditEventModel.getEventTitle();
                edtEventName.setText(eventTitle);
                edtEventName.setSelection(eventTitle.length());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(mEditEventModel.getTargetTime());
                mTargetCalendar = calendar;
                tvTargetDate.setText(DateUtils.getFormatedDate(mContext, calendar, 2, mEditEventModel.isLunarCalendar()));
                switchCalendar.setChecked(mEditEventModel.isLunarCalendar());
                switchTop.setChecked(mEditEventModel.isTop());
                String[] repeatArray = getResources().getStringArray(R.array.repeat_type);
                int repeatType = mEditEventModel.getRepeatType();
                if (repeatType < repeatArray.length) {
                    spinnerRepeat.setSelection(repeatType, true);
                }
                btnDelete.setVisibility(View.VISIBLE);
            }
        } else {
            // 新增模式
            setTitle("新增事件");
            mTargetCalendar = Calendar.getInstance();
            refreshTargetCalendar();
        }
        // 绘制日历切换按钮的背景文字
        this.switchCalendar.post(new Runnable() {
            public void run() {
                Bitmap trackBitmap = BitmapUtils.getBitmap(mContext, R.drawable.bg_switch_calendar_track);
                int width = trackBitmap.getWidth();
                int height = trackBitmap.getHeight();
                Canvas canvas = new Canvas(trackBitmap);
                Paint paint = new Paint();
                paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                paint.setColor(Color.parseColor("#FF565656"));
                paint.setAntiAlias(true);
                canvas.drawText(getString(R.string.solar), (float) (width / 8), (float) ((height * 7) / 10), paint);
                canvas.drawText(getString(R.string.lunar), (float) ((width * 5) / 9), (float) ((height * 7) / 10), paint);
                canvas.save();
                switchCalendar.setTrackDrawable(new BitmapDrawable(getResources(), trackBitmap));
            }
        });

    }

    private void refreshTargetCalendar() {
        tvTargetDate.setText(DateUtils.getFormatedDate(mContext, mTargetCalendar, 2, mIsLunarCalendar));
    }

    /**
     * 公历dialog
     */
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        private WeakReference<AddEventActivity> reference;

        public DatePickerFragment(AddEventActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String[] dateStrArr = reference.get().tvTargetDate.getText().toString().split(" ")[0].split("-");
            int year = Integer.parseInt(dateStrArr[0]);
            int month = Integer.parseInt(dateStrArr[1]) - 1;
            int day = Integer.parseInt(dateStrArr[2]);
            return new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme, this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String dateStr = "";
            String weekdayStr = "";
            try {
                dateStr = i + "-" + (i1 + 1) + "-" + i2;
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                weekdayStr = new SimpleDateFormat("EEEE").format(date);
                reference.get().mTargetCalendar.setTime(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            reference.get().tvTargetDate.setText(dateStr + " " + weekdayStr);
        }
    }

}
