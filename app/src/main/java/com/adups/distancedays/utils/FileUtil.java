package com.adups.distancedays.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件处理工具类
 * <p>
 * Created by Chang.Xiao on 2019/10/16.
 *
 * @version 1.0
 */
public class FileUtil {

    private static final String ENCODE = "UTF-8";

    /**
     * 从assets目录下面读取文件
     *
     * @param context 上下文
     * @return
     */
    public static String readAssetFileData(Context context, String filePath) {
        if (context == null)
            return "";

        String result = "";
        if (result == null || result.equals("")) {
            AssetManager am = context.getResources().getAssets();
            InputStream inputStream = null;
            try {
                inputStream = am.open(filePath);
                result = inputStream2String(inputStream, ENCODE);
            } catch (Exception e) {
                Logger.e("getAssets 发生异常:" + e.toString());
            } finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                        inputStream = null;
                    } catch (IOException e) {
                        Logger.e("close getAssets 发生异常:" + e.toString());
                    }
            }
        }
        if (result == null)
            result = "";

        return result;
    }

    public static String inputStream2String(InputStream inputStream, String encoding) {
        String result = "";
        try {
            int lenght = inputStream.available();
            byte[] buffer = new byte[lenght];
            inputStream.read(buffer);
            result = new String(buffer, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result == null)
            result = "";

        return result;
    }
}
