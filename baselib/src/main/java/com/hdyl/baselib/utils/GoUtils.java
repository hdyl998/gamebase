package com.hdyl.baselib.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;


/**
 * 应用跳转工具
 * Created by liugd on 2017/3/14.
 */

public class GoUtils {
    /***
     * 去应用市场
     */
    public static void goMarket(Context mContext) {
        try {
            Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
            Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent2);
        } catch (ActivityNotFoundException e) {
            ToastUtils.makeTextAndShow("抱歉,没有找到应用市场");
        }
    }

    /***
     * 以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
     *
     * @param mContext
     */
    public static void goAppDetailSettingIntent(Context mContext) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        }
        mContext.startActivity(localIntent);
    }

    public static void goMobileBrowser(Context mContext, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

}
