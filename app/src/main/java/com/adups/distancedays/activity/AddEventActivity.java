package com.adups.distancedays.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.adups.distancedays.db.dao.EventDao;
import com.adups.distancedays.db.entity.EventEntity;
import com.adups.distancedays.utils.DateUtils;
import com.adups.distancedays.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    @BindView(R.id.btn_save)
    Button btnSave;

    private boolean mIsLunarCalendar; // 是否为阴历
    private Calendar mTargetCalendar;
    private EventDao mEventDao;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_event;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("新增事件");
        mEventDao = DBHelper.getInstance(mContext).getDaoSession().getEventDao();
        parseBundle();
        initViews();
        refreshUi();
    }

    private void parseBundle() {
    }

    private void initViews() {
//        spinnerRepeat.setDropDownWidth(400); //下拉宽度
//        spinnerRepeat.setDropDownHorizontalOffset(100); //下拉的横向偏移
//        spinnerRepeat.setDropDownVerticalOffset(100); //下拉的纵向偏移
        //mSpinnerSimple.setBackgroundColor(AppUtil.getColor(instance,R.color.wx_bg_gray)); //下拉的背景色
        //spinner mode ： dropdown or dialog , just edit in layout xml
        //mSpinnerSimple.setPrompt("Spinner Title"); //弹出框标题，在dialog下有效
        String[] repeatTypes = getResources().getStringArray(R.array.repeat_type);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, repeatTypes);
        spinnerRepeat.setAdapter(spinnerAdapter);
        spinnerRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerRepeat.setPrompt(repeatTypes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.tv_target_date, R.id.switch_top, R.id.btn_save})
    public void onClick(View view) {
        int vId = view.getId();
        switch (vId) {
            case R.id.tv_target_date:
                DialogFragment datePickerFragment = new DatePickerFragment(this);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");

//                Calendar calendar = Calendar.getInstance();
//                new DatePickerDialog(mContext,new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        calendar.set(year,monthOfYear,dayOfMonth);
//                    }
//                }, calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH)
//                ).show();
                break;
            case R.id.switch_top:
                break;
            case R.id.btn_save:
                addEvent();
                break;
        }
    }

    @OnCheckedChanged(R.id.switch_calendar)
    public void onSwitchCalendarClick() {
        mIsLunarCalendar = switchCalendar.isChecked();
        refreshUi();
    }

    /**
     * 添加事件
     */
    private void addEvent() {
        String eventName = edtEventName.getText().toString().trim();
        if (TextUtils.isEmpty(eventName)) {
            ToastUtil.showToast("请输入事件标题");
            return;
        }
        EventEntity event = new EventEntity();
        event.setEventContent(eventName);
        event.setCreateDate(DateUtils.getCurrentTimeMillis());
        event.setTargetDate(mTargetCalendar.getTimeInMillis());
        mEventDao.insert(event);
        finish();
    }

    private void refreshUi() {
        mTargetCalendar = Calendar.getInstance();
        tvTargetDate.setText(DateUtils.getFormatedDate(mContext, mTargetCalendar, 2, mIsLunarCalendar));
    }

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
            return new DatePickerDialog(getActivity(), this, year, month, day);
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
