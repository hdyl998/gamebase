package com.hdyl.banghujichong;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hdyl.mine.R;

/**
 * <p>Created by Administrator on 2018/9/28.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class BhjcActivity extends AppCompatActivity implements BhjcLogic.IOnGameEvent, View.OnClickListener {
    BhjcView gameView;
    BhjcLogic bhjcLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhjc);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name_tetris);
        setSupportActionBar(toolbar);

        gameView = (BhjcView) findViewById(R.id.gameView);


        bhjcLogic = gameView.getBhjcLogic();
        findViewById(R.id.btnNewGame).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewGame:
                bhjcLogic.newGame();
                break;
        }

    }

    @Override
    public void onGameOver() {

    }


    @Override
    public void invalidate() {
        gameView.invalidate();
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
