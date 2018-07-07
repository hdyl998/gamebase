package com.hdyl.mine.newgame;

import com.hdyl.mine.tools.PermissionUtils;

/**
 * //$ git config --global http.sslverify "false"
 * Created by Administrator on 2018/6/30.
 */

public class MineItem {
    public int var;


    public final static int VAR_COVER = 1 << 5;

    public final static int VAR_MASK = VAR_COVER;


    public boolean isCover() {
        return (VAR_MASK & var) == VAR_COVER;
    }

//    public int getValue(){
//        return var-
//    }

}
