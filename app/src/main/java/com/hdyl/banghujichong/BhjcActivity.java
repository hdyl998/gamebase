package com.hdyl.banghujichong;

import android.app.Activity;
import android.os.Bundle;

import com.hdyl.mine.R;

/**
 * <p>Created by Administrator on 2018/9/28.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class BhjcActivity extends Activity implements BhjcLogic.IOnGameEvent {
    BhjcView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhjc);
        gameView = (BhjcView) findViewById(R.id.gameView);

    }

    @Override
    public void onGameOver() {

    }


    @Override
    public void invalidate() {

    }

    @Override
    public void onNewGame() {

    }

    @Override
    public void onGamePause() {

    }

    @Override
    public void onGamePauseResume() {

    }
}
