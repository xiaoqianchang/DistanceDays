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
    @BindView(R.id.btn_commit)
    Button btnCommit;

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
        Call<ResponseBody> call = OkHttpWrapper.getInstance().getNetApiInstance().processFeedback(HttpConstant.URL_FEEDBACK, DeviceUtil.getAndroidId(mContext), DeviceUtil.getModel(), String.valueOf(NetUtils.getNetworkState(mContext)), DeviceUtil.getIMEI(mContext), PackageUtil.getPackageName(mContext), PackageUtil.getVersionName(), contactWay, contactContent);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                try {
                    String string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        String string = response.errorBody().string();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

//        call.enqueue(new Callback<BaseModel>() {
//            @Override
//            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
//                BaseModel model = response.body();
//                ToastUtil.showToast(mContext, model == null ? "反馈成功" : model.getReason());
//                finish();
//            }
//
//            @Override
//            public void onFailure(Call<BaseModel> call, Throwable t) {
//                ToastUtil.showToast(mContext, "反馈失败");
//            }
//        });
    }
}
