package com.planet.light2345.sharelib.bean;

import android.os.Parcel;

/**
 * 分享的图片对象
 * Created by zhangk on 2018/8/22.
 */

public class ShareImageObject extends ShareObject{

  public String imageLocalPath; //图片下载在本地的路径

  public ShareImageObject(int shareType, int sharePlatform, String imageLocalPath) {
    super(shareType, sharePlatform);
    this.imageLocalPath = imageLocalPath;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.imageLocalPath);
  }

  protected ShareImageObject(Parcel in) {
    super(in);
    this.imageLocalPath = in.readString();
  }

  public static final Creator<ShareImageObject> CREATOR = new Creator<ShareImageObject>() {
    @Override
    public ShareImageObject createFromParcel(Parcel source) {
      return new ShareImageObject(source);
    }

    @Override
    public ShareImageObject[] newArray(int size) {
      return new ShareImageObject[size];
    }
  };
}
