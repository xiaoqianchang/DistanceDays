package com.android.ttlib;

import android.view.View;

import androidx.annotation.MainThread;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.common.adlib.base.BaseAd;
import com.common.adlib.base.HandlerManager;
import com.common.adlib.base.Report;
import com.common.adlib.base.ReportEvent;
import com.common.adlib.bean.AEffect;
import com.common.adlib.bean.ImageMode;
import com.common.adlib.bean.NativeAdBean;
import com.common.adlib.bean.NativeBean;
import com.common.adlib.bean.RequestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.common.adlib.toutiao.ToutiaoAd.java
 * @author: gz
 * @date: 2019-11-12 16:07
 */
public class ToutiaoAd extends BaseAd {

    public ToutiaoAd(RequestBean requestBean){
        super(requestBean);
    }
    private TTAdNative mTTAdNative;

    private void initAdNative() {
        if(HandlerManager.getInstance().getContext() != null){
            mTTAdNative = TTAdManagerHolder.getInstance(requestBean.getAppid())
                    .createAdNative(HandlerManager.getInstance().getContext());
        }
    }

    @Override
    public void requestSplashAd(final SplashAdListener listener, int adTimeOut) {
        super.requestSplashAd(listener,adTimeOut);
        initAdNative();
        if(mTTAdNative == null){
            return;
        }
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(requestBean.getZoneid())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .build();
        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                listener.onAdError(code,message);
            }

            @Override
            @MainThread
            public void onTimeout() {
                listener.onAdTimeOver();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                if (ad == null) {
                    return;
                }
                listener.onLoaded();

                //获取SplashView
                View view = ad.getSplashView();
                layout.removeAllViews();
                //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                layout.addView(view);
                //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                //ad.setNotAllowSdkCountdown();
                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        listener.onAdClick();
                        Report.report(requestBean, ReportEvent.CLICK);
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        listener.onAdShow();
                        Report.report(requestBean, ReportEvent.SHOW);
                    }

                    @Override
                    public void onAdSkip() {
                        listener.onAdSkip();
                    }

                    @Override
                    public void onAdTimeOver() {
                        listener.onAdTimeOver();
                    }
                });
            }
        }, adTimeOut);
        Report.report(requestBean,ReportEvent.REQUEST);
    }

    /**
     * 个性化模板
     */
    @Override
    public void requestBannerAd(final BannerAdListener listener) {
        super.requestBannerAd(listener);
        initAdNative();
        if(mTTAdNative == null){
            return;
        }
        layout.removeAllViews();
        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(requestBean.getZoneid())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(scaleWidth, scaleHeight) //这个参数设置即可，不影响模板广告的size
                .setExpressViewAcceptedSize(scaleWidth,scaleHeight) //期望模板广告view的size,单位dp
                .setAdCount(requestBean.getCount())
                .build();
        //调用feed原生信息流（包含视频）广告异步请求接口
        mTTAdNative.loadBannerExpressAd(adSlot,  new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                listener.onAdError(code,message);
                layout.removeAllViews();
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0){
                    listener.onAdError(0,"ad is null || size is 0");
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);

                mTTAd.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        listener.onAdClick();
                        Report.report(requestBean,ReportEvent.CLICK);
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        listener.onAdShow();
                        Report.report(requestBean, ReportEvent.SHOW);
                    }

                    @Override
                    public void onRenderFail(View view, String msg, int code) {
                        listener.onAdError(code,msg);
                    }

                    @Override
                    public void onRenderSuccess(View bannerView, float width, float height) {
                        if (bannerView == null) {
                            listener.onAdError(0,"bannerView is null");
                            return;
                        }
                        listener.onLoaded();
                        layout.removeAllViews();
                        layout.addView(bannerView);
                    }
                });
                if (mTTAd.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD){
                    return;
                }
                mTTAd.render();
            }
        });
        Report.report(requestBean,ReportEvent.REQUEST);
    }


    /**
     * 自渲染
     */
    /*@Override
    public void requestBannerAd(final BannerAdListener listener) {
        super.requestBannerAd(listener);
        initAdNative();
        if(mTTAdNative == null){
            return;
        }
        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(requestBean.getZoneid())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 100) //这个参数设置即可，不影响模板广告的size
                //.setExpressViewAcceptedSize(scaleWidth,scaleHeight) //期望模板广告view的size,单位dp
                .setAdCount(requestBean.getCount())
                .build();
        //调用feed原生信息流（包含视频）广告异步请求接口
        mTTAdNative.loadBannerAd(adSlot,  new TTAdNative.BannerAdListener() {
            @Override
            public void onError(int code, String message) {
                listener.onAdError(code,message);
            }

            @Override
            public void onBannerAdLoad(final TTBannerAd ad) {
                if (ad == null) {
                    listener.onAdError(0,"ad is null");
                    return;
                }

                View bannerView = ad.getBannerView();
                if (bannerView == null) {
                    listener.onAdError(0,"bannerView is null");
                    return;
                }
                listener.onLoaded();

                layout.removeAllViews();
                layout.addView(bannerView);
                ad.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        listener.onAdClick();
                        Report.report(requestBean.getZoneid(),ReportEvent.CLICK);
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        listener.onAdShow();
                        Report.report(requestBean.getZoneid(), ReportEvent.SHOW);
                    }
                });
            }
        });
        Report.report(requestBean.getZoneid(),ReportEvent.REQUEST);
    }*/

    @Override
    public void requestNativeAd(final NativeAdListener listener) {
        super.requestNativeAd(listener);
        initAdNative();
        if(mTTAdNative == null){
            return;
        }
        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(requestBean.getZoneid())
                .setSupportDeepLink(true)
                .setImageAcceptedSize(scaleWidth,scaleHeight)
                .setAdCount(1)
                .build();

        //调用feed原生信息流（包含视频）广告异步请求接口
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                listener.onAdError(code,message);
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                if (ads == null) {
                    listener.onAdError(0," ads is null");
                    return;
                }
                NativeBean adBean = parseAdBean(ads,0);
                listener.onLoaded(adBean);
            }
        });
        Report.report(requestBean,ReportEvent.REQUEST);
    }

    private NativeBean parseAdBean(List ads,int imgType) {
        NativeBean nativeBean = null;
        TTNativeAdBean ttNativeAdBean;

        List<NativeAdBean> nativeAdBeens = new ArrayList<>();

        try {
            nativeBean = new NativeBean();
            /*if (!TextUtils.isEmpty(localzoneid)) {
                nativeBean.zoneid = localzoneid;
            } else {
                nativeBean.zoneid = sdkType + zoneId;
            }*/

            TTNativeAd orginalTTAd = null;
            for (int i = 0; i < ads.size(); i++) {
                if(ads.get(i) instanceof TTFeedAd){
                    orginalTTAd = (TTFeedAd)ads.get(i);
                }else if(ads.get(i) instanceof TTNativeAd){
                    orginalTTAd = (TTNativeAd)ads.get(i);
                }
                if (orginalTTAd == null) {
                    break;
                }
                ttNativeAdBean = new TTNativeAdBean();
                ttNativeAdBean.setRequestBean(requestBean);
                ttNativeAdBean.setFeedAd(orginalTTAd);
                ttNativeAdBean.iconUrl = orginalTTAd.getAdLogo();
                //ttNativeAdBean.at_pgname = orginalTTAd.getAppPackage();
                ttNativeAdBean.at_description = orginalTTAd.getDescription();
                ttNativeAdBean.at_title = orginalTTAd.getTitle();
                ttNativeAdBean.at_apk_size = String.valueOf(orginalTTAd.getAppSize());
                ttNativeAdBean.nativeAdPic.icon = orginalTTAd.getIcon().getImageUrl();
                if (orginalTTAd.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
                    ttNativeAdBean.imageMode = ImageMode.SMALL;
                } else if (orginalTTAd.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
                    ttNativeAdBean.imageMode = ImageMode.LARGE;
                } else if (orginalTTAd.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
                    ttNativeAdBean.imageMode = ImageMode.GROUP;
                } else if (orginalTTAd.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) {
                    ttNativeAdBean.imageMode = ImageMode.VIDEO;
                } else if(imgType > 0){
                    ttNativeAdBean.imageMode = imgType;
                }else {
                    ttNativeAdBean.imageMode = ImageMode.UNKNOWN;
                }
                if (ttNativeAdBean.imageMode > ImageMode.UNKNOWN && ttNativeAdBean.imageMode < ImageMode.VIDEO) {
                    if (orginalTTAd.getImageList() != null && !orginalTTAd.getImageList().isEmpty()) {
                        TTImage image = orginalTTAd.getImageList().get(0);
                        if (image != null && image.isValid()) {
                            ttNativeAdBean.nativeAdPic.banner = image.getImageUrl();
                        }
                    }
                }
                if (orginalTTAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ttNativeAdBean.click_effect = AEffect.DOWNLOAD.getValue();
                } else {
                    ttNativeAdBean.click_effect = AEffect.REDIRECT_PAGE.getValue();
                }
                ttNativeAdBean.at_style = "3";

                ttNativeAdBean.zoneid = requestBean.getZoneid();
                ttNativeAdBean.atType = requestBean.getSdkType();

                ttNativeAdBean.setProvider(requestBean.getSdkType());
                ttNativeAdBean.at_id = 0;
                nativeAdBeens.add(ttNativeAdBean);
            }
            nativeBean.nativeAdBeens = nativeAdBeens;
        } catch (Exception e) {
            //LogUtils.e(TAG, "native adverts baidu NativeResponse Parse failure!\n" + e.getMessage());
        }
        return nativeBean;
    }
}
