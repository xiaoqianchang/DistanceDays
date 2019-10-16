package com.adups.distancedays.http;

import android.text.TextUtils;

import com.adups.distancedays.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class OkHttpWrapper {

    public static final long DEFAULT_TIMEOUT = 10;
    public static final String SERVER_URL_INIT_1 = "https://w.duocaijr.com";

    private static OkHttpWrapper mInstance;
    private static Retrofit.Builder builder;
    private static NetApi mNetApi;
    protected static final Object monitor = new Object();
    private static OkHttpClient.Builder okHttpBuilder;
    private static OkHttpClient client;
    private static Gson gson;

    private OkHttpWrapper() {
        // 用于控制非法反射单例类的构造函数
        if (OkHttpWrapper.OkHttpWrapperHolder.sInstance != null) {
            try {
                throw new IllegalAccessException("非法反射构造函数");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static OkHttpWrapper getInstance() {
        return OkHttpWrapper.OkHttpWrapperHolder.sInstance;
    }

    static Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request authorised = originalRequest.newBuilder()
                    .addHeader("Connection", "close")
                    .build();
            return chain.proceed(authorised);
        }
    };

    static {
        // OkHttp3
        okHttpBuilder = new OkHttpClient().newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        okHttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.addNetworkInterceptor(mTokenInterceptor);
        client = okHttpBuilder.build();

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        builder = new Retrofit.Builder();
    }

    /**
     * Get NetApi instance
     *
     * @return
     */
    public NetApi getNetApiInstance() {
        return getNetApiInstance(SERVER_URL_INIT_1);
    }

    /**
     * Get NetApi instance
     *
     * @param serverUrl
     * @return
     */
    public NetApi getNetApiInstance(String serverUrl) {
        boolean hasBackslash = hasBackslash(serverUrl); // 默认最后没有反斜杠
        synchronized (monitor) {
            Retrofit retrofit = builder
                    .client(client)
                    .baseUrl(serverUrl + (hasBackslash ? "" : "/"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mNetApi = retrofit.create(NetApi.class);
            return mNetApi;
        }
    }

    /**
     * 给定url是否以反斜杠结尾
     *
     * @param serverUrl
     * @return
     */
    private boolean hasBackslash(String serverUrl) {
        if (TextUtils.isEmpty(serverUrl)) {
            return false;
        }
        if ("/".equals(serverUrl.toCharArray()[serverUrl.length() - 1])) {
            return true;
        }
        return false;
    }

    private static final class OkHttpWrapperHolder {
        private static final OkHttpWrapper sInstance = new OkHttpWrapper();
    }
}
