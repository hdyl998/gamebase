package com.hdyl.mine.newgame.ui;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MineUiProviderManager {

    public final static int MINE_UI_TYPE_XP = 0;

    public final static int MINE_UI_TYPE_WIN7 = 1;

    public static IMineUIProvider createFromConfig(int config) {
        IMineUIProvider provider;
        switch (config) {
            case MINE_UI_TYPE_XP:
                provider = new XPMineUIProvider();
                break;
            case MINE_UI_TYPE_WIN7:
                provider = new Win7MineUIProvider();
                break;
            default:
                provider = new XPMineUIProvider();
                break;
        }
        return provider;
    }
}
