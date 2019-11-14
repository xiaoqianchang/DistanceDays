package com.adups.distancedays.base;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.adups.distancedays.R;
import com.adups.distancedays.utils.SystemUtils;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

/**
 * 带Toolbar的基类Activity
 * <p>
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public abstract class ToolBarActivity extends BaseActivity {

    public static final int MENU_TYPE_BASE = 1;
    public static final int MENU_TYPE_SWITCH_LAYOUT = 2;
    public static final int MENU_TYPE_ADD_EVENT = 3;
    public static final int MENU_TYPE_EDIT_EVENT = 4;

    @BindView(R.id.app_bar_layout)
    protected AppBarLayout mAppBarLayout;
    @BindView(R.id.status_bar)
    protected View mStatusBar;
    @BindView(R.id.toolBar)
    protected Toolbar mToolbar;

    protected boolean isToolBarHiding = false;
    protected List<Integer> mMenuTypes = Arrays.asList(MENU_TYPE_BASE);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        initToolBar();
    }

    protected void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }

    protected void initToolBar() {
        if (null == mAppBarLayout || null == mToolbar) {
            throw new IllegalStateException("The subclass of ToolbarActivity must contain a toolbar.");
        }
        mToolbar.setOnClickListener(v -> onToolbarClick());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(canBack()); // 设置返回箭头
        }
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBarLayout.setElevation(10.6f);
        }
    }

    /**
     * onToolbarClick
     */
    protected void onToolbarClick() {
        // empty
    }

    /**
     * 设置 NavigationButton 是否可见
     *
     * @return
     */
    protected boolean canBack() {
        return false;
    }

    public void setTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }

    public void setTitle(@StringRes int resId) {
        mToolbar.setTitle(resId);
    }

    /**
     * 设置当前界面 action menu type
     *
     * @param menuType
     */
    public void setMenuTypes(Integer... menuType) {
        this.mMenuTypes = Arrays.asList(menuType);
        updateMenuActionBar();
    }

    public void clearMenuActionBar() {
        this.mMenuTypes = Arrays.asList(MENU_TYPE_BASE);
        updateMenuActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void updateMenuActionBar() {
        invalidateOptionsMenu();
    }

    public Runnable getMenuSwitchLayoutAction() {
        return null;
    }
    public Runnable getMenuAddEventAction() {
        return null;
    }
    public Runnable getMenuEditEventAction() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_switch_layout).setVisible(false);
        menu.findItem(R.id.action_add_event).setVisible(false);
        menu.findItem(R.id.action_edit_event).setVisible(false);
        Iterator<Integer> iterator = mMenuTypes.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            switch (next) {
                case MENU_TYPE_SWITCH_LAYOUT:
                    menu.findItem(R.id.action_switch_layout).setVisible(true);
                    break;
                case MENU_TYPE_ADD_EVENT:
                    menu.findItem(R.id.action_add_event).setVisible(true);
                    break;
                case MENU_TYPE_EDIT_EVENT:
                    menu.findItem(R.id.action_edit_event).setVisible(true);
                    break;
                default:
                    break;
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                //在Action Bar的最左边，就是Home icon和标题的区域
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_switch_layout:
                if (getMenuSwitchLayoutAction() != null) {
                    getMenuSwitchLayoutAction().run();
                }
                return true;
            case R.id.action_add_event:
                if (getMenuAddEventAction() != null) {
                    getMenuAddEventAction().run();
                }
                return true;
            case R.id.action_edit_event:
                if (getMenuEditEventAction() != null) {
                    getMenuEditEventAction().run();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setAppBarAlpha(float alpha) {
        mAppBarLayout.setAlpha(alpha);
    }

    /**
     * Control ToolBar show or hiden
     */
    protected void hideOrShowToolBar() {
        mAppBarLayout.animate().translationY(isToolBarHiding ? 0 : -mAppBarLayout.getHeight()).setInterpolator(new DecelerateInterpolator(2)).start();
        isToolBarHiding = !isToolBarHiding;
    }

    @Override
    protected void onDestroy() {
        if (mToolbar != null) {
            setSupportActionBar(null);
            mToolbar.setOnClickListener(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mToolbar.releasePointerCapture();
            } else {
                mToolbar.setFocusable(false);
            }
            mToolbar = null;
        }
        super.onDestroy();
    }
}
