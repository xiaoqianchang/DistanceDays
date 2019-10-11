package com.adups.distancedays.adapter.abslistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.adups.distancedays.adapter.ViewHolder;

import java.util.List;

/**
 * Item为多种Layout情形的适配器
 *
 * Created by Chang.Xiao on 2016/4/14 14:57.
 *
 * @version 1.0
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    protected MultiItemTypeSupport mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas, MultiItemTypeSupport multiItemTypeSupport) {
        super(context, -1, datas);
        this.mMultiItemTypeSupport = multiItemTypeSupport;
        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemTypeSupport != null) {
            return mMultiItemTypeSupport.getViewTypeCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null) {
            return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mMultiItemTypeSupport == null) {
            return super.getView(position, convertView, parent);
        }

        int layoutId = mMultiItemTypeSupport.getLayoutId(position, mDatas.get(position));
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }
}
