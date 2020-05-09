package com.android.ttlib;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.common.adlib.bean.ImageMode;
import com.common.adlib.bean.NativeAdBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TTNativeAdBean extends NativeAdBean implements Serializable {

    TTNativeAdBean() {
        super();
    }

    private TTNativeAd mFeedAd;
    private View video;

    void setFeedAd(TTNativeAd mFeedAd) {
        this.mFeedAd = mFeedAd;
    }

    public void onExposure(View view, String desc){
        super.onExposure(view, description);
        if (mFeedAd == null) {
            Log.e("TTNativeAdBean", "mFeedAd is null!");
            return;
        }
        this.view = view;
        this.description = desc;
        ViewGroup layoutDraw = (ViewGroup) view;
        List<View> clickViews = new ArrayList<>();
        if (imageMode == ImageMode.VIDEO) {
            video = mFeedAd.getAdView();
            if(video != null){
                layoutDraw.removeAllViews();
                layoutDraw.addView(video);
                /*
                int height = (int) dip2Px(view.getContext(), 46);
                int margin = (int) dip2Px(view.getContext(), 6);
                Button btTitle = null;
                btTitle = new Button(layoutDraw.getContext());
                btTitle.setText(mFeedAd.getTitle());
                if(btTitle != null){
                    clickViews.add(btTitle);
                }
                if(btTitle != null){
                Button action = new Button(layoutDraw.getContext());
                if(!TextUtils.isEmpty(mFeedAd.getButtonText())){
                action.setText(mFeedAd.getButtonText());
                }else {
                action.setText("查看详情");
                }
            //noinspection SuspiciousNameCombination
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int)(height * 3), height);
            lp.gravity = Gravity.END | Gravity.BOTTOM;
            lp.rightMargin = margin;
            lp.bottomMargin = margin;
            layoutDraw.addView(action, lp);
            creativeViews.add(action);
        }
                //video 情况加入双按钮
                FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams((int)(height * 3), height);
                lp1.gravity = Gravity.LEFT | Gravity.BOTTOM;
                lp1.leftMargin = margin;
                lp1.bottomMargin = margin;
                layoutDraw.addView(btTitle, lp1);*/
            }/* else {
                layoutDraw.removeAllViews();
                layoutDraw.addView(video);
            }*/
        }
        View tempView;
        if(video != null){
            tempView = video;
        }else {
            tempView = layoutDraw;
        }
        clickViews.add(tempView);
        List<View> creativeViews = new ArrayList<>();
        creativeViews.add(tempView);

        mFeedAd.registerViewForInteraction(layoutDraw, clickViews,creativeViews, new TTFeedAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, TTNativeAd ttNativeAd) {
                if (view == null) {
                    return;
                }
                reportClick();

            }

            @Override
            public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
                reportClick();
            }

            @Override
            public void onAdShow(TTNativeAd ttNativeAd) {
                reportShow();
            }
        });
    }

    private float dip2Px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

}
