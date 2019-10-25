package com.adups.distancedays.model;

/**
 * 列表、网格的footer模型
 * <p>
 * Created by Chang.Xiao on 2019/10/25.
 *
 * @version 1.0
 */
public class FooterModel {

    private int resId; // 图标资源id
    private String hint; // 提示

    public FooterModel(int resId, String hint) {
        this.resId = resId;
        this.hint = hint;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
