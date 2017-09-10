package com.hdyl.tetris;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hdyl.baselib.utils.ToastUtils;
import com.hdyl.m2048.Main2048Activity;
import com.hdyl.mine.R;
import com.hdyl.tetris.common.GameConfig;
import com.hdyl.tetris.shape.TetrisShape;
import com.hdyl.tetris.shape.TetrisShapeFactory;
import com.hdyl.tetris.sound.SoundManager;

public class MainTetrisActivity extends AppCompatActivity implements GameBoard.OnGameEvent, View.OnClickListener {

    GameView gameView;
    TextView tvScore, tvScoreHigh, tvLevel;

    NextShapeView nextShapeView;
    GameBoard gameBoard;
    Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_main_tetris);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name_tetris);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_new_game:
                        gameBoard.newGame();
                        break;
                    case R.id.action_rotate:
                        new AlertDialog.Builder(mContext).setTitle(item.getTitle())
                                .setSingleChoiceItems(new String[]{"逆时针","顺时针"}, GameConfig.getInstance().isNishi() ? 0 : 1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        GameConfig.getInstance().setNishi(which==0);
                                        GameConfig.getInstance().save();
                                    }
                                }).show();

                        break;
                    case R.id.action_anim:
                        new AlertDialog.Builder(mContext).setTitle(item.getTitle()).setMultiChoiceItems(new String[]{"下落动画","消行动画"},
                                new boolean[]{GameConfig.getInstance().isAnimDown(), GameConfig.getInstance().isAnimXiaohang()},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(which==0) {
                                            GameConfig.getInstance().isAnimDown=isChecked;
                                        }
                                        else if(which==1){
                                            GameConfig.getInstance().isAnimXiaohang=isChecked;
                                        }
                                        GameConfig.getInstance().save();
                                    }
                                }
                        ).setPositiveButton("确定",null).show();

                        break;
                    case R.id.action_sound:
                        new AlertDialog.Builder(mContext).setTitle(item.getTitle()).setMultiChoiceItems(new String[]{"音乐","音效"},
                                new boolean[]{GameConfig.getInstance().isBgMusic(), GameConfig.getInstance().isSoundEffect()},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(which==0) {
                                            GameConfig.getInstance().isBgMusic=isChecked;
                                            if(isChecked){
                                                SoundManager.getInstance().playPlayingBgMusic();
                                            }
                                            else {
                                                SoundManager.getInstance().releaseBgMusic();
                                            }
                                        }
                                        else if(which==1){
                                            GameConfig.getInstance().isSoundEffect=isChecked;
                                        }
                                        GameConfig.getInstance().save();
                                    }
                                }
                        ).setPositiveButton("确定",null).show();
                        break;
                    case R.id.action_exit:
                        finish();
                        break;
                }
                return true;
            }
        });


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
        findViewById(R.id.buttonPause).setOnClickListener(this);
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        gameBoard.doPauseGame();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tetris, menu);//加载menu文件到布局
        return true;
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
            case R.id.buttonPause:
                gameBoard.exchangePausePlayingGameState();
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
        new AlertDialog.Builder(mContext).setTitle("消息").setMessage("游戏结束")
                .setPositiveButton("确定",null).show();
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

    @Override
    public void onGamePause() {
        stopRun();
        SoundManager.getInstance().releaseBgMusic();
    }

    @Override
    public void onGamePauseResume() {
        startRun();
        SoundManager.getInstance().playPlayingBgMusic();
    }


    Handler handler = new Handler();


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIME_DELAY);
            gameBoard.downALine(1);
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
//        TetrisShape nextShape = TetrisShapeFactory.createRandomShape();
//        System.out.println(JSON.toJSONString(nextShape));
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
