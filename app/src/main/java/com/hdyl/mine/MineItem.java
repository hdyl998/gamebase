package com.hdyl.mine;

import com.hdyl.mine.stage.StageList;

import java.io.Serializable;

/**
 * Date:2017/10/27 17:31
 * Author:liugd
 * Modification:
 **/


public class MineItem implements Serializable {

    public int width;
    public int height;
    public int mineNum;


    public boolean isList = true;

    public MineItem(StageList.StageItem item) {
        this.width = item.width;
        this.height = item.height;
        this.mineNum = item.mineNum;
    }

}
