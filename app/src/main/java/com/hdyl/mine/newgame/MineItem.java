package com.hdyl.mine.newgame;

/**
 * //$ git config --global http.sslverify "false"
 * Created by Administrator on 2018/6/30.
 */

public class MineItem {
    public int var;//
    public int flag;//标记

    public final static int VAR_OPEN = 0;
    public final static int VAR_COVER = 1 << 5;
    public final static int VAR_FLAG = 2 << 5;

    public final static int VAR_MASK = VAR_COVER;

    //0~12


    public boolean isCover() {
        return (VAR_MASK & var) == VAR_COVER;
    }

//    public int getValue(){
//        return var-
//    }

}
