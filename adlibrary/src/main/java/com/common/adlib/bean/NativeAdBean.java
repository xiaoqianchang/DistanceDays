package com.common.adlib.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.common.adlib.base.Report;
import com.common.adlib.base.ReportEvent;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.common.adlib.bean.NativeAdBean.java
 * @author: gz
 * @date: 2019-11-12 16:49
 */
public class NativeAdBean implements Serializable, Parcelable {

    /**
     * 提供广告sdk方
     */
    private String provider;
    /**
     * 图片对象 包含  icon  大图banner等
     */
    public NativeAdPic nativeAdPic;
    /**
     * apk size大小
     */
    public String at_apk_size;
    /**
     * attype 广告类型
     * 广告类型 写死  api sdk link apk ngp
     */
    public String atType = "";
    /**
     * 请求bean
     */
    private RequestBean requestBean;
    /**
     * 广告位id
     */
    public String zoneid;
    /**
     * 广告id
     */
    public int at_id = 0;
    /**
     * 广告名称
     */
    public String at_title = "";
    /**
     * 广告描述
     */
    public String at_description;
    /**
     * 点击效果  apkdownload:跳转apk;redirectpage:跳转页面;redirectgp:跳转gp;openapp:打开app
     */
    public String click_effect;
    /**
     * 广告包名
     */
    public String at_pgname;
    /**
     * 广告展示样式
     */
    public String at_style;
    /**
     * 下载地址
     */
    private String at_download_url;
    /**
     * 位置信息
     */
    public String description;
    /**
     * 是否显示通知栏是否显示下载进度,0不显示，1显示。默认显示
     */
    public String notify = "1";
    /**
     * 是否展示过
     */
    protected boolean isExposure = false;
    /**
     * 表示是否点击过
     */
    protected boolean isClicked = false;
    /**
     * 加载图片模式
     */
    public int imageMode;
    /**
     * （以前api使用，目前暂时不用） 服务端 标识（用于表示，描述，图片，icon,titil,downloadurl 唯一性）
     */
    public String c_md5;
    /**
     * （以前api使用，目前暂时不用）  点击位置，四个值downx,downy,upx,upy
     */
    public int[] click_position;
    protected int touchxDown, touchyDown, relativexDown, relativeyDown;
    /**
     * 传入展示区域的view
     */
    public View view;
    /**
     * toutiao 穿山甲图标
     */
    public Bitmap iconUrl;
    /**
     * logo地址
     */
    public String bdLogoUrl;

    public NativeAdBean() {
        nativeAdPic = new NativeAdPic();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public RequestBean getRequestBean() {
        return requestBean;
    }

    public void setRequestBean(RequestBean requestBean) {
        this.requestBean = requestBean;
    }

    /**
     * 展示曝光
     *
     * @param view        要展示曝光的view
     * @param description 曝光的描述，可以为空
     */
    public void onExposure(final View view, final String description) {
        this.view = view;
        this.description = description;
        if (view == null)
            return;
        reportShow();
    }

    protected void reportShow() {
        if (isExposure) {
            return;
        }
        isExposure = true;
        Report.report(requestBean, ReportEvent.SHOW);
    }

    /**
     * @param description 点击事件的描述，可以为空
     */
    public void onClicked(final String description) {
        if (view == null) {
            return;
        }
        reportClick();
    }

    protected void reportClick() {
        if (isClicked) {
            return;
        }
        isClicked = true;
        Report.report(requestBean, ReportEvent.CLICK);
    }

    public boolean getIsClicked() {
        return isClicked;
    }

    public boolean getIsExposure() {
        return isExposure;
    }

    public void setExposure(boolean exposue) {
        isExposure = exposue;
    }

    public View getView() {
        return view;
    }

    public String Urlencoder(String url) throws UnsupportedEncodingException {
        String body = url.substring(0, url.indexOf("?"));
        String param = url.substring(url.indexOf("?") + 1, url.length());
        String[] params = param.split("&");
        int len = params.length;
        for (int i = 0; i < len; i++) {
            String str = params[i];
            String key = str.substring(0, str.indexOf("="));
            String value = str.substring(str.indexOf("=") + 1, str.length());
            if (i == 0) {
                body = body + "?" + key + "=" + URLEncoder.encode(value, "UTF-8");
            } else {
                body = body + "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
            }
        }
        return body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.provider);
        dest.writeSerializable(this.nativeAdPic);
        dest.writeString(this.at_apk_size);
        dest.writeString(this.atType);
        dest.writeString(this.zoneid);
        dest.writeInt(this.at_id);
        dest.writeString(this.at_title);
        dest.writeString(this.at_description);
        dest.writeString(this.click_effect);
        dest.writeString(this.at_pgname);
        dest.writeString(this.at_style);
        dest.writeString(this.at_download_url);
        dest.writeString(this.description);
        dest.writeString(this.notify);
        dest.writeByte(isExposure ? (byte) 1 : (byte) 0);
        dest.writeByte(isClicked ? (byte) 1 : (byte) 0);
        dest.writeString(this.c_md5);
    }

    private NativeAdBean(Parcel in) {
        this.provider = in.readString();
        this.nativeAdPic = (NativeAdPic) in.readSerializable();
        this.at_apk_size = in.readString();
        this.atType = in.readString();
        this.zoneid = in.readString();
        this.at_id = in.readInt();
        this.at_title = in.readString();
        this.at_description = in.readString();
        this.click_effect = in.readString();
        this.at_pgname = in.readString();
        this.at_style = in.readString();
        this.at_download_url = in.readString();
        this.description = in.readString();
        this.notify = in.readString();
        this.isExposure = in.readByte() != 0;
        this.isClicked = in.readByte() != 0;
        this.c_md5 = in.readString();
    }

    public static final Creator<NativeAdBean> CREATOR = new Creator<NativeAdBean>() {
        public NativeAdBean createFromParcel(Parcel source) {
            return new NativeAdBean(source);
        }

        public NativeAdBean[] newArray(int size) {
            return new NativeAdBean[size];
        }
    };
}
