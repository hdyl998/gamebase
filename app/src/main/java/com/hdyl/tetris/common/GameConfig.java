package com.hdyl.tetris.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.hdyl.baselib.base.App;

/**
 * Created by Administrator on 2017/5/14.
 */

public class GameConfig {
    public boolean isBgMusic = true;//背景音乐
    public boolean isSoundEffect = true;//音效

    public boolean isNishi = true;//是否方向是逆时针

    static GameConfig gameConfig;

    public boolean isBgMusic() {
        return isBgMusic;
    }

    public boolean isSoundEffect() {
        return isSoundEffect;
    }


    public boolean isNishi() {
        return isNishi;
    }

    public void setNishi(boolean nishi) {
        isNishi = nishi;
    }

    public static GameConfig getInstance() {
        if (gameConfig == null) {
            gameConfig = read();
        }
        return gameConfig;
    }


    public static void save() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_CACHE_CONFIG, JSON.toJSONString(getInstance()));
        editor.commit();
    }


    public static GameConfig read() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        String string = settings.getString(KEY_CACHE_CONFIG, null);
        if (string != null) {
            try {
                return JSON.parseObject(string, GameConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new GameConfig();
    }

    public final static String KEY_CACHE_CONFIG = "gameconfig";
}
