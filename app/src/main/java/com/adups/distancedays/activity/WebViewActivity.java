package com.adups.distancedays.activity;

import android.content.Intent;
import android.os.Bundle;

import com.adups.distancedays.R;
import com.adups.distancedays.base.ToolBarActivity;
import com.adups.distancedays.model.RichModel;
import com.adups.distancedays.utils.BundleConstants;
import com.adups.distancedays.utils.FileUtil;
import com.adups.distancedays.view.LocalTemplateWebView;

import butterknife.BindView;

/**
 * 统一浏览器
 * 待扩展、完善
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class WebViewActivity extends ToolBarActivity {

    @BindView(R.id.wv_rich_content)
    LocalTemplateWebView mRichContent;

    private String title;
    private String sourceUrl = "";
    private String filePath;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        parseBundle();
        initViews();
        loadData();
    }

    private void parseBundle() {
        Intent intent = getIntent();
        if (null != intent) {
            title = intent.getStringExtra(BundleConstants.KEY_TITLE);
            sourceUrl = intent.getStringExtra(BundleConstants.KEY_URL);
            filePath = intent.getStringExtra(BundleConstants.KEY_FILE_PATH);
        }
    }

    private void initViews() {
        setTitle(title);
    }

    private void loadData() {
        String richTemplate = FileUtil.readAssetFileData(mContext, filePath);
        mRichContent.setRichContent(new RichModel(richTemplate));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mRichContent) {
            mRichContent.destroy();
            mRichContent = null;
        }
    }
}
