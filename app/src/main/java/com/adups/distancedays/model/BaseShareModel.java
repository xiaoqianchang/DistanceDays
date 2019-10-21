package com.adups.distancedays.model;

import java.io.Serializable;

/**
 * Share model.
 * <p>
 * Created by Chang.Xiao on 2019/10/21.
 *
 * @version 1.0
 */
public abstract class BaseShareModel<T> implements Serializable {

    private static final long serialVersionUID = 8948917371951819828L;

    public long id;//分享事物id
    public String title;
    public String shareLink;//事物对外分享url
    public String content;//分享事物内容
    public String contentUrl;//事物内容分享url
    public String picUrl;//事物图片分享url
    public byte[] thumbData;//事物图片数据
    public int shareType;//分享类型，album/track/app等

    private BaseShareModel() {
    }

    public BaseShareModel(T object) {
        convert(object);
    }

    public abstract void convert(T object);
}
