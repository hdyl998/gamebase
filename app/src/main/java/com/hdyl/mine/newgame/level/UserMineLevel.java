package com.hdyl.mine.newgame.level;

import com.hdyl.mine.game.MineUtils;
import com.hdyl.mine.newgame.SpCacheUtils;

/**
 * Created by Administrator on 2018/7/8.
 */

public class UserMineLevel extends MineLevel {


    public final static String KEY_WIDTH = "width";
    public final static String KEY_HEIGHT = "height";
    public final static String KEY_NUM = "mine_num";


    public final static String FILE_NAME = "mine_cache";

    public UserMineLevel() {
        int width = SpCacheUtils.getInt(FILE_NAME, KEY_WIDTH);
        int height = SpCacheUtils.getInt(FILE_NAME, KEY_HEIGHT);
        int num = SpCacheUtils.getInt(FILE_NAME, KEY_NUM);
        int arr[] = MineUtils.checkCorrectUserDefineMineNum(width, height, num);
        width = arr[0];
        height = arr[1];
        num = arr[2];
        initLevelInfo(width, height, num);
        setNote("自定义");
    }

    public void save() {
        SpCacheUtils.putInt(FILE_NAME, KEY_WIDTH, width);
        SpCacheUtils.putInt(FILE_NAME, KEY_HEIGHT, height);
        SpCacheUtils.putInt(FILE_NAME, KEY_NUM, mineNum);
    }
}
