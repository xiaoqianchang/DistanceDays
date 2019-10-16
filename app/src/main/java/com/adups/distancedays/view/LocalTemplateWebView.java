package com.adups.distancedays.view;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static android.webkit.WebSettings.LayoutAlgorithm.SINGLE_COLUMN;

/**
 * 加载本地模板的WebView
 * 富文本本地控制逻辑待添加
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class LocalTemplateWebView extends WebView {

    public interface URLClickListener {
        boolean urlClick(String url);
    }

    private URLClickListener mURLClickListener;

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:JsInterface.resize(document.body.getBoundingClientRect().height)");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (mURLClickListener != null && mURLClickListener.urlClick(url)) {
                return true;
            }
            // 这里表示此webView默认不处理点击事件
            return true;
        }
    };

    public LocalTemplateWebView(Context context) {
        super(context);
    }

    public LocalTemplateWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocalTemplateWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void destroy() {
        release();
        super.destroy();
    }

    public void setURLClickListener(URLClickListener URLClickListener) {
        mURLClickListener = URLClickListener;
    }

    @Override
    public void setOverScrollMode(int mode) {
        try {
            super.setOverScrollMode(mode);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 通过模板显示html内容
     *
     * @param richStr
     */
    public void setData(String richStr) {
        if (Looper.myLooper() == null) {
            return;
        }
        setWebViewClient(mWebViewClient);
        WebSettings settings = getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(true);
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(false);
            settings.setUseWideViewPort(true); // 让图片自适应屏幕
            settings.setLayoutAlgorithm(SINGLE_COLUMN);
            settings.setLoadWithOverviewMode(true);
            settings.setDomStorageEnabled(true);
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            settings.setAppCacheEnabled(false);
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    settings.setMediaPlaybackRequiresUserGesture(false);
                } catch (Throwable e) {
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {   //TODO 关闭debug
            setWebContentsDebuggingEnabled(true);
        }

        try {
            loadDataWithBaseURL(null, richStr, "text/html", "utf-8", null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        setFocusable(false);

        dealJavascriptLeak();
    }

    private void dealJavascriptLeak() {
        try {
            removeJavascriptInterface("searchBoxJavaBridge_");
            removeJavascriptInterface("accessibility");
            removeJavascriptInterface("accessibilityTraversal");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放
     */
    private void release() {
        if (getParent() != null && getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).removeView(this);
        }
        removeAllViews();
        loadUrl("about:blank");
        stopLoading();
        setWebChromeClient(null);
        setWebViewClient(null);
        if (getSettings() != null) {
            getSettings().setJavaScriptEnabled(false);
        }
    }
}
