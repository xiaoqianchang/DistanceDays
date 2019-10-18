package com.adups.distancedays.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.adups.distancedays.R;
import com.adups.distancedays.utils.LunarCalendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 阴历dailog
 * <p>
 * Created by Chang.Xiao on 2019/10/18.
 *
 * @version 1.0
 */
public class LunarPickerDialogFragment extends DialogFragment {

    @BindView(R.id.lunar_year)
    NumberPicker numberPickerYear;
    @BindView(R.id.lunar_month)
    NumberPicker numberPickerMonth;
    @BindView(R.id.lunar_day)
    NumberPicker numberPickerDay;
    @BindView(R.id.confirm_button)
    Button confitmButton;

    public static List<String> ai = Arrays.asList(new String[]{"闰正月", "闰二月", "闰三月", "闰四月", "闰五月", "闰六月", "闰七月", "闰八月", "闰九月", "闰十月", "闰冬月", "闰腊月"});
    Calendar ae;
    LunarCalendar af;
    int ag;
    int ah;
    private OnConfirmClickListener aj;
    private List<String> ak;
    private List<String> al;


    public interface OnConfirmClickListener {
        void onConfirmClick(Calendar calendar);
    }

    public static LunarPickerDialogFragment newInstance(Calendar currentCalendar) {
        LunarPickerDialogFragment fragment = new LunarPickerDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("CurrentCalendar", currentCalendar);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.ae = (Calendar) getArguments().getSerializable("CurrentCalendar");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(1);
        View rootView = inflater.inflate(R.layout.fragment_lunar_picker_dialog, container, false);
        ButterKnife.bind((Object) this, rootView);
        y();
        return rootView;
    }

    private void y() {
        z();
        this.numberPickerYear.setMinValue(1901);
        this.numberPickerYear.setMaxValue(2099);
        this.numberPickerYear.setValue(this.af.getYear());
        this.numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                LunarPickerDialogFragment.this.d(newVal);
                LunarPickerDialogFragment.this.c(newVal);
                LunarPickerDialogFragment.this.b(newVal, LunarPickerDialogFragment.this.af.getMonth());
            }
        });
        c(this.af.getYear());
        this.numberPickerMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                LunarPickerDialogFragment.this.e(newVal + 1);
                LunarPickerDialogFragment.this.b(LunarPickerDialogFragment.this.af.getYear(), newVal + 1);
            }
        });
        this.numberPickerMonth.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        this.numberPickerDay.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        b(this.af.getYear(), this.af.getMonth());
        this.numberPickerDay.setValue(this.af.getDay() - 1);
        this.numberPickerDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                LunarPickerDialogFragment.this.f(newVal + 1);
            }
        });
    }

    /* access modifiers changed from: private */
    public void c(int year) {
        this.ah = LunarCalendar.leapMonth(year);
        this.ak = new ArrayList();
        this.ak.addAll(LunarCalendar.a);
        if (this.ah != 0) {
            this.ak.add(this.ah, ai.get(this.ah - 1));
        }
        this.numberPickerMonth.setDisplayedValues(null);
        this.numberPickerMonth.setMinValue(0);
        this.numberPickerMonth.setMaxValue(this.ak.size() - 1);
        this.numberPickerMonth.setDisplayedValues((String[]) this.ak.toArray(new String[this.ak.size()]));
        if (this.ah > this.af.getMonth() || this.ah == 0) {
            this.numberPickerMonth.setValue(this.af.getMonth() - 1);
        } else if (this.af.isLeapMonth() || this.ah != this.af.getMonth()) {
            this.numberPickerMonth.setValue(this.af.getMonth());
        } else {
            this.numberPickerMonth.setValue(this.af.getMonth() - 1);
        }
    }

    /* access modifiers changed from: private */
    public void b(int year, int month) {
        this.ag = LunarCalendar.daysInMonth(year, month);
        this.al = new ArrayList();
        this.al.addAll(LunarCalendar.c);
        if (this.ag == 30) {
            this.al.add("三十");
        }
        this.numberPickerDay.setDisplayedValues(null);
        this.numberPickerDay.setMinValue(0);
        this.numberPickerDay.setMaxValue(this.ag - 1);
        this.numberPickerDay.setDisplayedValues((String[]) this.al.toArray(new String[this.al.size()]));
        this.numberPickerDay.invalidate();
    }

    private void z() {
        this.af = new LunarCalendar(this.ae);
    }

    /* access modifiers changed from: private */
    public void d(int newVal) {
        this.af.setYear(newVal);
        this.ah = LunarCalendar.leapMonth(newVal);
    }

    /* access modifiers changed from: private */
    public void e(int newVal) {
        boolean z = false;
        if (this.ah == 0 || newVal <= this.ah) {
            this.af.setLeapMonth(false);
        } else {
            newVal--;
            LunarCalendar lunarCalendar = this.af;
            if (newVal == this.ah) {
                z = true;
            }
            lunarCalendar.setLeapMonth(z);
        }
        this.af.setMonth(newVal);
    }

    /* access modifiers changed from: private */
    public void f(int newVal) {
        this.af.setDay(newVal);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onDetach() {
        super.onDetach();
        this.aj = null;
    }

    /* access modifiers changed from: 0000 */
    @OnClick(R.id.confirm_button)
    public void onConfirmButtonClick() {
        if (this.aj != null) {
            this.aj.onConfirmClick(LunarCalendar.lunarToSolarCalendar(this.af.getYear(), this.af.getMonth(), this.af.getDay(), this.af.isLeapMonth()));
        }
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.aj = listener;
    }


}
