package com.hdyl.mine.game;

import com.alibaba.fastjson.annotation.JSONField;
import com.hdyl.mine.newgame.level.MineLevel;
import com.hdyl.mine.newgame.level.MineLevelManager;
import com.hdyl.mine.newgame.ui.IMineUIProvider;
import com.hdyl.mine.newgame.ui.MineUiProviderManager;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MineSetting {


    private final static MineSetting setting = new MineSetting();

    public static MineSetting getInstance() {
        return setting;
    }

    private boolean isVibrator = true;
    private boolean isSound = true;
    private int UIType = MineUiProviderManager.MINE_UI_TYPE_XP;//UI风格，XP风格,WIN7风格，其它风格
    private int levelType = MineLevelManager.MINE_LEVEL_EASY;//简单模式


    private IMineUIProvider uiProvider;

    private MineLevel mineLevel;


    @JSONField(serialize = false)
    public MineLevel getMineLevel() {
        if (mineLevel == null) {
            mineLevel = MineLevelManager.createFromConfig(levelType);
        }
        return mineLevel;
    }

    @JSONField(serialize = false)
    public IMineUIProvider getUiProvider() {
        if (uiProvider == null) {
            uiProvider = MineUiProviderManager.createFromConfig(UIType);
        }
        return uiProvider;
    }

    public boolean isVibrator() {
        return isVibrator;
    }

    public boolean isSound() {
        return isSound;
    }

    public int getUIType() {
        return UIType;
    }

    public int getLevelType() {
        return levelType;
    }


    public void setVibrator(boolean vibrator) {
        isVibrator = vibrator;
    }

    public void setUIType(int UIType) {
        if (this.UIType != UIType) {
            this.UIType = UIType;
            uiProvider = null;
        }

    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public void setSound(boolean sound) {
        isSound = sound;
    }


}
