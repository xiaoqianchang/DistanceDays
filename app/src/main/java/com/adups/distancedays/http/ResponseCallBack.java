package com.adups.distancedays.http;

import com.adups.distancedays.R;
import com.adups.distancedays.model.BaseModel;
import com.adups.distancedays.utils.CommonUtil;
import com.adups.distancedays.utils.NetUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络响应封装
 * <p>
 * Created by Chang.Xiao on 2016/8/16.
 *
 * @version 1.0
 */
public abstract class ResponseCallBack<T> implements Callback<T> {

    private static final String TAG = ResponseCallBack.class.getSimpleName();

    private int code;
    private int errorCode;
    private String msg = "";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            String jsonStr = "";
            T body = response.body();
            int statusCode = response.code(); // 服务解析返回后的code
            // 500
            if (500 == statusCode) {
                onError(500, "Internal Server Error");
                return;
            }

            // 405 right return
            if (405 == statusCode) {
                jsonStr = response.errorBody().string().trim();
                body = new Gson().fromJson(jsonStr, new TypeToken<T>() {
                }.getType());
            }

            if (null != body) {
                if (body instanceof BaseModel) {
                    BaseModel baseModel = (BaseModel) body;
                    code = baseModel.resultCode;
                    errorCode = baseModel.errorCode;
                    msg = baseModel.reason;
                }
                if (code == HttpConstant.SUCCESS || errorCode == 0) {
                    onSuccess(body);
                } else {
                    onError(code, msg);
                }
//                switch (code) {
//                    case HttpConstant.SUCCESS:
//                        onSuccess(body);
//                        break;
//                    default:
//                        onError(code, msg);
//                        break;
//                }
            } else {
                onError(HttpConstant.CODE_UNKNOWN, CommonUtil.getApplication().getString(R.string.string_error_network)); // response body is null
            }
        } catch (Exception e) {
            Logger.t(TAG).e(e.getMessage());
            onError(HttpConstant.CODE_UNKNOWN, e.getMessage());
        }
    }

    /**
     * 1.服务器连上失败
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!NetUtils.isNetConnected(CommonUtil.getApplication())) {
            onError(HttpConstant.CODE_UNKNOWN, "网络不可用!");
            return;
        }
        onError(HttpConstant.CODE_UNKNOWN, CommonUtil.getApplication().getString(R.string.string_error_network));
        Logger.d(TAG, "onFailure方法：" + t.getMessage());
    }

    public abstract void onSuccess(T t);

    public abstract void onError(int code, String msg);
}
