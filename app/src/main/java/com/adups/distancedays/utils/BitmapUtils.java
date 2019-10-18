package com.adups.distancedays.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/18.
 *
 * @version 1.0
 */
public class BitmapUtils {

    /**
     * 该方法适配从drawable中的xml获取drawable转bitmap。
     * 查看https://stackoverflow.com/questions/10111073/how-to-get-a-bitmap-from-a-drawable-defined-in-a-xml
     *
     * 如果是读取drawable中的图片转换为bitmap，
     * 用该方法BitmapFactory.decodeResource(getResources(), R.drawable.bg_switch_calendar_track).copy(Bitmap.Config.ARGB_8888, true);
     * 如果是读取drawable中的xml获取drawable转bitmap，该方法返回null并报空指针。
     *
     * @param context
     * @param drawableRes
     * @return
     */
    public static Bitmap getBitmap(Context context, int drawableRes) {
        if (context == null) {
            return null;
        }
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
