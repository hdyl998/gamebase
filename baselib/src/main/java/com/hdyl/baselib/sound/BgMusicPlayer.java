package com.hdyl.baselib.sound;

import android.media.MediaPlayer;
import android.support.annotation.RawRes;

/**背景音乐播放器
 * Created by Administrator on 2017/5/14.
 */

public class BgMusicPlayer extends AbsPlayer {
    public MediaPlayer mediaPlayer;

    @Override
    public void initRawRes(int soundFlag, @RawRes int rawRes) {
        soundMap.put(soundFlag, rawRes);
    }

    public void play(int soundFlag) {
        Integer rawRes = soundMap.get(soundFlag);
        if (rawRes != null) {
            release();
            mediaPlayer = MediaPlayer.create(mContext, rawRes);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    //设置音乐开关
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    public void pause(){
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }


    @Override
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
