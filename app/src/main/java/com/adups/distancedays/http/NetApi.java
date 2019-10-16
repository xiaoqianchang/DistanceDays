package com.adups.distancedays.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public interface NetApi {

    /**
     * 反馈api
     * doc：https://note.youdao.com/ynoteshare1/index.html?id=2b5c163da9d5eddf9a14a64b35d9ae31&type=note
     *
     * 请求方式：post
     * 请求头：application/x-www-form-urlencoded
     *
     * @param url 接口地址 https://wx1.adgomob.com/c_terminal/
     * @param androidid
     * @param model 机型
     * @param apn 网络场景
     * @param imei 唯一值
     * @param packagename 包名
     * @param versionname 版本号
     * @param contactWay 联系方式
     * @param content 反馈内容
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> processFeedback(
            @Url String url
            , @Field("androidid") String androidid
            , @Field("model") String model
            , @Field("apn") String apn
            , @Field("imei") String imei
            , @Field("packagename") String packagename
            , @Field("versionname") String versionname
            , @Field("contactWay") String contactWay
            , @Field("content") String content);

    /**
     * 每日文章api
     * doc：https://note.youdao.com/ynoteshare1/index.html?id=2b5c163da9d5eddf9a14a64b35d9ae31&type=note
     *
     * 请求方式：get/post
     *
     * 请求示例：http://api.juheapi.com/japi/toh?key=您申请的KEY&v=1.0&month=11&day=1
     *
     * @param url 接口地址 http://api.juheapi.com/japi/toh/
     * @param key 在个人中心->我的数据,接口名称上方查看
     * @param version 版本，当前：1.0
     * @param month 月份，如：10
     * @param day 日，如：1
     * @return
     */
    @GET
    Call<ResponseBody> getDailyArticle(
            @Url String url
            , @Field("key") String key
            , @Field("v") String version
            , @Field("month") String month
            , @Field("day") String day);
}
