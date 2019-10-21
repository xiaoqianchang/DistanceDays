package com.planet.light2345.sharelib.channel.qq;

import android.app.Activity;
import android.content.Intent;
import com.planet.light2345.sharelib.bean.ShareObject;
import com.planet.light2345.sharelib.channel.Controller;

/**
 * Created by zhangk on 2018/8/21.
 */

public class QQChannelController extends Controller {

  public QQChannelController(Activity activity, ShareObject shareObject) {
    super(activity, shareObject);
  }


  /**
   * 处理分享回调
   */
  public void onActivityResultData(int requestCode, int resultCode, Intent data) {
    if (mUmShareUtil != null) {
      mUmShareUtil.registerOnActivityResult(mActivity, requestCode, resultCode, data);
    }
  }
}
