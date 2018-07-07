package com.hdyl.mine.newgame.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;

/**
 * Win7风格
 * Created by Administrator on 2018/7/7.
 */

public class Win7MineUIProvider extends IMineUIProvider {

    @Override
    protected Bitmap[] onCreateBitmaps() {
        int[] ids = new int[]{R.drawable.mine_open_normal, R.drawable.mine_open_1, R.drawable.mine_open_2, R.drawable.mine_open_3, R.drawable.mine_open_4, R.drawable.mine_open_5, R.drawable.mine_open_6, R.drawable.mine_open_7, R.drawable.mine_open_8, R.drawable.bomb_revealed,// 9雷
                R.drawable.mine_normal,// 10没开的
                R.drawable.i_flag_down,// 标志11
                R.drawable.bomb_death,// 点到雷12
                R.drawable.bomb_mis_flagged,// 非雷，但标记错误13
        };

        Bitmap[] bitmaps = new Bitmap[ids.length];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(App.getContext().getResources(), ids[i]);
        }
        for (int i = 1; i < 10; i++) {
            bitmaps[i] = reDrawBitmap(bitmaps[i], bitmaps[0]);
        }
        bitmaps[ID_ERROR_BLACK] = reDrawBitmap(bitmaps[ID_ERROR_BLACK], bitmaps[0]);
        bitmaps[ID_FLAG] = reDrawBitmap(bitmaps[ID_FLAG], bitmaps[ID_COVER]);
        return bitmaps;
    }

    /**
     * 重新绘制BITMAP
     *
     * @param source
     * @param bgBitmap
     */
    private Bitmap reDrawBitmap(Bitmap source, Bitmap bgBitmap) {
        Bitmap output = Bitmap.createBitmap(bgBitmap.getWidth(), bgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Rect rect = new Rect(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        canvas.drawBitmap(bgBitmap, rect, rect, null);
        canvas.drawBitmap(source, rect, rect, null);
        return output;
    }
}
