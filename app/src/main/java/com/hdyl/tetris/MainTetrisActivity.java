package com.hdyl.tetris;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hdyl.baselib.utils.ToastUtils;
import com.hdyl.mine.R;
import com.hdyl.tetris.shape.TetrisShape;
import com.hdyl.tetris.shape.TetrisShapeFactory;
import com.hdyl.tetris.sound.SoundManager;

public class MainTetrisActivity extends AppCompatActivity implements GameBoard.OnGameEvent, View.OnClickListener {

    GameView gameView;
    TextView tvScore, tvScoreHigh, tvLevel;

    NextShapeView nextShapeView;
    GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tetris);

        gameView = (GameView) findViewById(R.id.gameView);

        gameBoard = gameView.getGameBoard();

        nextShapeView = (NextShapeView) findViewById(R.id.nextShapeView);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvScoreHigh = (TextView) findViewById(R.id.tv_score_high);
        tvLevel = (TextView) findViewById(R.id.tv_level);
        findViewById(R.id.buttonDown).setOnClickListener(this);
        findViewById(R.id.buttonLeft).setOnClickListener(this);
        findViewById(R.id.buttonRight).setOnClickListener(this);
        findViewById(R.id.buttonNewGame).setOnClickListener(this);
        findViewById(R.id.buttonRotate).setOnClickListener(this);
        findViewById(R.id.buttonFastDown).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRun();
//        SoundManager.getInstance().relaseAll();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLeft:
                gameBoard.move(GameBoard.DIRECTION_LEFT);

                break;
            case R.id.buttonRight:
                gameBoard.move(GameBoard.DIRECTION_RIGHT);
                break;
            case R.id.buttonDown:
                gameBoard.move(GameBoard.DIRECTION_DOWN);
                break;
            case R.id.buttonNewGame:
                gameBoard.newGame();
                break;
            case R.id.buttonRotate:
                gameBoard.move(GameBoard.DIRECTION_ROTATE);
                break;
            case R.id.buttonFastDown:
                gameBoard.move(GameBoard.DIRECTION_FAST_DOWN);
                break;
        }
    }

    @Override
    public void onScoreChanged(int curScore, int addScore, int highScore, int curLine) {
        tvScore.setText(curScore + "");
        tvScoreHigh.setText(highScore + "");

    }

    @Override
    public void onGameOver() {
        //停止时间跳动
        stopRun();
        ToastUtils.makeTextAndShow("GameOver");
    }

    @Override
    public void invalidateGameBoard() {
        gameView.invalidate();
    }

    @Override
    public void invalidateNextBoard() {
        nextShapeView.setTetrisShape(gameBoard.getNextShape());
        startRun();
    }

    @Override
    public void onNewGame() {
        handler.removeCallbacks(runnable);
        startRun();
    }


    Handler handler = new Handler();


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIME_DELAY);
            gameBoard.downALine();
        }
    };

    final static int TIME_DELAY = 1000;

    private void startRun() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, TIME_DELAY);
    }

    private void stopRun() {
        handler.removeCallbacks(null);
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundManager.getInstance().releaseBgMusic();
        gameBoard.save();
        stopRun();
    }


    @Override
    protected void onResume() {
        super.onResume();
        TetrisShape nextShape = TetrisShapeFactory.createRandomShape();
        System.out.println(JSON.toJSONString(nextShape));
        SoundManager.getInstance().playPlayingBgMusic();
        gameBoard.read();
        if (gameBoard.isGamePlaying()) {
            startRun();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //Do nothing
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            gameBoard.move(GameBoard.DIRECTION_FAST_DOWN);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//            gameBoard.move(GameBoard.);
            gameBoard.move(GameBoard.DIRECTION_ROTATE);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            gameBoard.move(GameBoard.DIRECTION_LEFT);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            gameBoard.move(GameBoard.DIRECTION_RIGHT);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
