package com.adups.distancedays.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adups.distancedays.R;
import com.adups.distancedays.base.ToolBarActivity;
import com.adups.distancedays.http.HttpConstant;
import com.adups.distancedays.http.OkHttpWrapper;
import com.adups.distancedays.http.ResponseCallBack;
import com.adups.distancedays.model.BaseModel;
import com.adups.distancedays.utils.DeviceUtil;
import com.adups.distancedays.utils.NetUtils;
import com.adups.distancedays.utils.PackageUtil;
import com.adups.distancedays.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 反馈
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class FeedbackActivity extends ToolBarActivity {

    @BindView(R.id.edt_feedback_content)
    EditText edtFeedbackContent;
    @BindView(R.id.tv_feedback_word_count)
    TextView tvFeedbackWordCount;
    @BindView(R.id.edt_feedback_contact)
    EditText edtFeedbackContact;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("意见反馈");
    }

    @OnTextChanged(R.id.edt_feedback_content)
    public void onFeedbackContent(CharSequence s) {
        String str = s.toString().trim();
        int count = str.length();
        tvFeedbackWordCount.setText(getString(R.string.string_feedback_word_count, String.valueOf(count)));
    }

    @OnClick(R.id.btn_commit)
    public void onCommitClick() {
        // 调网络请求
        String contactWay = edtFeedbackContact.getText().toString();
        String contactContent = edtFeedbackContent.getText().toString();
        if (TextUtils.isEmpty(contactContent)) {
            ToastUtil.showToast(mContext, "输入意见不能为空");
            return;
        }
        if (TextUtils.isEmpty(contactWay)) {
            ToastUtil.showToast(mContext, "联系方式不能为空");
            return;
        }
        Call<BaseModel> call = OkHttpWrapper.getInstance().getNetApiInstance().processFeedback(
                DeviceUtil.getAndroidId(mContext),
                DeviceUtil.getModel(),
                String.valueOf(NetUtils.getNetworkState(mContext)),
                DeviceUtil.getIMEI(mContext),
                PackageUtil.getPackageName(mContext),
                PackageUtil.getVersionName(),
                contactWay,
                contactContent);
        call.enqueue(new ResponseCallBack<BaseModel>() {
            @Override
            public void onSuccess(BaseModel baseModel) {
                ToastUtil.showToast(mContext, getString(R.string.toast_feedback_fail));
                finish();
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtil.showToast(mContext, msg);
            }
        });
    }
}
