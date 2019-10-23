package com.adups.distancedays.manager;

import android.app.Activity;
import android.graphics.Bitmap;

import com.adups.distancedays.R;
import com.adups.distancedays.model.ShareModel;
import com.adups.distancedays.utils.CommonUtil;
import com.adups.distancedays.utils.ContextUtils;
import com.adups.distancedays.utils.ShareImageHelper;
import com.adups.distancedays.utils.ToastUtil;
import com.adups.distancedays.view.CommonProgressDialog;
import com.orhanobut.logger.Logger;
import com.color.distancedays.sharelib.ShareManager;
import com.color.distancedays.sharelib.bean.ShareImageObject;
import com.color.distancedays.sharelib.bean.ShareTextObject;
import com.color.distancedays.sharelib.constant.ShareConstant;
import com.color.distancedays.sharelib.listener.ShareListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * Share manager
 * <p>
 * Created by Chang.Xiao on 2019/10/21.
 *
 * @version 1.0
 */
public class AppShareManager {

    private static final String TAG = AppShareManager.class.getSimpleName();

    private final static int BASE_INDEX = 0;
    public static int INDEX_WEIXIN_FRIEND = BASE_INDEX;
    public static int INDEX_WEIXIN_GROUP = ++INDEX_WEIXIN_FRIEND;
    public static int INDEX_QQ = ++INDEX_WEIXIN_GROUP;

    public enum ShareToType {
        TO_WEIXIN_FRIEND(R.mipmap.ic_share_weixinfriend, R.string.share_weixin_friend, "weixin", INDEX_WEIXIN_FRIEND),
        TO_WEIXIN_GROUP(R.mipmap.ic_share_weixingroup, R.string.share_weixin_group, "weixinGroup", INDEX_WEIXIN_GROUP),
        TO_QQ(R.mipmap.ic_share_qq, R.string.share_qq, "weibo", INDEX_QQ);

        private String shareToType;
        private int iconResId;
        private int titleResId;
        private int index;

        ShareToType(@DrawableRes int iconResId, @StringRes int titleResId, String type, int index) {
            this.shareToType = type;
            this.iconResId = iconResId;
            this.titleResId = titleResId;
            this.index = index;
        }

        public String getShareToType() {
            return this.shareToType;
        }

        public int getIconResId() {
            return this.iconResId;
        }

        public int getTitleResId() {
            return this.titleResId;
        }

        public int getIndex() {
            return this.index;
        }
    }

    /**
     * 分享类型
     **/
    public static final int TYPE_SHARE_TXT = 1; // 分享文字
    public static final int TYPE_SHARE_IMG = 2; // 分享图片

    private Activity mContext; // 分享入口上下文；
    private HashMap<ShareToType, ShareModel> shareModelMap = new HashMap<>();
    private ShareToType mCurrentType;

    private AppShareManager() {
    }

    public static AppShareManager getInstance() {
        return ShareLibManagerHolder.INSTANCE;
    }

    private static final class ShareLibManagerHolder {
        private static final AppShareManager INSTANCE = new AppShareManager();
    }

    public ShareModel getShareModelByType(AppShareManager.ShareToType type) {
        if (shareModelMap.containsKey(type)) {
            return shareModelMap.get(type);
        }
        return null;
    }

    /**
     * 初始化分享sdk
     */
    public static void init() {
        String umengAppkey = CommonUtil.getApplication().getString(R.string.umeng_appkey);

        String weixinId = CommonUtil.getApplication().getString(R.string.share_weixin_id);
        String weixinSecrete = CommonUtil.getApplication().getString(R.string.share_weixin_secrete);
        String qqId = CommonUtil.getApplication().getString(R.string.share_qq_id);
        String qqSecrete = CommonUtil.getApplication().getString(R.string.share_qq_secrete);

        ShareManager.initShare(CommonUtil.getApplication(), umengAppkey, weixinId, weixinSecrete, qqId, qqSecrete);
    }

    /**
     * 外界分享入口
     *
     * @param activity
     * @param type
     * @param model
     */
    public void share(Activity activity, ShareToType type, ShareModel model) {
        if (!ContextUtils.checkContext(activity) || model == null) {
            return;
        }
        this.mContext = activity;
        this.mCurrentType = type;
        switch (model.getShareType()) {
            case TYPE_SHARE_TXT:
                umengShareAction(type, model, null);
                break;
            case TYPE_SHARE_IMG:
//                showLoading();
                ShareImageHelper.getShareMergeResultImage(mContext, model.getEventTitle(), model.getDay(), model.getDueDate(), new ShareImageHelper.OnImageResultListener() {
                    @Override
                    public void onImageReady(Bitmap bitmap) {
                        umengShareAction(type, model, bitmap);
                    }

                    @Override
                    public void onImageFail() {

                    }
                });
                break;
        }
    }

    /**
     * 友盟分享
     *
     * @param type 分享平台
     * @param model 分享文字类型时配置信息、分享图片时入参为空
     * @param bitmap 分享图片类型时配置信息、分享文字时入参为空
     */
    public void umengShareAction(ShareToType type, ShareModel model, Bitmap bitmap) {
        if (!ContextUtils.checkContext(mContext)) {
            return;
        }
        if (bitmap == null && model == null) {
//            dismissLoading();
            ToastUtil.showToast(mContext, R.string.share_error_msg);
            return;
        }
        int sharePlatform = getSharePlatform(type);
        if (bitmap == null) { // 文字分享
            String shareTitleText = model.getTitle();
            String shareContentText = model.getContent();
            String shareLinkUrl = model.getContentUrl();
            String picUrl = model.getPicUrl();
            ShareTextObject shareTextObject = new ShareTextObject(ShareConstant.ShareType.TYPE_TEXT, sharePlatform, shareTitleText, shareContentText,
                    shareLinkUrl, picUrl);
            ShareManager.getInstance().shareTo(mContext, shareTextObject, shareListener);
        } else { // 图片分享
            ShareImageObject shareImageObject = new ShareImageObject(ShareConstant.ShareType.TYPE_IMAGE, sharePlatform, extractBitmapToByteArray(bitmap));
            ShareManager.getInstance().shareTo(mContext, shareImageObject, shareListener);
        }
    }

    public static byte[] extractBitmapToByteArray(final Bitmap bmp) {
//        Bitmap thumbBmp = BitmapUtils.compressBySize(bmp,31);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private int getSharePlatform(ShareToType type) {
        int sharePlatform = ShareConstant.SharePlatform.PLATFORM_INVALID;
        switch (type) {
            case TO_WEIXIN_FRIEND:
                sharePlatform = ShareConstant.SharePlatform.WX;
                break;
            case TO_WEIXIN_GROUP:
                sharePlatform = ShareConstant.SharePlatform.WX_TIMELINE;
                break;
            case TO_QQ:
                sharePlatform = ShareConstant.SharePlatform.QQ;
                break;
        }
        return sharePlatform;
    }

    private ShareListener shareListener = new ShareListener() {
        @Override
        public void onStart(int platform) {
            Logger.d("onStart");
//            dismissLoading();
            String msg;
            if (mContext == null) {
                return;
            }
            if (platform == ShareConstant.SharePlatform.QQ) {
                msg = mContext.getString(R.string.share_to_qq);
            } else if (platform == ShareConstant.SharePlatform.WX) {
                msg = mContext.getString(R.string.share_to_wechat);
            } else if (platform == ShareConstant.SharePlatform.WX_TIMELINE) {
                msg = mContext.getString(R.string.share_to_wechat_circle);
            } else {
                ToastUtil.showToast(mContext, mContext.getString(R.string.share_umeng_default_text));
                return;
            }
            ToastUtil.showToast(mContext, mContext.getString(R.string.share_umeng_toast_text, msg));
        }

        @Override
        public void onResult(int platform) {
            Logger.d("onResult");
            //友盟分享成功
//            dismissLoading();
            release();
        }

        @Override
        public void onError(int platform, int errorType, Throwable throwable) {
            Logger.d("onError");
//            dismissLoading();
            String msg;
            if (platform == ShareConstant.SharePlatform.QQ) {
                msg = mContext.getString(R.string.share_to_qq);
            } else if (platform == ShareConstant.SharePlatform.WX) {
                msg = mContext.getString(R.string.share_to_wechat);
            } else if (platform == ShareConstant.SharePlatform.WX_TIMELINE) {
                if (errorType == ShareConstant.ErrorType.ERROR_TYPE_NOT_INSTALLED) {
                    msg = mContext.getString(R.string.share_to_wechat);
                } else {
                    msg = mContext.getString(R.string.share_to_wechat_circle);
                }
            } else {
                ToastUtil.showToast(mContext, mContext.getString(R.string.share_error_msg));
                return;
            }
            Logger.t(TAG).d("onError errorType=" + errorType);
            if (errorType == ShareConstant.ErrorType.ERROR_TYPE_NOT_INSTALLED) {
                ToastUtil.showToast(mContext, mContext.getString(R.string.share_error_no_platform, msg));
            } else {
                ToastUtil.showToast(mContext, mContext.getString(R.string.share_final_error_msg, msg));
            }
            release();
        }

        @Override
        public void onCancel(int platform) {
            Logger.d("onCancel");
//            dismissLoading();
            release();
        }
    };

    CommonProgressDialog mLoadDialog;
    public void dismissLoading() {
        if (mLoadDialog != null && mLoadDialog.isShowing()) {
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    public void showLoading() {
        if (mContext instanceof Activity) {
            if (mLoadDialog == null || !mLoadDialog.isShowing()) {
                mLoadDialog = new CommonProgressDialog(mContext);
                mLoadDialog.show();
            }
        }
    }

    public ShareToType getType() {
        return mCurrentType;
    }

    /**
     * 结束时释放内存
     */
    public void release() {
//        dismissLoading();
        Logger.d("ShareHelper release");
//        mLoadDialog = null;
        mContext = null;
        mCurrentType = null;
        ShareManager.getInstance().release();
    }
}
