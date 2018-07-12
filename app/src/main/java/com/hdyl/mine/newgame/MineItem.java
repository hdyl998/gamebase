package com.hdyl.mine.newgame;

import android.graphics.Bitmap;

import com.hdyl.mine.game.MineSetting;
import com.hdyl.mine.newgame.ui.IMineUIProvider;

/**
 * //$ git config --global http.sslverify "false"
 * Created by Administrator on 2018/6/30.
 */

public class MineItem {
    public int var;//
    public int flag;//标记


    public final static int VAR_OPEN = 0;
    public final static int VAR_COVER = 1;
    public final static int VAR_FLAG = 1 << 1 | VAR_COVER;//标记也算一种cover


    public boolean isOpen() {
        return isFlagValue(VAR_OPEN);
    }


    /***
     * 切换标记和cover
     */
    public void toggleCoverFlag() {
        if (!isOpen()) {
            if (isCover()) {
                flag = VAR_FLAG;
            } else {
                flag = VAR_COVER;
            }
        }
    }

    public boolean isCover() {
        return isFlagValue(VAR_COVER);
    }

    private boolean isFlagValue(int var) {
        return (flag & var) == var;
    }

    public boolean isFlag() {
        return isFlagValue(VAR_FLAG);
    }

    /***
     * 获取绘制的图片
     * @return
     */
    public Bitmap getDrawingBitmap(IMineUIProvider uiProvider) {
        switch (flag) {
            case VAR_OPEN:
                return uiProvider.getBitmapByNumber(var);
            case VAR_COVER:
                return uiProvider.getBitmapCover();
            case VAR_FLAG:
                return uiProvider.getBitmapFlag();
        }
        return null;
    }


//    public int getValue(){
//        return var-
//    }

}
