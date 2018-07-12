package com.hdyl.mine.base;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hdyl.baselib.base.App;

public class ScreenSizeUtils {

    public final static int SCREEN_WIDTH;
    public final static int SCREEN_HEIGHT;

    static {
        DisplayMetrics display = new DisplayMetrics();
        WindowManager manager = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(display);
        SCREEN_HEIGHT = Math.max(display.heightPixels, display.widthPixels);
        SCREEN_WIDTH = Math.min(display.heightPixels, display.widthPixels);
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

}
