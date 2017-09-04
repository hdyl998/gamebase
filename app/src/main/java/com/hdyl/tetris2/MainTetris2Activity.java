package com.hdyl.tetris2;

import android.view.View;
import android.widget.TextView;

import com.hdyl.baselib.base.BaseActivity;
import com.hdyl.baselib.sound.SoundPlayer;
import com.hdyl.baselib.utils.ToastUtils;
import com.hdyl.mine.R;

/**
 * Created by liugd on 2017/9/4.
 */

public class MainTetris2Activity extends BaseActivity implements GameBoard.OnGameEvent {

    TextView tvScore;
    GameTetris2View gameView;
    GameBoard gameBoard = new GameBoard();
    SoundPlayer soundPlayer = new SoundPlayer();

    final int SOUND_KEY_ADD = 0;


    @Override
    public void initViews() {
        tvScore = findViewByID(R.id.tvScore);
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
        tvScore.setText(finalScore + "");
        if (addScore != 0) {
            soundPlayer.play(SOUND_KEY_ADD);
        }
    }

    @Override
    public void invalidateGameBoard() {
        gameView.invalidate();
    }

    @Override
    public void onGameOver() {
        ToastUtils.makeTextAndShow("gameover!");
    }

    @Override
    public void onNewGame() {
        ToastUtils.makeTextAndShow("新游戏!");
    }
}
