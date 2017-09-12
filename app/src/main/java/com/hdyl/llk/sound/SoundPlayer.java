package com.hdyl.llk.sound;

import android.media.AudioManager;
import android.media.SoundPool;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static boolean soundSt = true; // 音效开关

    public static final int ID_WIN = 0;
    public static final int ID_REFRESH = 1;
    public static final int ID_CLEAR = 2;
    public static final int ID_PREFRECT = 3;

    public static final int ID_SELECT = 4;

    public static final int ID_LESS_TIME = 5;

    public static final int ID_GAME_OVER = 6;

    public static final int ID_TOOL_ERROR = 7;

    // public static final int ID_TOOL_REFRESH = 3;
    // public static final int ID_TOOL_BOMB = 4;
    // public static final int ID_TOOL_DELAY = 5;
    // public static final int ID_TOOL_REMIND = 6;
    //
    //
    // public static final int ID_BOMB = 12;
    //
    // public static final int ID_TIME_COUNT = 11;

    private static Map<Integer, Integer> soundMap; // 音效资源id与加载过后的音源id的映射关系表

    static {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
        soundMap = new HashMap<Integer, Integer>();
        // 0,,,,,,,,,,,,,
        // 1,,,,,,,,,,,,,2,,,,,,,,,,,,,,,,,,3,,,,,,,,,,,,,,4,,,,,,,,,5,,,,,,,,6,,,,,,7
        int resourceArr[] = {R.raw._win, R.raw.tool_refresh, R.raw._clear, R.raw.prefect, R.raw.sel, R.raw._lesstime, R.raw._lose, R.raw._error};
        for (int i = 0; i < resourceArr.length; i++) {
            soundMap.put(i, soundPool.load(App.getContext(), resourceArr[i], 1));
        }
    }

    /**
     * 播放音效
     *
     * @param resId 音效资源id
     */
    public static void playSound(int id) {
        if (soundSt == false)
            return;

        Integer soundId = soundMap.get(id);
        if (soundId != null)
            soundPool.play(soundId, 1, 1, 1, 0, 1);
    }

    /**
     * 获得音效开关状态
     *
     * @return
     */
    public static boolean isSoundSt() {
        return soundSt;
    }

    /**
     * 设置音效开关
     *
     * @param soundSt
     */
    public static void setSoundSt(boolean soundSt) {
        SoundPlayer.soundSt = soundSt;
    }

}