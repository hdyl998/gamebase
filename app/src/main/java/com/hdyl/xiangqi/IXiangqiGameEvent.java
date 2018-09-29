package com.hdyl.xiangqi;

/**
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public interface IXiangqiGameEvent {

    void onGameOver();//游戏结束

    void invalidate();//绘制当前面版

    void onNewGame();

    void onGamePause();

    void onGamePauseResume();
}
