package com.hdyl.mine.newgame;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;

import com.hdyl.mine.newgame.ui.IMineUIProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.PhantomReference;

/**
 * //$ git config --global http.sslverify "false"
 * Created by Administrator on 2018/6/30.
 */

public class Cell {
    public int value;//
    public int flag;//标记


    public void clear() {
        value = VALUE_EMPTY;
        flag = FLAG_COVER;
    }

    public void setValue(@ValueEmnu int value) {
        this.value = value;
    }

    public void addValue() {
        this.value++;
    }


    public int getValue() {
        return value;
    }

    public boolean isMine() {
        return value == VALUE_MINE;
    }

    public boolean isNone() {
        return value == VALUE_NONE;
    }


    public final static int FLAG_OPEN = 0;
    public final static int FLAG_COVER = 1;
    public final static int FLAG_FLAG = 1 << 1 | FLAG_COVER;//标记也算一种cover


    public final static int VALUE_NONE = -1;
    public final static int VALUE_EMPTY = 0;
    public final static int VALUE_MINE = 9;
    public final static int VALUE_ERROR_MINE = 12;
    public final static int VALUE_ERROR_BLACK = 13;

    @IntDef({VALUE_EMPTY, VALUE_NONE, VALUE_MINE, VALUE_ERROR_MINE, VALUE_ERROR_BLACK})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ValueEmnu {
    }

    public boolean isOpen() {
        return isFlagValue(FLAG_OPEN);
    }


    /***
     * 切换标记和cover
     */
    public void toggleCoverFlag() {
        if (!isOpen()) {
            if (isCover()) {
                flag = FLAG_FLAG;
            } else {
                flag = FLAG_COVER;
            }
        }
    }

    public boolean isCover() {
        return isFlagValue(FLAG_COVER);
    }

    private boolean isFlagValue(int var) {
        return (flag & var) == var;
    }

    public boolean isFlag() {
        return isFlagValue(FLAG_FLAG);
    }

    /***
     * 获取绘制的图片
     * @return
     */
    public Bitmap getDrawingBitmap(IMineUIProvider uiProvider) {
        switch (flag) {
            case FLAG_OPEN:
                return uiProvider.getBitmapByNumber(value);
            case FLAG_COVER:
                return uiProvider.getBitmapCover();
            case FLAG_FLAG:
                return uiProvider.getBitmapFlag();
        }
        return null;
    }


//    public int getValue(){
//        return var-
//    }

}
