package com.adups.distancedays.adapter.abslistview;

/**
 * Created by Chang.Xiao on 2016/4/14 14:53.
 *
 * @version 1.0
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int position, T t);
}
