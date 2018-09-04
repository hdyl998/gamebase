package com.hdyl.mine;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.RawRes;

import java.util.HashMap;

/**
 * 声音播放器，播放小音乐
 * Created by liugd on 2017/1/11.
 */

public class MySoundPlayer implements SoundPool.OnLoadCompleteListener {

    /***
     * 音乐池
     */
    SoundPool soundPool;
    /***
     * 上下文
     */
    Context mContext;


    /***
     * 缓存池
     */
    HashMap<Integer, Integer> hashMap;


    public MySoundPlayer(Context mContext) {
        this.mContext = mContext;
        // 参数详解(允许同时播放的声音的最大数量,音频类型:默认为AudioManager.STREAM_MUSIC,采样率:即播放质量,
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 100);
        hashMap = new HashMap<>();
        soundPool.setOnLoadCompleteListener(this);
    }


    /***
     * 初始音乐池
     *
     * @param rawID 声音资源ID
     */
    public void initSoundPool(int soundKey, @RawRes int rawID) {
        int id = soundPool.load(mContext, rawID, 1);// 参数详解(上下问对象,需要播放的音频的ID
        hashMap.put(soundKey, id);
    }

    /***
     * 播放声音
     *
     * @param soundKey 键值
     */
    public void playSound(int soundKey) {
        this.soundKey = soundKey;
        Integer key = hashMap.get(soundKey);
        if (key != null) {

//            AudioManager mgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
//            float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
//            float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            /**
             * 播放的音量
             */
//            float volume = streamVolumeCurrent / streamVolumeMax;
            // 参数：1、Map中取值 2、左声道 3、右声道 4、优先级:默认为1 5、重播次数 6、播放速度
            soundPool.play(key, 1, 1, 1, 0, 1.0f);
        }
    }

    int soundKey = -1;

    //防止第一次加载时没有声音，当加载成功后就播放第一次没有播放成功的声音
    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        if (soundKey != -1) {
            playSound(soundKey);
        }
    }
}
