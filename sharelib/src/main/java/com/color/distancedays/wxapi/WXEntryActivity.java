package com.color.distancedays.wxapi;

import android.os.Bundle;

import com.color.distancedays.sharelib.util.SystemUtils;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtils.fixAndroid26OrientationBug(this);
    }
}
