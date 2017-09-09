package com.hdyl.tetris2;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.hdyl.baselib.base.BaseActivity;
import com.hdyl.baselib.sound.SoundPlayer;
import com.hdyl.baselib.utils.TextViewUtils;
import com.hdyl.baselib.utils.ToastUtils;
import com.hdyl.mine.R;

/**
 * Created by liugd on 2017/9/4.
 */

public class MainTetris2Activity extends BaseActivity implements GameBoard.OnGameEvent {

    TextView tvScore;
    TextView tvHighScore;
    GameTetris2View gameView;
    GameBoard gameBoard = new GameBoard();
    SoundPlayer soundPlayer = new SoundPlayer();

    final int SOUND_KEY_ADD = 0;


    @Override
    public void initViews() {
        tvScore = findViewByID(R.id.tvScore);
        tvHighScore=findViewByID(R.id.tvHighScore);
        gameView = findViewByID(R.id.gameView);
        gameView.setGameBoard(gameBoard);
        gameBoard.setOnGameEvent(this);
        soundPlayer.initRawRes(SOUND_KEY_ADD, R.raw.delete1);
    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_main_tetris2;
    }

    @Override
    public int[] setClickIDs() {
        return new int[]{R.id.tvNewGame, R.id.tvEnd};
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNewGame:
                gameBoard.newGame();
                break;
            case R.id.tvEnd:
                mContext.finish();
                break;
        }
    }

    @Override
    public void onScoreChange(int addScore, int finalScore) {
        tvScore.setText("得分: " + finalScore);
        if (addScore >=100) {
            soundPlayer.play(SOUND_KEY_ADD);
        }
    }


    @Override
    public void onHighScore(boolean isInited,int highScore) {
        tvHighScore.setText("最高分: "+highScore);
        if(isInited==false)
        new AlertDialog.Builder(mContext).setTitle("打破记录").setMessage("最高分: "+highScore)
                .setPositiveButton("确定",null).show();
    }

    @Override
    public void invalidateGameBoard() {
        gameView.invalidate();
    }

    @Override
    public void onGameOver() {
        new AlertDialog.Builder(mContext).setTitle("消息").setMessage("游戏结束")
                .setPositiveButton("确定",null).show();
    }

    @Override
    public void onNewGame() {
    }
}
