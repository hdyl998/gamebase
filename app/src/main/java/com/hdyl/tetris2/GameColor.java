package com.hdyl.tetris2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;

import java.util.Random;


public class GameColor {
    public static Bitmap[] bitmaps;
    public static Bitmap bitmapBlack;

    static {
        int[] resids = new int[]{R.drawable.cube_960_011,
                R.drawable.cube_960_012,
                R.drawable.cube_960_013,
                R.drawable.cube_960_014,
                R.drawable.cube_960_015,
                R.drawable.cube_960_016,
                R.drawable.cube_960_017
        };
        bitmaps = new Bitmap[resids.length];
        int count = 0;
        for (int i : resids) {
            bitmaps[count++] = BitmapFactory.decodeResource(App.getContext().getResources(), i);
        }
        bitmapBlack = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.black);
    }


    /***
     * 随机获得一种颜色
     *
     * @return
     */
    public static byte getRandomResIndex() {
        Random random = new Random();
        return (byte) random.nextInt(bitmaps.length);
    }

    public static Bitmap getBitmapAtIndex(int index) {
        return bitmaps[index];
    }

    public static Bitmap getBitmapBlack() {
        return bitmapBlack;
    }
}
