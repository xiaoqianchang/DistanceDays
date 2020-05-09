package com.common.adlib.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * 基础信息管理, 需要application中调用{@link #init(Context)}方法
 *
 * 
 */
public class HandlerManager {
	private Context mContext;
	private Handler mMainHandler;

	private static HandlerManager sInstance;

	public static void init(Context context) {
		if(sInstance == null){
			sInstance = new HandlerManager(context);
		}
	}

	public static HandlerManager getInstance() {
		return sInstance;
	}

	public Context getContext() {
		return mContext;
	}

	/**
	 * 取得主线程Handler
	 * 
	 * @return Handler
	 */
	public Handler getMainHandler() {
		return mMainHandler;
	}

	private HandlerManager(Context context) {
		mContext = context;
		mMainHandler = new Handler(Looper.getMainLooper());
	}

}
