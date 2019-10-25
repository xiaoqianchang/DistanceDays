package com.adups.distancedays.adapter;

import android.view.View;

/**
 * 适用于多个viewtype
 *
 * Created by Chang.Xiao on 2016/4/14 14:53.
 *
 * @version 1.0
 */
public interface MultiViewTypeSupport<Model> {

    /**
     * 返回布局id
     *
     * @return
     */
    int getLayoutId();

    void convert(ViewHolder holder, ItemModel<Model> t, View convertView, int position);
}
