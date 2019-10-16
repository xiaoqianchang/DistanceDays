package com.adups.distancedays.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

//            if (null != body) {
//                if (body instanceof BaseModel) {
//                    BaseModel baseModel = (BaseModel) body;
//                }
//                try {
//                    Field code = c.getField("code");
//                    Field msg = c.getField("msg");
//                    Field sid = c.getField("sid");
//                    this.code = (String) code.get(body);
//                    this.msg = (String) msg.get(body);
//
//                } catch (NoSuchFieldException e) {
//                    // 若遇到405，说明是文件访问类型，可能出现无code和sid参数返回的情况，此时若满足json格式正常，则继续走code＝1000的流程
//                    if (HttpStatus.SC_METHOD_NOT_ALLOWED == statusCode && new ZRJsonValidator().validate(jsonStr)) {
//                        code = "1000";
//                    }
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                switch (code) {
//                    case SUCCESS:
//                        onSuccess(body);
//                        break;
//                    case ERROR:
//                        onError(code, msg);
//                        break;
//                    case SESSION_TIMEOUT:
//                        if (TextUtils.isEmpty(msg))
//                            msg = ZRErrors.getLocalErrorMsg(ZRApplication.applicationContext, code);
//                        // 针对session刷新失败，在调某接口时1002处理
//                        if ("用户未登录".equals(msg)) {
//                            ZRDataEngine.getInstance().setPersonalInfo(null);
//                            break;
//                        }
//                        onError(code, msg);
//                        break;
//                    case JSON_EXCEPTION:
//                        onError(code, "JSON不合法，错误JSON为" + msg);
//                        break;
//                    default:
//                        onError(code, msg);
//                        break;
//                }
//            } else {
//                onFailure(new ServerException(ZRErrors.getLocalErrorMsg(ZRApplication.applicationContext, ZRErrors.ERROR_NETWORK))); // response body is null
//            }
        } catch (Exception e) {
            e.printStackTrace();
//            onError("", e.getMessage());
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
//        if (!ZRNetUtils.isNetworkConnected(ZRApplication.applicationContext)) {
//            onError("", "网络不可用!");
//            return;
//        }
//        onError("", ZRErrors.getLocalErrorMsg(ZRApplication.applicationContext, ZRErrors.ERROR_NETWORK));
//        ZRLog.d(TAG, "onFailure方法：" + t.getMessage());
    }

    public void onFailure(Throwable e) {
//        if (e instanceof ServerException) {
//            onError("", e.getMessage());
//        } else if (e instanceof Exception) {
//            // 更多异常处理
//        } else {
//            onError("", "请求失败，请稍后重试...");
//        }
    }

//    private void doLogin() {
//        Activity activity = ZRAppActivityManager.getAppManager().currentActivity();
////        Intent intent = new Intent(activity, ZRActivityLogin.class);
////        activity.startActivity(intent);
//        // TODO: 2016/12/15 后台直接登录
//        String loginName = ZRSharePreferenceKeeper.getStringValue(activity, ZRConstant.KEY_PHONE);
//        String password = ZRSharePreferenceKeeper.getStringValue(activity, ZRConstant.KEY_PASSWORD);
//        if (TextUtils.isEmpty(password))
//            return;
//        ZRUserInfo.getInstance().doLogin(new ZRUserInfo.UserInfoCallBack() {
//            @Override
//            public void onUserInfo(Object object) {
//
//            }
//
//            @Override
//            public void onUserInfoError(String message) {
//                Intent intent = new Intent(activity, ZRActivityLogin.class);
//                activity.startActivity(intent);
//            }
//        }, loginName, password);
//    }

    public abstract void onSuccess(T t);

    public abstract void onError(int code, String msg);
}
