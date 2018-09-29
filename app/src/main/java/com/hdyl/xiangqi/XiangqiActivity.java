package com.hdyl.xiangqi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hdyl.mine.R;

/**
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class XiangqiActivity extends AppCompatActivity implements IXiangqiGameEvent {
    XiangqiView chessView;
    XiangqiLogic chessLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqi);
        chessView = (XiangqiView) findViewById(R.id.gameView);

        chessLogic = chessView.getXiangqiLogic();

        findViewById(R.id.btnNewGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessLogic.newGame();
            }
        });
    }

    @Override
    public void onGameOver() {

    }

    @Override
    public void invalidate() {
        chessView.invalidate();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XiangqiResourcesManager.onDestory();
    }
}
