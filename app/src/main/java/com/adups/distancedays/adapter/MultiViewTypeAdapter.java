package com.adups.distancedays.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.adups.distancedays.utils.ToolUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Item为多种Layout情形的适配器
 *
 * Created by Chang.Xiao on 2016/4/14 14:57.
 *
 * @version 1.0
 */
public final class MultiViewTypeAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private Map<Integer, MultiViewTypeSupport> viewTypes;
    private List<ItemModel> mDatas;
    private boolean mNotifyOnChange = true;

    public MultiViewTypeAdapter(Context context, Map<Integer, MultiViewTypeSupport> viewTypes) {
        this.mContext = context;
        if (viewTypes == null) {
            viewTypes = new HashMap<>();
        }
        this.viewTypes = new HashMap<>(viewTypes);
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
    }

    @Override
    public int getViewTypeCount() {
        if (viewTypes != null) {
            int size = viewTypes.size();
            if (size < 1) {
                return 1;
            }
            return size;
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        ItemModel itemModel = getItem(position);
        if (itemModel != null) {
            return itemModel.viewType;
        }
        return 0;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public ItemModel getItem(int position) {
        if (mDatas != null && getCount() > 0 && position < mDatas.size()) {
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
        MultiViewTypeSupport multiViewTypeSupport = viewTypes.get(getItemViewType(position));
        if (multiViewTypeSupport == null) {
            return null;
        }
        int layoutId = multiViewTypeSupport.getLayoutId();
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        multiViewTypeSupport.convert(holder, getItem(position), holder.getConvertView(), position);
        return holder.getConvertView();
    }

    private void checkViewType(int viewType) {
        if (viewTypes == null || !viewTypes.containsKey(viewType)) {
            //            if (ConstantsOpenSdk.isDebug) {
            throw new RuntimeException("设置ViewType时要先进行配置");
            //            }
        }
    }

    public ItemModel add(Object object, int viewType) {
        if (object == null) {
            return null;
        }
        checkViewType(viewType);
        ItemModel itemModel = new ItemModel(object, viewType);
        mDatas.add(itemModel);
        if (mNotifyOnChange)
            notifyDataSetChanged();

        return itemModel;
    }

    public void addAll(List list, int viewType) {
        if (ToolUtil.isEmptyCollects(list)) {
            return;
        }
        checkViewType(viewType);
        Object object = list.get(0);
        if (object == null) {
            return;
        }
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (item == null || item.getClass() != object.getClass()) {
                Logger.e(getClass().getSimpleName(), "Item 不能为null 并且同一Type的List中元素必须一致");
                iterator.remove();
            } else {
                ItemModel itemModel = new ItemModel(item, viewType);
                mDatas.add(itemModel);
            }
        }

        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void updateViewItem(View itemView, int position) {
        if (itemView == null || position < 0) {
            return;
        }
        ViewHolder holder = (ViewHolder) itemView.getTag();
        MultiViewTypeSupport multiViewTypeAdapter = viewTypes.get(getItemViewType(position));
        multiViewTypeAdapter.convert(holder, mDatas.get(position), itemView, position);
    }

    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
            if (mNotifyOnChange) notifyDataSetChanged();
        }
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }
}
