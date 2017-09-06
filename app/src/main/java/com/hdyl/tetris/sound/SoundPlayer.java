package com.hdyl.tetris.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.RawRes;

/**音效播放器
 * Created by Administrator on 2017/5/14.
 */

public class SoundPlayer extends AbsPlayer {


    SoundPool soundPool;

    @Override
    protected void initDatas() {
        soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public void initRawRes(int soundFlag, @RawRes int rawRes) {
        soundMap.put(soundFlag, soundPool.load(mContext, rawRes, 1));
    }

    public void play(int soundFlag) {
        AudioManager mgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;
        Integer soundID = soundMap.get(soundFlag);
        if (soundID != null) {
            soundPool.play(soundID, volume, volume, 1, 0, 1.0f);
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
