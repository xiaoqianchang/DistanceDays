package com.adups.distancedays.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的AbsListView的Adapter
 * <p/>
 * Created by Chang.Xiao on 2016/4/14 14:42.
 *
 * @version 1.0
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        if(mDatas != null && getCount() > 0 && position < mDatas.size()){
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    protected abstract void convert(ViewHolder holder, T t);

    public List<T> getData() {
        return mDatas;
    }

    public void setData(List<T> data){
        if (data == null) return;
        if (mDatas == null) {
            mDatas = data;
        } else {
            mDatas.clear();
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (data == null) return;
        if (mDatas == null) {
            mDatas = data;
        } else {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(int position, List<T> data) {
        if (mDatas == null) {
            mDatas = data;
        } else {
            mDatas.addAll(position, data);
        }
        notifyDataSetChanged();
    }

    public void deleteData(int position) {
        if(mDatas != null && mDatas.size() > position){
            mDatas.remove(position);
            notifyDataSetChanged();
        }
    }

    public void deleteData(T data) {
        if(mDatas != null) {
            mDatas.remove(data);
            notifyDataSetChanged();
        }
    }

    public void deleteDatas(List<T> data) {
        if(mDatas != null) {
            mDatas.removeAll(data);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if(mDatas != null){
            mDatas.clear();
            notifyDataSetChanged();
        } else {
            mDatas = new ArrayList<>();
        }
    }
}
