package com.hdyl.pintu.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;
import com.hdyl.pintu.GameView15;
import com.hdyl.pintu.Tools;
import com.hdyl.pintu.save.SaveData;


public class Constants {

    // 帮助
    public final static String URLHELP_STRING = "http://blog.sina.cn/dpool/blog/s/blog_49fef54f01018ecl.html?vt=4";

    public static final String APPID = "24b5f7acce38a940a7db476aa0e5c9ac";

    public final static int PICTURE_NUM = 50;
    public static Bitmap[] bitmaps = new Bitmap[PICTURE_NUM];
    public static Bitmap[] bitmaps2 = new Bitmap[PICTURE_NUM];
    public static Bitmap curPicBitmap = null;

    static {
        Bitmap numBitmap = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.mynum);
        int width = numBitmap.getWidth() / 5;
        int height = numBitmap.getHeight() / 2;
        int offsetY = height / 4;
        int offsetX = width / 4;
        for (int i = 0; i < 10; i++) {

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 设置一个iconSize大小的块的位图
            Canvas canvas = new Canvas(bitmap);// 获取bitMap的画布
            int yTimes = i / 5;
            canvas.drawBitmap(numBitmap, new Rect(width * (i % 5), yTimes * height, width * (i % 5 + 1), yTimes * height + height), new Rect(offsetX, offsetY, width / 2 + offsetX, height / 2 + offsetY), null);

            // b[0] ->0 b[1] ->1 b[9]->9
            bitmaps[(i + 1) % 10] = bitmap;
        }

        for (int i = 10; i < PICTURE_NUM; i++) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 设置一个iconSize大小的块的位图
            Canvas canvas = new Canvas(bitmap);// 获取bitMap的画布

            int shiwei = i / 10;
            int gewei = i % 10;
            canvas.drawBitmap(bitmaps[shiwei], new Rect(offsetX, offsetY, width / 2 + offsetX, height / 2 + offsetY), new Rect(0, offsetY, width / 2, height / 2 + offsetY), null);
            canvas.drawBitmap(bitmaps[gewei], new Rect(offsetX, offsetY, width / 2 + offsetX, height / 2 + offsetY), new Rect(width / 2, offsetY, width, height / 2 + offsetY), null);
            bitmaps[i] = bitmap;
        }
        // 这个资源不需要
        bitmaps[0].recycle();
        numBitmap.recycle();
        SaveData saveData = SaveData.getInstance();
        Bitmap defaultBitmap = null;
        // 图片模式且找得到图片
        if (saveData.isNummode == false && saveData.uriImage != null) {
            Bitmap bitmap = Tools.getBitmapFromUri(App.getContext(), Uri.parse(saveData.uriImage));

            if (bitmap != null)
                defaultBitmap = bitmap;
        }
        // 加载默认图片
        if (defaultBitmap == null)
            defaultBitmap = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.mine);
        loadBitmaps(defaultBitmap);
    }

    public static Bitmap getBitmap(int num) {
        if (SaveData.getInstance().isNummode)
            return bitmaps[num];
        return bitmaps2[num - 1];
    }

    public static Bitmap getNumBitmap(int num) {
        return bitmaps[num];
    }

    public static void loadBitmaps(Bitmap numBitmap) {
        if (curPicBitmap != null) {
            curPicBitmap.recycle();
            for (Bitmap bitmap : bitmaps2)
                bitmap.recycle();
        }
        curPicBitmap = numBitmap;
        int width = numBitmap.getWidth() / GameView15.SIZE;
        int height = numBitmap.getHeight() / GameView15.SIZE;
        for (int i = 0; i < GameView15.SIZE * GameView15.SIZE; i++) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 设置一个iconSize大小的块的位图
            Canvas canvas = new Canvas(bitmap);// 获取bitMap的画布
            int yTimes = i / GameView15.SIZE;
            canvas.drawBitmap(numBitmap, new Rect(width * (i % GameView15.SIZE), height * yTimes, width * (i % GameView15.SIZE) + width, height * yTimes + height), new Rect(0, 0, width, height), null);
            bitmaps2[i] = bitmap;
        }
    }

}
