package com.planet.light2345.sharelib.channel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.planet.light2345.sharelib.ShareManager;
import com.planet.light2345.sharelib.bean.ShareObject;
import com.planet.light2345.sharelib.channel.Controller.ControllerListener;
import com.planet.light2345.sharelib.listener.ShareListener;

/**
 * Created by zhangk on 2018/8/21.
 */

public abstract class BaseChannelActivity<T extends Controller> extends Activity implements ControllerListener {

  public static String KEY_SHARE_OBJECT = "key_share_object";
  protected ShareObject mShareObject;
  private boolean mIsFirstEnter = true;
  protected T mController;
  protected ShareListener mShareListener;

  public static Intent newIntent(Activity activity, ShareObject shareObject, Class<?> cls) {
    Intent intent = new Intent(activity, cls);
    intent.putExtra(KEY_SHARE_OBJECT, shareObject);
    return intent;
  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    if (intent != null && intent.getExtras() != null) {
      setIntent(intent);
      mShareObject = intent.getParcelableExtra(KEY_SHARE_OBJECT);
    }
    mShareListener = ShareManager.getInstance().getShareListener();
    mController = createController();
    if (mController != null) {
      mController.setListener(this);
      mController.share();
    } else {
      finish();
    }
  }

  public abstract T createController();


  @Override
  protected void onResume() {
    try {
      super.onResume();
      //防止Activity分享完成后没有及时的关闭，新版微信分享即使成功了也不会有回调
      if (mIsFirstEnter) {
        mIsFirstEnter = false;
        return;
      }
    } catch (Exception e) {
    }
    finish();
  }

  @Override
  public void onHandleStart() {
    if (mShareListener != null && mShareObject != null) {
      mShareListener.onStart(mShareObject.sharePlatform);
    }
  }

  @Override
  public void onHandleError(int errorType, Throwable throwable) {
    if (mShareListener != null && mShareObject != null) {
      mShareListener.onError(mShareObject.sharePlatform, errorType, throwable);
    }
    finish();
  }

  @Override
  public void onHandleCancel() {
    if (mShareListener != null && mShareObject != null) {
      mShareListener.onCancel(mShareObject.sharePlatform);
    }
    finish();
  }

  @Override
  public void onHandleResult() {
    if (mShareListener != null && mShareObject != null) {
      mShareListener.onResult(mShareObject.sharePlatform);
    }
    finish();
  }
}
