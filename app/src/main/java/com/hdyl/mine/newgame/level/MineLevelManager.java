package com.hdyl.mine.newgame.level;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MineLevelManager {
    public final static MineLevel easyMineLevel = new MineLevel(8, 8, 10).setNote("初级");//0.15625
    public final static MineLevel normalMineLevel = new MineLevel(16, 16, 40).setNote("中级");//0.15625
    public final static MineLevel hardMineLevel = new MineLevel(16, 30, 99).setNote("高级");// 0.20625
    public final static MineLevel specialMineLevel = new MineLevel(80, 80, 1000).setNote("特级");// 0.15625
    public final static MineLevel extremeMineLevel = new MineLevel(80, 80, 1920).setNote("专家级");// 0.3

    public final static int MINE_LEVEL_EASY = 0;
    public final static int MINE_LEVEL_NORMAL = 1;
    public final static int MINE_LEVEL_HARD = 2;
    public final static int MINE_LEVEL_SPECIAL = 3;
    public final static int MINE_LEVEL_EXTREME = 4;
    public final static int MINE_LEVEL_USERDEFINE = 999;

    public static MineLevel createFromConfig(int config) {
        MineLevel provider;
        switch (config) {
            case MINE_LEVEL_EASY:
                provider = easyMineLevel;
                break;
            case MINE_LEVEL_NORMAL:
                provider = normalMineLevel;
                break;
            case MINE_LEVEL_HARD:
                provider = hardMineLevel;
                break;
            case MINE_LEVEL_SPECIAL:
                provider = specialMineLevel;
                break;
            case MINE_LEVEL_EXTREME:
                provider = extremeMineLevel;
                break;
            case MINE_LEVEL_USERDEFINE:
                provider = new UserMineLevel().setNote("自定义");
                break;
            default:
                provider = easyMineLevel;
                break;
        }
        return provider;
    }


}
