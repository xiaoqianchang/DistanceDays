package com.adups.distancedays.fragment;

import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.BaseFragment;
import com.adups.distancedays.http.HttpConstant;
import com.adups.distancedays.http.OkHttpWrapper;
import com.adups.distancedays.http.ResponseCallBack;
import com.adups.distancedays.model.ArticleModel;
import com.adups.distancedays.model.BaseModel;
import com.adups.distancedays.model.RichModel;
import com.adups.distancedays.utils.FileUtil;
import com.adups.distancedays.utils.PackageUtil;
import com.adups.distancedays.utils.ToastUtil;
import com.adups.distancedays.view.LocalTemplateWebView;

import java.util.Calendar;

import butterknife.BindView;
import retrofit2.Call;

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
        Call<BaseModel<ArticleModel>> call = OkHttpWrapper.getInstance().getNetApiInstance().getDailyArticle();
        call.enqueue(new ResponseCallBack<BaseModel<ArticleModel>>() {
            @Override
            public void onSuccess(BaseModel<ArticleModel> articleModelBaseModel) {
                ArticleModel articleModel = articleModelBaseModel.getResult();
                if (articleModel != null) {
                    ArticleModel.ArticleInfo articleInfo = articleModel.getArticleInfo();
                    if (articleInfo != null) {
                        String title = articleInfo.getTitle();
                        String author = articleInfo.getArticleAuthor();
                        String text = articleInfo.getArticleText();
                        RichModel richModel = new RichModel(title, author, text);
                        mRichContent.setRichContent(richModel, true);
                    }
                }
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtil.showToast(getContext(), msg);
            }
        });
    }
}
