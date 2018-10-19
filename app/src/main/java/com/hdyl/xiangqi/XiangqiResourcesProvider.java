package com.hdyl.xiangqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;

/**
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class XiangqiResourcesProvider {

    private static XiangqiResourcesProvider manager;

    public static XiangqiResourcesProvider getInstance() {
        if (manager == null) {
            manager = new XiangqiResourcesProvider();
        }
        return manager;
    }

    Bitmap[] bitmaps;//车马相仕炮兵将,与ChessType一致

    //背景
    Bitmap bitmapBg;

    //焦点图
    Bitmap bitmapFocus;

    public Bitmap getBitmapInChess(int index) {
        return bitmaps[index];
    }

    public Bitmap getBitmapBg() {
        return bitmapBg;
    }

    public Bitmap getBitmapFocus() {
        return bitmapFocus;
    }

    private XiangqiResourcesProvider() {
        init(App.getContext());
    }

    private void init(Context mContext) {
        bitmapBg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chessboard);

        int ress[] = {R.drawable.red_rook, R.drawable.red_knight, R.drawable.red_bishop, R.drawable.red_advisor, R.drawable.red_king, R.drawable.red_cannon, R.drawable.red_pawn,
                R.drawable.black_rook, R.drawable.black_knight, R.drawable.black_bishop, R.drawable.black_advisor, R.drawable.black_king, R.drawable.black_cannon, R.drawable.black_pawn
        };
        bitmaps = new Bitmap[ress.length];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(mContext.getResources(), ress[i]);
        }
        bitmapFocus = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.focus);
    }

    public static void onDestory() {
        manager = null;
    }
}
