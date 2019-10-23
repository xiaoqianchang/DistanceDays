package com.adups.distancedays.activity;

import butterknife.BindView;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adups.distancedays.R;
import com.adups.distancedays.base.ToolBarActivity;
import com.adups.distancedays.utils.BundleConstants;
import com.adups.distancedays.utils.PackageUtil;

/**
 * 关于我们
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class AboutUsActivity extends ToolBarActivity {

    @BindView(R.id.tv_version_name)
    TextView tvVersionName;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("关于我们");
        tvVersionName.setText(getString(R.string.string_version_name, PackageUtil.getVersionName()));
    }

    @OnClick({R.id.tv_privacy_policy, R.id.tv_agreement})
    public void onClick(View view) {
        int vId = view.getId();
        Intent intent = null;
        switch (vId) {
            case R.id.tv_privacy_policy:
                intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(BundleConstants.KEY_TITLE, "隐私政策");
                intent.putExtra(BundleConstants.KEY_FILE_PATH, "agreement/privacyPolicy.html");
                startActivity(intent);
                break;
            case R.id.tv_agreement:
                intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(BundleConstants.KEY_TITLE, "用户协议");
                intent.putExtra(BundleConstants.KEY_FILE_PATH, "agreement/userAgreement.html");
                startActivity(intent);
                break;
        }
    }
}
