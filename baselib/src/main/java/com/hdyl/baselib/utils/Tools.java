package com.hdyl.baselib.utils;

import android.content.Context;
import android.graphics.Paint;

import com.hdyl.baselib.base.App;

/**
 * Created by liugd on 2017/7/21.
 */

public class Tools {
    /**
     * 得到demo的px大小
     *
     * @param dimen
     * @return
     */
    @Deprecated
    public static int getDimen750Px(Context context, int dimen) {
        float dp = dimen * 360f / 750;//除以750表示用的是750的标注
        return dip2px(context, dp);
    }

    public static int getDimen750Px(int dimen) {
        float dp = dimen * 360f / 750;//除以750表示用的是750的标注
        return dip2px(App.getContext(), dp);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 得到字体高度，用于画Y轴字体与线居中
     *
     * @param paint
     * @return
     */
    public static int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) (Math.ceil(fm.descent - fm.ascent) + 2) / 2;
    }
}
