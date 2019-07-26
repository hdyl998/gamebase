package com.hdyl.mine.newgame.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;

/**
 * xp风格
 * Created by Administrator on 2018/7/7.
 */

public class XPMineUIProvider extends IMineUIProvider {

    @Override
    protected Bitmap[] onCreateBitmaps() {
        int[] ids = new int[]{R.drawable.open, R.drawable._1, R.drawable._2, R.drawable._3, R.drawable._4, R.drawable._5, R.drawable._6, R.drawable._7, R.drawable._8, R.drawable.mine,// 9雷
                R.drawable.unopen,// 10打开的
                R.drawable.flag,// 标志11
                R.drawable.mineclick,// 点到雷12
                R.drawable.minewrong// 非雷，但标记错误13
        };
        Bitmap[] bitmaps = new Bitmap[ids.length];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(App.getContext().getResources(), ids[i]);
        }
        return bitmaps;
    }
}
