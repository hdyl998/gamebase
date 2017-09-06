package com.hdyl.tetris.sound;

import android.content.Context;
import android.support.annotation.RawRes;

import com.hdyl.baselib.base.App;

import java.util.HashMap;

/**
 * 音乐播放器，共同的基类
 * Created by Administrator on 2017/5/14.
 */

public abstract class AbsPlayer {


    protected final Context mContext = App.getContext();
    protected final HashMap<Integer, Integer> soundMap = new HashMap<>();

    public AbsPlayer() {
        initDatas();
    }


    protected void initDatas() {
    }

    public abstract void initRawRes(int soundFlag, @RawRes int rawRes);

    public abstract void play(int soundFlag);

    public abstract void release();

}
