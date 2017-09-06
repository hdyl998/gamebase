package com.hdyl.m2048;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyl.mine.R;
import com.umeng.analytics.MobclickAgent;

public class Main2048Activity extends AppCompatActivity implements View.OnClickListener, GameLogic.OnGameStateChangedLinstener {

    GameView gameView;
    GameLogic gameLogic;
    TextView tvStep, tvScore, tvAddScore, tvMode, tvUndo, tvHighScore, tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2048);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("2048之那些年");
        setSupportActionBar(toolbar);


//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setLogo(R.drawable.logo_2048);


        //toolbar的menu点击事件的监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_new_game:
                        new AlertDialog.Builder(Main2048Activity.this).setTitle("确认要新的开始?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        gameLogic.setGameInitVar(0);
                                        gameLogic.newGame();
                                        MobclickAgent.onEvent(Main2048Activity.this, "新游戏");
                                    }
                                })
                                .setNegativeButton("取消", null).create().show();
                        break;
                    case R.id.action_crazy_2048:
                        new AlertDialog.Builder(Main2048Activity.this).setTitle("确认要开始新的游戏--疯狂2048(新游戏)?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        gameLogic.setGameInitVar(2048);
                                        gameLogic.newGame();
                                        MobclickAgent.onEvent(Main2048Activity.this, "新游戏2048");
                                    }
                                })
                                .setNegativeButton("取消", null).create().show();
                        break;
                    case R.id.action_zhuanzhi:
                        MobclickAgent.onEvent(Main2048Activity.this, "转置");
                        Dialog dialog = new AlertDialog.Builder(Main2048Activity.this).setTitle("转置设置")
                                .setView(R.layout.dialog_zhuanzhi)
                                .setPositiveButton("确定", null)
                                .create();
                        View.OnClickListener clickListener = new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.button_leftright:
                                        gameLogic.leftRightZhuanzhi();
                                        break;
                                    case R.id.button_updown:
                                        gameLogic.upDownZhuanzhi();
                                        break;
                                    case R.id.button_xy:
                                        gameLogic.xyZhuangzhi();
                                        break;
                                    case R.id.button_all:
                                        gameLogic.allZhuanzhi();
                                        break;
                                }
                                gameView.invalidate();
                            }
                        };
                        dialog.findViewById(R.id.button_leftright).setOnClickListener(clickListener);
                        dialog.findViewById(R.id.button_updown).setOnClickListener(clickListener);
                        dialog.findViewById(R.id.button_xy).setOnClickListener(clickListener);
                        dialog.findViewById(R.id.button_all).setOnClickListener(clickListener);
                        dialog.show();
                        break;
                    case R.id.action_exit:
                        finish();
                        MobclickAgent.onEvent(Main2048Activity.this, "退出");
                        break;
                    case R.id.action_count://游戏统计
                        new AlertDialog.Builder(Main2048Activity.this).setTitle("本局统计")
                                .setMessage(gameLogic.getGameData().getCountString())
                                .setPositiveButton("确定", null)
                                .create().show();

                        break;
                }
                return true;
            }

        });


        gameView = (GameView) findViewById(R.id.gameView);
        gameLogic = gameView.logic;

        tvStep = (TextView) findViewById(R.id.tv_step);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvAddScore = (TextView) findViewById(R.id.tv_addscore);
        tvMode = (TextView) findViewById(R.id.btn_mode);

        tvMode.setText(GameConfig.getInstance().getModeName());
        tvMode.setOnClickListener(this);

        tvUndo = (TextView) findViewById(R.id.btn_all_back);
        tvUndo.setOnClickListener(this);

        tvHighScore = (TextView) findViewById(R.id.tv_high_score);
        tvTime = (TextView) findViewById(R.id.tv_time);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);//加载menu文件到布局
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mode:
                MobclickAgent.onEvent(this, "模式");
                GameConfig.getInstance().exChangeMode();
                tvMode.setText(GameConfig.getInstance().getModeName());
                gameView.invalidate();
                return;
            case R.id.btn_all_back:
                MobclickAgent.onEvent(this, "撤销");
                gameLogic.undo();
                tvUndo.setEnabled(gameLogic.isStackEmpty() == false);
                return;
        }
    }


    @Override
    public void onGameOver() {
        Toast.makeText(this, "GAMEOVER", Toast.LENGTH_SHORT).show();
    }

    Animation animator;

    @Override
    public void onGameScoreChanged(int addScore, int endScore, int hightScore) {
        tvScore.setText(endScore + "");
        tvHighScore.setText(hightScore + "");
        if (addScore != 0) {
            if (addScore >= 64) {
                tvAddScore.setTextColor(Color.RED);
            } else {
                tvAddScore.setTextColor(Color.WHITE);
            }
            tvAddScore.setText("+" + addScore);
            if (animator == null) {
                animator = AnimationUtils.loadAnimation(this, R.anim.anim_up_scalxy);
                animator.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvAddScore.setText("");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
            tvAddScore.clearAnimation();
            tvAddScore.startAnimation(animator);
        } else {
            tvAddScore.setText("");
        }

    }

    @Override
    public void onGameStepChanged(int endStep) {
        tvStep.setText(endStep + "");
        gameView.invalidate();
        tvUndo.setEnabled(gameLogic.isStackEmpty() == false);
    }


    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        MobclickAgent.onPause(this);
        gameLogic.save();

        stopRun();
    }

    //开时计时，对游戏时长进行统计
    private void startRun() {
        tvTime.setText(getTimeString(gameLogic.getGameData().getTime()));
        gameView.removeCallbacks(runnable);
        gameView.postDelayed(runnable, 1000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            gameLogic.getGameData().addTime();
            tvTime.setText(getTimeString(gameLogic.getGameData().getTime()));
            gameView.postDelayed(runnable, 1000);
        }
    };


    public String getTimeString(long ttime) {
        long daysuuu, hoursuuu, minutesuuu, secondsuuu;
//        ttime = ttime / 1000;
        String daysT = "", restT = "";
        daysuuu = ((ttime) / 86400);
        hoursuuu = ((ttime) / 3600) - (daysuuu * 24);
        minutesuuu = ((ttime) / 60) - (daysuuu * 1440) - (hoursuuu * 60);
        secondsuuu = (ttime) % 60;
        if (daysuuu == 1) daysT = String.format("%d day ", daysuuu);
        if (daysuuu > 1) daysT = String.format("%d days ", daysuuu);
        restT = String.format("%02d:%02d:%02d", hoursuuu, minutesuuu, secondsuuu);
        System.out.println("" + ttime);
        return daysT + restT;
    }

    //停止计时，对游戏时长进行统计
    private void stopRun() {
        gameView.removeCallbacks(runnable);
    }


    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onPageStart(getClass().getSimpleName());
        MobclickAgent.onResume(this);

        gameLogic.read();
        tvUndo.setEnabled(gameLogic.isStackEmpty() == false);

        startRun();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //Do nothing
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            gameLogic.push(GameLogic.GAME_DIRECTION_DOWN);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            gameLogic.push(GameLogic.GAME_DIRECTION_UP);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            gameLogic.push(GameLogic.GAME_DIRECTION_LEFT);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            gameLogic.push(GameLogic.GAME_DIRECTION_RIGHT);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
