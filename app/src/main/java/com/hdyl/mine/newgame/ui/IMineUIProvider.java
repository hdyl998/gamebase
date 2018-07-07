package com.hdyl.mine.newgame.ui;

import android.graphics.Bitmap;

/**
 * 位图资源提供器
 * Created by Administrator on 2018/7/7.
 */

public abstract class IMineUIProvider {



    ////位图资源 0 打开图 1-8对应数字，9打开雷，10 没开打的，11flag标志 12爆雷 13 ERROR
    public final Bitmap[] getBitmaps() {
        return bitmaps;
    }

    protected abstract Bitmap[] onCreateBitmaps();


    public Bitmap getBitmapByNumber(int number) {
        return bitmaps[number];
    }

    public Bitmap getBitmapCover() {
        return bitmaps[ID_COVER];
    }

    public Bitmap getBitmapFlag() {
        return bitmaps[ID_FLAG];
    }


    private Bitmap[] bitmaps;


    public IMineUIProvider() {
        bitmaps = onCreateBitmaps();
    }

    public final static int ID_COVER = 10;
    public final static int ID_FLAG = 11;
    public final static int ID_ERROR_MINE = 12;
    public final static int ID_ERROR_BLACK = 13;

}
