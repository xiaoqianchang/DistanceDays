package com.adups.distancedays.manager;

import android.app.Activity;

import com.adups.distancedays.R;
import com.adups.distancedays.model.BaseShareModel;
import com.adups.distancedays.utils.CommonUtil;
import com.adups.distancedays.view.CommonProgressDialog;

import java.lang.ref.WeakReference;
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
public class ShareManager {

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

    private WeakReference<Activity> mActivityRef;
//    public CallbackManager mCallbackManager;
//    private ShareDialog mFBShareDialog;
    private volatile static ShareManager INSTANCE = null;
    private HashMap<ShareToType, BaseShareModel> shareModelMap = new HashMap<>();

    private ShareManager() {
    }

    private ShareManager(Activity activity) {
        mActivityRef = new WeakReference<>(activity);
    }

    public static ShareManager getInstance() {
        return ShareManagerHolder.INSTANCE;
    }

    public BaseShareModel getShareModelByType(ShareManager.ShareToType type) {
        if (shareModelMap.containsKey(type)) {
            return shareModelMap.get(type);
        }
        return null;
    }

    private static final class ShareManagerHolder {
        private static final ShareManager INSTANCE = new ShareManager();
    }

    CommonProgressDialog progressDialog;

    /**
     * 初始化分享sdk
     */
    public static void init() {
        String weixinId = CommonUtil.getApplication().getString(R.string.share_weixin_id);
        String weixinSecrete = CommonUtil.getApplication().getString(R.string.share_weixin_secrete);
        String qqId = CommonUtil.getApplication().getString(R.string.share_qq_id);
        String qqSecrete = CommonUtil.getApplication().getString(R.string.share_qq_secrete);

        com.planet.light2345.sharelib.ShareManager.initShare(CommonUtil.getApplication(), weixinId, weixinSecrete, qqId, qqSecrete);
    }


    /**
     * 分享方式 云控类型
     */
    public static final int SHARE_TYPE_IMG = 1;//分享图片
    private static final int SHARE_TYPE_TXT = 2;//分享文字
    private static final int SHARE_TYPE_PASTE = 4;//分享粘贴文字

    public void share(BaseShareModel model, ShareToType type) {

    }
}
