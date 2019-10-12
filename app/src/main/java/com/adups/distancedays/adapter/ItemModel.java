package com.adups.distancedays.adapter;

/**
 * 多种布局的数据类型抽象为 ItemModel.
 * <p>
 * Created by Chang.Xiao on 2017/7/27.
 *
 * @version 1.0
 */
public class ItemModel<Model> {

    // Item 对应的数据模型
    protected Model model;
    // Item 对应的布局类型
    public int viewType;
    // Item 对应的数据类型
    protected int dataType;

    public ItemModel(Model model, int viewType) {
        this.model = model;
        this.viewType = viewType;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
