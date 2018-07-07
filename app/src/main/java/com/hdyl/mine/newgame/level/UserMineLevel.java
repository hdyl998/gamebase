package com.hdyl.mine.newgame.level;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.game.MineUtils;
import com.hdyl.mine.tools.MySharepreferences;

/**
 * Created by Administrator on 2018/7/8.
 */

public class UserMineLevel extends MineLevel {


    public UserMineLevel() {
        int width = MySharepreferences.getInt(App.getContext(), "aa", "width");
        int height = MySharepreferences.getInt(App.getContext(), "aa", "height");
        int num = MySharepreferences.getInt(App.getContext(), "aa", "num");
        int arr[] = MineUtils.checkCorrectUserDefineMineNum(width, height, num);
        width = arr[0];
        height = arr[1];
        num = arr[2];
        initLevelInfo(width, height, num);
    }
}
