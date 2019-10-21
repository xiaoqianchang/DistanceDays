package com.planet.light2345.sharelib.bean;

import android.os.Parcel;

/**
 * Created by zhangk on 2018/8/20.
 */
public class ShareObject implements android.os.Parcelable {

  public int shareType;//分享type
  public int sharePlatform;//需要分享的平台
  public String shareWindow; //分享页的窗体（Activity）


  public ShareObject(int shareType, int sharePlatform) {
    this.shareType = shareType;
    this.sharePlatform = sharePlatform;
  }

  public ShareObject(int shareType, int sharePlatform, String shareWindow) {
    this.shareType = shareType;
    this.sharePlatform = sharePlatform;
    this.shareWindow = shareWindow;

  }

  public ShareObject() {
  }

  public static final Creator<ShareObject> CREATOR = new Creator<ShareObject>() {
    @Override
    public ShareObject createFromParcel(Parcel in) {
      return new ShareObject(in);
    }

    @Override
    public ShareObject[] newArray(int size) {
      return new ShareObject[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.shareType);
    dest.writeInt(this.sharePlatform);
    dest.writeString(this.shareWindow);
  }

  protected ShareObject(Parcel in) {
    this.shareType = in.readInt();
    this.sharePlatform = in.readInt();
    this.shareWindow = in.readString();
  }

}
