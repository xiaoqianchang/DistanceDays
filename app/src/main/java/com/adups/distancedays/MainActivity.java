package com.adups.distancedays;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.adups.distancedays.base.ToolBarActivity;
import com.adups.distancedays.fragment.DistanceDaysFragment;
import com.adups.distancedays.fragment.HistoryFragment;
import com.adups.distancedays.fragment.RelaxFragment;
import com.adups.distancedays.fragment.SettingFragment;
import com.adups.distancedays.manager.TabFragmentManager;
import com.adups.distancedays.utils.TypeConversionUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * 首页
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class MainActivity extends ToolBarActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "MainActivity";

    /**
     * 当前主页显示的fragment的tag
     */
    public static final String KEY_CURRENT_SHOW_FRAGMENT_TAG = "current_show_fragment_tag";

    public static final int TAB_DISTANCE_DAYS = R.id.tab_distance_days; // 倒数日
    public static final int TAB_HISTORY = R.id.tab_history; // 历史上今天
    public static final int TAB_RELAX = R.id.tab_relax; // 轻松一刻
    public static final int TAB_SETTING = R.id.tab_setting; // 设置

    @BindView(R.id.rg_tabs)
    RadioGroup mRadioGroup;

    private FragmentManager mFragmentManager;
    public Fragment mCurrentFragment; // 当前显示的fragment
    private int mCurrentTabResId = -1; // 当前选中tab的id
    private TabFragmentManager mTabFragmentManager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        recoverStatus(savedInstanceState);
        mTabFragmentManager = new TabFragmentManager(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.setVisibility(View.VISIBLE);
        setMenuTypes(MENU_TYPE_SWITCH_LAYOUT, MENU_TYPE_ADD_EVENT);
    }

    /**
     * 恢复之前的fragment
     * @param savedInstanceState
     */
    private void recoverStatus(Bundle savedInstanceState) {
        if (savedInstanceState != null && mFragmentManager != null) {
            String currentShowFragmentTag = savedInstanceState
                    .getString(KEY_CURRENT_SHOW_FRAGMENT_TAG, String.valueOf(TAB_DISTANCE_DAYS));
            Fragment fragment = mFragmentManager.findFragmentByTag(currentShowFragmentTag);
            if (fragment != null) {
                changeFragment(TypeConversionUtil.parseInt(currentShowFragmentTag, TAB_DISTANCE_DAYS));
            } else {
                changeFragment(TAB_DISTANCE_DAYS);
            }
        } else {
            changeFragment(TAB_DISTANCE_DAYS);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        changeFragment(checkedId);
    }

    private void changeFragment(int checkedId) {
        Fragment changeToFragment = null;
        Fragment fragment = mFragmentManager.findFragmentByTag(String.valueOf(checkedId));
        if (fragment == null) {
            switch (checkedId) {
                case TAB_DISTANCE_DAYS:
                    fragment = DistanceDaysFragment.newInstance();
                    break;
                case TAB_HISTORY:
                    fragment = HistoryFragment.newInstance();
                    break;
                case TAB_RELAX:
                    fragment = RelaxFragment.newInstance();
                    break;
                case TAB_SETTING:
                    fragment = SettingFragment.newInstance();
                    break;
                default:
                    fragment = DistanceDaysFragment.newInstance();
                    break;
            }
        }
        changeToFragment = fragment;
        if (changeToFragment != null) {
            if (mCurrentFragment != changeToFragment) {
                try {
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    if (mCurrentFragment != null) {
                        transaction.hide(mCurrentFragment);
                    }
                    if (changeToFragment.isAdded()) {
                        transaction.show(changeToFragment);
                    } else {
                        transaction.add(R.id.fragment_container, changeToFragment, String.valueOf(checkedId));
                    }
                    transaction.commitAllowingStateLoss();
                    mCurrentFragment = changeToFragment;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mCurrentTabResId =checkedId;
            changeTitleBar(mCurrentTabResId);
        }
    }

    /**
     * 改变标题栏
     * @param mCurrentTabResId
     */
    private void changeTitleBar(int mCurrentTabResId) {
        String centerStr;
        switch (mCurrentTabResId) {
            case TAB_DISTANCE_DAYS:
                centerStr = getString(R.string.main_tab_distance_days);
                break;
            case TAB_HISTORY:
                centerStr = getString(R.string.main_tab_history);
                break;
            case TAB_RELAX:
                centerStr = getString(R.string.main_tab_relax);
                break;
            case TAB_SETTING:
                centerStr = getString(R.string.main_tab_setting);
                break;
            default:
                centerStr = getString(R.string.app_name);
                break;
        }
        setTitle(centerStr);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCurrentFragment != null) {
            mCurrentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null && mCurrentFragment != null) {
            outState.putString(KEY_CURRENT_SHOW_FRAGMENT_TAG, mCurrentFragment.getTag());
        }
    }
}
