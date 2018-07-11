package com.hdyl.mine.newgame;

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

    //
//    public final static int VAR_MASK = VAR_COVER;
//
//    //0~12
//
//
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

//    public int getValue(){
//        return var-
//    }

}
