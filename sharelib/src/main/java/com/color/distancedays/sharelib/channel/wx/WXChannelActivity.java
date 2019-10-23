package com.color.distancedays.sharelib.channel.wx;

import android.content.Intent;
import com.color.distancedays.sharelib.channel.BaseChannelActivity;

/**
 * Created by zhangk on 2018/8/21.
 */

public class WXChannelActivity extends BaseChannelActivity<WXChannelController> {

  @Override
  public WXChannelController createController() {
    return new WXChannelController(this, mShareObject);
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mController.onActivityResultData(requestCode, resultCode, data);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mController.release();
  }
}
