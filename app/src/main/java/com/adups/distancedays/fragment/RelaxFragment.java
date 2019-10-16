package com.adups.distancedays.fragment;

import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.http.HttpConstant;
import com.adups.distancedays.http.OkHttpWrapper;
import com.adups.distancedays.utils.PackageUtil;
import com.adups.distancedays.view.LocalTemplateWebView;

import java.util.Calendar;

import butterknife.BindView;

/**
 * 轻松一刻
 * 
 * Created by Chang.Xiao on 2019/10/11.
 *
 * @version 1.0
 */
public class RelaxFragment extends BaseFragment {

    @BindView(R.id.wv_rich_content)
    LocalTemplateWebView mRichContent;

    public static RelaxFragment newInstance() {
        Bundle bundle = new Bundle();
        RelaxFragment fragment = new RelaxFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_relax;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected void loadData() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        OkHttpWrapper.getInstance().getNetApiInstance().getDailyArticle(HttpConstant.URL_DAILY_ARTICLE, HttpConstant.DAILY_ARTICLE_KEY, PackageUtil.getVersionName(), month, day);
    }
}
