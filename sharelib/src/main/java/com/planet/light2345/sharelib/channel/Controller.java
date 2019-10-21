package com.planet.light2345.sharelib.channel;

import android.app.Activity;
import com.planet.light2345.sharelib.bean.ShareObject;
import com.planet.light2345.sharelib.constant.ShareConstant.ErrorType;
import com.planet.light2345.sharelib.util.UMShareUtil;

/**
 * Created by zhangk on 2018/8/21.
 */

public class Controller {

  protected ShareObject mShareObject;
  protected Activity mActivity;
  protected UMShareUtil mUmShareUtil;

  public Controller(Activity activity, ShareObject shareObject) {
    this.mActivity = activity;
    this.mShareObject = shareObject;
    mUmShareUtil = new UMShareUtil();
  }


  /**
   * 在这里检查数据
   * 根据不同的分享类型判断检查数据
   */
  protected boolean checkData() {
    if (mActivity == null) {
      if (mControllerListener != null) {
        mControllerListener.onHandleError(ErrorType.ERROR_TYPE_ACTIVITY_NOT_NULL, new Throwable("Activity 不能为null"));
      }
      return false;
    }
    if (mShareObject == null) {
      if (mControllerListener != null) {
        mControllerListener.onHandleError(ErrorType.ERROR_TYPE_SHARE_OBJECT_NOT_NULL, new Throwable("ShareObject 不能为null"));
      }
      return false;
    }

    return true;
  }

  /**
   * 统一的分享入口
   * 如果需要替换掉友盟分享，修改这里即可
   */
  public void share() {
    if (!checkData()) {
      return;
    }
    if (mUmShareUtil != null) {
      mUmShareUtil.startUmengShare(mActivity, mShareObject, mControllerListener);
    }
  }

  public void release() {
    if (mUmShareUtil != null) {
      mUmShareUtil.release(mActivity);
    }
  }

  protected ControllerListener mControllerListener;

  public void setListener(ControllerListener listener) {
    this.mControllerListener = listener;
  }

  public interface ControllerListener {

    void onHandleStart();


    void onHandleError(int errorType, Throwable throwable);

    void onHandleCancel();


    void onHandleResult();

  }

}
