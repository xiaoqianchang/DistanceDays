package com.planet.light2345.sharelib.bean;

import android.os.Parcel;
import java.io.File;

/**
 * 分享的文本对象 Created by zhangk on 2018/8/22.
 */

public class ShareTextObject extends ShareObject {
  public String title;   //标题
  public String content;   //分享的内容
  public String targetUrl;   //目标地址
  public String thumbnailUrl;  //缩略图地址
  public File thumbFile;    //缩略图文件

  public ShareTextObject(int shareType, int sharePlatform, String title, String content, String targetUrl, String thumbnailUrl) {
    super(shareType, sharePlatform);
    this.title = title;
    this.content = content;
    this.targetUrl = targetUrl;
    this.thumbnailUrl = thumbnailUrl;
  }

  public ShareTextObject(int shareType, int sharePlatform, String title, String content, String targetUrl, File thumbFile) {
    super(shareType, sharePlatform);
    this.title = title;
    this.content = content;
    this.targetUrl = targetUrl;
    this.thumbFile = thumbFile;
  }

  public ShareTextObject(int shareType, int sharePlatform, String title, String content, String targetUrl, File thumbFile, String shareWindow) {
    super(shareType, sharePlatform, shareWindow);
    this.title = title;
    this.content = content;
    this.targetUrl = targetUrl;
    this.thumbFile = thumbFile;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.title);
    dest.writeString(this.content);
    dest.writeString(this.targetUrl);
    dest.writeString(this.thumbnailUrl);
    dest.writeSerializable(this.thumbFile);
  }

  protected ShareTextObject(Parcel in) {
    super(in);
    this.title = in.readString();
    this.content = in.readString();
    this.targetUrl = in.readString();
    this.thumbnailUrl = in.readString();
    this.thumbFile = (File) in.readSerializable();
  }

  public static final Creator<ShareTextObject> CREATOR = new Creator<ShareTextObject>() {
    @Override
    public ShareTextObject createFromParcel(Parcel source) {
      return new ShareTextObject(source);
    }

    @Override
    public ShareTextObject[] newArray(int size) {
      return new ShareTextObject[size];
    }
  };
}
