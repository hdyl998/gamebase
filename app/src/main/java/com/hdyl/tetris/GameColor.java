//package com.hdyl.tetris;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//
//import com.hdyl.tetris.common.MyApp;
//
//import java.util.Random;
//
//
//public class GameColor {
//    public static Bitmap[] bitmaps;
//    public static Bitmap bitmapBlack;
//
//    static {
//        int[] resids = new int[]{R.mipmap.cube_960_011,
//                R.mipmap.cube_960_012,
//                R.mipmap.cube_960_013,
//                R.mipmap.cube_960_014,
//                R.mipmap.cube_960_015,
//                R.mipmap.cube_960_016,
//                R.mipmap.cube_960_017
//        };
//        bitmaps = new Bitmap[resids.length];
//        int count = 0;
//        for (int i : resids) {
//            bitmaps[count++] = BitmapFactory.decodeResource(MyApp.getContext().getResources(), i);
//        }
//        bitmapBlack = BitmapFactory.decodeResource(MyApp.getContext().getResources(), R.mipmap.black);
//    }
//
//
//    /***
//     * 随机获得一种颜色
//     *
//     * @return
//     */
//    public static int getRandomResIndex() {
//        Random random = new Random();
//        return random.nextInt(bitmaps.length);
//    }
//
//    public static Bitmap getBitmapAtIndex(int index) {
//        return bitmaps[index];
//    }
//
//    public static Bitmap getBitmapBlack() {
//        return bitmapBlack;
//    }
//}
