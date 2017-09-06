package com.hdyl.m2048;

import android.graphics.Color;
import android.util.SparseArray;

/**
 * Created by Administrator on 2017/4/16.
 */

public class GameColor {
    public final static SparseArray<Integer> map;
    public final static int COLOR_MAX_NUM;
    static {
        map=new SparseArray<>();
        map.put(0, Color.parseColor("#d6cdc4"));
        map.put(2, Color.parseColor("#eee4da"));
        map.put(4, Color.parseColor("#ede0c8"));
        map.put(8, Color.parseColor("#f2b179"));
        map.put(16, Color.parseColor("#f59563"));
        map.put(32, Color.parseColor("#f67c5f"));
        map.put(64, Color.parseColor("#f65e3b"));
        map.put(128, Color.parseColor("#edcf72"));
        map.put(256, Color.parseColor("#edcc61"));
        map.put(512, Color.parseColor("#edc850"));
        map.put(1024, Color.parseColor("#edc53f"));
        map.put(2048, Color.parseColor("#edc22e"));
        COLOR_MAX_NUM= Color.parseColor("#3c3a32");
    }

    public static int getValueColor(int value){
       Integer integer= map.get(value);
        if(integer==null){
           return COLOR_MAX_NUM;
        }
        return integer.intValue();
    }
}
