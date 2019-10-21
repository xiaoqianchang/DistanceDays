package com.adups.distancedays.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.adups.distancedays.R;
import java.lang.reflect.Field;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;


/**
 * DialogFragment 基类
 *
 * Created by Chang.Xiao on 2017/1/17.
 *
 * @version 1.0
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private static final String TAG = "base_bottom_dialog";

    private static final float DEFAULT_DIM = 0.5f;
    public static final int DEFAULT_GRAVITY = Gravity.BOTTOM;
    public static final int GRAVITY_CENTER = Gravity.CENTER;
    public enum LoadCompleteType {
        OK, NETWOEKERROR, NOCONTENT, LOADING
    }

    protected Context mContext;
    protected View mMainView;

    /**
     * 此处覆写是为了防止提交时检验报错 具体见
     * @see android.support.v4.app.FragmentManagerImpl#enqueueAction(Runnable, boolean) 中的checkStateLoss
     * @param manager
     * @param tag
     */
    private boolean isAdd = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
        getExtraArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());
        mMainView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, mMainView);
        return mMainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        bindView(view);
    }

    protected abstract void getExtraArguments();

    @LayoutRes
    public abstract int getContentViewId();

    protected abstract void initPresenter();

    public abstract void bindView(View view);

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.dimAmount = getDimAmount();
        if(getWidth()>0){
            params.width = getWidth();
        }else {
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
        }
        if (getHeight() > 0) {
            params.height = getHeight();
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        params.gravity = getGravity();

        window.setAttributes(params);

    }

    public int getHeight() {
        return -1;
    }

    public int getWidth(){
        return -1;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }
    public int getGravity(){
        return DEFAULT_GRAVITY;
    }

    public boolean getCancelOutside() {
        return true;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public void setIsAdd(boolean isAdd){
        this.isAdd = isAdd;
    }

    public boolean isAddFix(){
        return isAdd||isAdded();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if(!isAddFix()) {
            setIsAdd(true);
            try {
                Class classInstanse = Class.forName("androidx.fragment.app.DialogFragment");
                Field fieldDismissed = classInstanse.getDeclaredField("mDismissed");
                fieldDismissed.setAccessible(true);
                fieldDismissed.setBoolean(this, false);

                Field fieldShownByMe = classInstanse.getDeclaredField("mShownByMe");
                fieldShownByMe.setAccessible(true);
                fieldShownByMe.setBoolean(this, true);

                FragmentTransaction ft = manager.beginTransaction();
                ft.add(this, tag);
                ft.commitAllowingStateLoss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dismiss() {
        if(isAddFix()) {
            if(getFragmentManager() != null) {
                dismissAllowingStateLoss();
            }
            setIsAdd(false);
        }
    }

    public void dismissAllowingStateLoss() {
        if(isAddFix()) {
            if (getFragmentManager() != null) {
                super.dismissAllowingStateLoss();
            }

            setIsAdd(false);
        }
    }

    /**
     * 如果希望执行父类的dismiss方法可以执行这个函数
     */
    public void dismissBySuper() {
        super.dismiss();
        setIsAdd(false);
    }

    /**
     * 执行父类的show方法
     * @param manager
     * @param tag
     */
    public void showBySuper(FragmentManager manager, String tag) {
        if(!isAddFix()) {
            setIsAdd(true);
            super.show(manager ,tag);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setIsAdd(false);
    }

    public boolean canUpdateUi() {
        return !(!isAdded() || isRemoving() || isDetached());
    }

    protected View findViewById(int viewId) {
        if(getView() != null) {
            return getView().findViewById(viewId);
        }
        return null;
    }


    public final String getStringSafe(@StringRes int resId) {
        if (getActivity() == null)
            return "";
        return getActivity().getString(resId);
    }

    public final String getStringSafe(@StringRes int resId, Object... formatArgs) {
        if (getActivity() == null)
            return "";
        return getActivity().getString(resId, formatArgs);
    }

    public Resources getResourcesSafe() throws NullPointerException {
        return getActivity().getResources();
    }

    @Override
    public void startActivity(Intent intent) {
        if (getActivity() == null)
            return;
        getActivity().startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) throws IllegalStateException {
        if (getActivity() == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActivity().startActivity(intent, options);
        } else {
            super.startActivity(intent, options);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (getActivity() == null)
            return;
        getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) throws IllegalStateException {
        if (getActivity() == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActivity().startActivityForResult(intent, requestCode, options);
        } else {
            super.startActivityForResult(intent, requestCode, options);
        }
    }
}
