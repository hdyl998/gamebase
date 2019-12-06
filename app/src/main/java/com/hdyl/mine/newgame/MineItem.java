package com.hdyl.mine.newgame;

import android.graphics.Bitmap;

import com.hdyl.mine.game.MineSetting;
import com.hdyl.mine.newgame.ui.IMineUIProvider;
import com.hdyl.mine.newgame.ui.MineUiProviderManager;

public class MineItem {


    public final static int NUM_NONE = -1;
    public static final int NUM_EMPTY = 0;//数字空
    public static final int NUM_MINE = 9;//数字雷

    public static final int MASK_NUM = 0xF;//占了4位,数字0-9


    public static final int SHIFT_COVER = 5;
    public static final int SHIFT_ERROR = 7;

    public static final int FUN_COVER = 1 << SHIFT_COVER;//覆盖类型Empty
    public static final int FUN_COVER_FLAG = 2 << SHIFT_COVER;//覆盖类型旗帜
    public static final int FUN_COVER_QUESTION = 3 << SHIFT_COVER;//覆盖类型问号

    public static final int MASK_FUN_COVER = FUN_COVER | FUN_COVER_FLAG | FUN_COVER_QUESTION;//占了2位

    public static final int FUN_ERROR = 1 << SHIFT_ERROR;//是否是错误


    private int value;

    public int getNum() {
        return value & MASK_NUM;
    }


    public boolean isError() {
        return isVar(FUN_ERROR);
    }

    public boolean isCover() {
        return getCoverValue() != 0;
    }

    public void printCoverInfo() {
        if (isCover()) {
            if (isCoverFlag()) {
                System.out.println("coverFlag");
            } else {
                System.out.println("coverQuestion");
            }
        } else {
            System.out.println("uncover");
        }
    }


    private int getCoverValue() {
        return value & MASK_FUN_COVER;
    }


    public static boolean isQuestion = true;


    public void setError() {
        setFlag(FUN_ERROR, FUN_ERROR);
    }


    public void clear() {
        value = 0;
    }

    public boolean isCoverFlag() {
        return getCoverValue() == FUN_COVER_FLAG;
    }

    public boolean isCoverQuestion() {
        return getCoverValue() == FUN_COVER_QUESTION;
    }

    public void setCover() {
        setFlag(FUN_COVER, MASK_FUN_COVER);
    }

    public void openCover() {
        if (isCover()) {
            setFlag(0, MASK_FUN_COVER);
            if (isMine()) {
                setError();
            }
        }
    }


    public void toggleCover() {
        if (isCover()) {
            int coverVar = getCoverValue();
            if (isQuestion) {
                switch (coverVar) {
                    case FUN_COVER:
                        setFlag(FUN_COVER_FLAG, MASK_FUN_COVER);
                        break;
                    case FUN_COVER_FLAG:
                        setFlag(FUN_COVER_QUESTION, MASK_FUN_COVER);
                        break;
                    case FUN_COVER_QUESTION:
                        setFlag(FUN_COVER, MASK_FUN_COVER);
                        break;
                }
            } else {
                if (coverVar == FUN_COVER) {
                    setFlag(FUN_COVER_FLAG, MASK_FUN_COVER);
                } else {
                    setFlag(FUN_COVER, MASK_FUN_COVER);
                }
            }
        }
    }

    private void clearFlag(int mask) {
        value = (value & ~mask);
    }


    private void setFlag(int flags, int mask) {
        value = (value & ~mask) | (flags & mask);
    }


    private boolean isVar(int var) {
        return (value & var) == var;
    }

    public boolean isMine() {
        return getNum() == NUM_MINE;
    }


    public boolean isEmpty() {
        return getNum() == NUM_EMPTY;
    }

    public Bitmap getBitmapShowing() {
        IMineUIProvider provider = MineSetting.getInstance().getUiProvider();
        if (isError()) {
            if (isMine()) {
                return provider.getBitmapErrorMine();
            }
            return provider.getBitmapErrorBlack();
        }
        switch (getCoverValue()) {
            case 0:
                return provider.getBitmapByNumber(getNum());
            case FUN_COVER:
                return provider.getBitmapCover();
            case FUN_COVER_FLAG:
                return provider.getBitmapCoverFlag();
            case FUN_COVER_QUESTION:
                return provider.getBitmapCoverQuestion();
        }
        return provider.getBitmapByNumber(getNum());
    }

}
