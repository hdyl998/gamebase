package com.hdyl.mine.newgame;

import android.view.View;

import com.hdyl.mine.newgame.ui.IMineUIProvider;

public class MineCellUtils {


    public final static int COVER_TYPE_NONE = 0;
    public final static int COVER_TYPE_COVER = 1;
    public final static int COVER_TYPE_FLAG = 2;
    public final static int COVER_TYPE_QUESTION = 3;

    public final static int COVER_ERROR = 9;
    public final static int COVER_SHIFT = 10;


    public final static int FUN_ERROR = 1 << COVER_ERROR;//是否错了
    public final static int FUN_FLAG = COVER_TYPE_FLAG << COVER_SHIFT;//旗帜
    public final static int FUN_QUESTION = COVER_TYPE_QUESTION << COVER_SHIFT;//问号


    public final static int VALUE_EMPTY = 0;
    //之间还有数字。。1，2，3，4，5，6，7，8
    public final static int VALUE_MINE = 9;

    public final static int VALUE_MASK = 0xF;//8位

    public final static int COVER_MASK = 0B11 << COVER_SHIFT;//两位

    /***
     * 获得覆盖物的类型
     * @param var
     * @return
     */
    public static int getCoverType(int var) {
        return (var & COVER_MASK) >> COVER_SHIFT;
    }

    /***
     * 是否覆盖
     * @param var
     * @return
     */
    public static boolean isCover(int var) {
        return getCoverType(var) != COVER_TYPE_NONE;
    }

    /***
     * 是否打开的
     * @param var
     * @return
     */
    public static boolean isOpen(int var) {
        return getCoverType(var) == COVER_TYPE_NONE;
    }

    /***
     * 是否点错了
     * @param var
     * @return
     */
    public static boolean isError(int var) {
        return (var & FUN_ERROR) != 0;
    }

//    /***
//     * 是否是标记
//     * @param var
//     * @return
//     */
//    public static boolean isFlag(int var) {
//        return (var & FUN_FLAG) != 0;
//    }


    public static int toggleCover(int var) {
        int coverType = getCoverType(var);
        switch (coverType) {
            case COVER_TYPE_COVER:
                var = setFlag(var, FUN_FLAG, COVER_MASK);
//                var=(var&~FUN_FLAG)|FUN_FLAG;//(mViewFlags & ~mask) | (flags & mask);
                break;
            case COVER_TYPE_NONE:
                break;
            case COVER_TYPE_FLAG:
                var = clearFlag(var, COVER_MASK);
                break;
        }
        return var;
    }

    private static int clearFlag(int var, int mask) {
        return (var & ~mask);
    }


    private static int setFlag(int var, int flags, int mask) {
        return (var & ~mask) | (flags & mask);
    }


    public static int getBitmapIndex(int var) {
        //0-9
        int num = var & VALUE_MASK;
        int coverType = getCoverType(var);
        if (isError(var)) {
            if (coverType == COVER_TYPE_FLAG) {
                return IMineUIProvider.ID_ERROR_MINE;
            }
            return IMineUIProvider.ID_ERROR_BLACK;
        }
        switch (coverType) {
            case COVER_TYPE_NONE:
                return num;
            case COVER_TYPE_COVER:
                return IMineUIProvider.ID_COVER;
            case COVER_TYPE_FLAG:
                return IMineUIProvider.ID_FLAG;
            case COVER_TYPE_QUESTION:
                return IMineUIProvider.ID_QUESTION;
        }
        //throw new RuntimeException();
        return num;
    }
}
