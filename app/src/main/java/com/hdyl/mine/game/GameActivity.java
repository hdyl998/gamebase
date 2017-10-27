package com.hdyl.mine.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hdyl.mine.MineItem;
import com.hdyl.mine.R;
import com.hdyl.mine.base.MineBaseActivity;
import com.hdyl.mine.base.LoadingDialog;
import com.hdyl.mine.tools.MySharepreferences;
import com.hdyl.mine.tools.Tools;
import com.hdyl.mine.top.TopActivity;
import com.hdyl.mine.top.TopObject;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends MineBaseActivity implements OnClickListener {


    public static void launch(Activity context, MineItem item, int code) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra("data", item);
        context.startActivityForResult(intent, code);
    }

    ImageView imageView2;
    GameView gameView;
    TextView textViewMine, textViewTime, textViewLevel, textViewFlag, textViewPercent, textViewBest;
    Timer timer;
    MineItem mineItem;
    int time = 0;
    int AIleftTime = 0;// AI剩余的时间
    final int AI_COSTS_TIME = 200; // 20 X10 即20秒

    int highScore = 100000;
    ShoulueTuView shoulueTuView;

    RelativeLayout relativeLayout, shoulueTuView1;
    SoundPool soundPool;
    int musicID;

    boolean isON;

    ScrollView scrollView1;
    HorizontalScrollView scrollView2;

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 新游戏
     */
    public void newGame() {
        isPause = false;
        gameView.initMap();
        gameView.requestLayout();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.textViewTool:// AI
                Tools.setDisableForAWhile(arg0);
                if (!gameView.isInGame()) {// 没有在游戏中，直接返回
                    return;
                }
                if (AIleftTime == 0) {
                    gameView.aiCalc();
                    AIleftTime = AI_COSTS_TIME;
                } else {
                    String left = String.format("%.1f", AIleftTime / 10f);
                    LoadingDialog dialog = new LoadingDialog(mContext, "AI技能尚未冷却~\n还需 $秒".replace("$", left));
                    dialog.show();
                }
                break;
            case R.id.textViewFlag:
                gameView.isFlag = !gameView.isFlag;
                initBtn();
                MySharepreferences.putBoolean(this, "aa", "isFlag", gameView.isFlag);
                break;
            case R.id.button1:
                newGame();
                // ToastUtils.makeTextAndShow(this, px2dip(this,
                // findViewById(R.id.ll).getWidth()) + "  " + px2dip(this,
                // findViewById(R.id.ll).getHeight()));
                break;
            case R.id.textViewBack:
                onBackPressed();
                break;
            case R.id.textViewTopbang:
                Intent intent = new Intent(this, TopActivity.class);
                intent.putExtra("aa", gameView.gameType);
                startActivity(intent);
                break;
            case R.id.suoluetu:
            case R.id.suoluetu1:// 隐藏缩略图
                shoulueTuView1.setVisibility(View.GONE);

                int nX = scrollView1.getWidth() / gameView.size;
                int nY = scrollView1.getHeight() / gameView.size;
                // Log.e("aa1",nX+"  "+nY);
                int x = (int) (gameView.size * (shoulueTuView.x - nX / 2));
                int y = (int) (gameView.size * (shoulueTuView.y - nY / 2));

                // Log.e("aa1", gameView.getWidth()+"  "+scrollView1.getWidth());

                // 满屏摆的格子数

                // Log.e("aa", scrollView1.getHeight()+"  "+scrollView1.getWidth());

                scrollView1.scrollTo(0, y/* +scrollView1.getHeight()/2 */);// 竖直
                scrollView2.scrollTo(x/* +scrollView1.getWidth()/2 */, 0);// 水平
                break;
            case R.id.textViewShuolve:// 显示缩略图
                shoulueTuView1.setVisibility(View.VISIBLE);
                // Log.e("aaaa", shoulueTuView.fullXCount + "  " +
                // shoulueTuView.fullYCount);
                shoulueTuView.setIsNeedDrawing();
                if (shoulueTuView.isFirstInit == false) {
                    shoulueTuView.isFirstInit = true;
                    shoulueTuView.fullXCount = scrollView1.getWidth() / gameView.size;
                    shoulueTuView.fullYCount = scrollView1.getHeight() / gameView.size;
                    shoulueTuView.x = scrollView2.getScrollX() / gameView.size + shoulueTuView.fullXCount / 2;
                    shoulueTuView.y = scrollView1.getScrollY() / gameView.size + shoulueTuView.fullYCount / 2;
                    // shoulueTuView.isInvate=false;//只绘制一次标志
                    shoulueTuView.initPiciture(gameView, relativeLayout.getWidth(), relativeLayout.getHeight());

                } else {
                    shoulueTuView.x = scrollView2.getScrollX() / gameView.size + shoulueTuView.fullXCount / 2;
                    shoulueTuView.y = scrollView1.getScrollY() / gameView.size + shoulueTuView.fullYCount / 2;
                    // shoulueTuView.isInvate=false;//只绘制一次标志
                    shoulueTuView.invalidate();

                }
                break;
        }
    }

    /***
     * 初始化游戏acitivity
     */
    public void initGameActivity() {
        this.initGameStateUI();
        this.cancelTimer(true);
        this.setMineNum(gameView.mineNum);
        this.setPercentText(0f);
        AIleftTime = 0;// AI可用
    }

    public void initGameStateUI() {
        int res = R.drawable.bt_start_click;
        switch (gameView.gameState) {
            case GameView.STATE_LOSE:
                res = R.drawable.ic_sad;
                break;
            case GameView.STATE_WIN:
                res = R.drawable.ic_smill;
                break;
        }
        imageView2.setImageResource(res);
    }

    public void initBtn() {

        int res = R.drawable.ic_wa;
        if (gameView.isFlag) {
            res = R.drawable.ic_flag1;
        }
        Drawable rightDrawable = getResources().getDrawable(res);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        textViewFlag.setCompoundDrawables(null, rightDrawable, null, null);
        textViewFlag.setText(gameView.isFlag ? "旗帜" : "挖开");
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            time += 1;
            textViewTime.setText("" + time / 10f);
            if (AIleftTime > 0) {
                AIleftTime--;
            }
        }
    };

    public void startTimer() {
        cancelTimer(true);

        timer = new Timer();
        time = 0;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (isPause == false) {
                    handler.sendEmptyMessage(0);
                }
            }
        }, 100, 100);
    }

    public void cancelTimer(boolean isNeedRestore) {
        if (isNeedRestore == true) {
            textViewTime.setText("0.0");
        }
        if (timer != null) {
            timer.cancel();
        }
        handler.removeMessages(0);
    }

    @Override
    protected void onDestroy() {
        cancelTimer(false);
        super.onDestroy();
    }

    /**
     * 设置雷的数量
     */
    public void setMineNum(int rest) {
        textViewMine.setText(rest + "/" + gameView.mineNum);
    }

    public void setLevelText(int level) {
        String str[] = {"初级", "中级", "高级", "专家级", "自定义", "挑战"};
        String ssString = "难度 " + str[level] + "($% #1x#2)".replace("$", String.format("%.1f", gameView.getRate() * 100));
        ssString = ssString.replace("#1", gameView.WIDTH + "");
        ssString = ssString.replace("#2", gameView.HEIGHT + "");
        textViewLevel.setText(ssString);
        int data = 0;
        if (level != 4) {
            data = TopObject.getTop(this, level);
        } else {
            data = highScore;
        }
        if (data != 100000) {
            textViewBest.setText("最佳 " + data / 10f);
        } else {
            textViewBest.setText("最佳 --");
        }
    }

    /***
     * 设置百分比数据
     *
     * @param percent
     */
    public void setPercentText(float percent) {
        textViewPercent.setText(String.format("%.2f", percent) + "%");
    }

    @Override
    public void onBackPressed() {// 连按两次退出应用

        if (shoulueTuView1.getVisibility() == View.VISIBLE) {
            shoulueTuView1.setVisibility(View.GONE);
            return;
        }

        if (dialogExit != null && gameView.gameState == GameView.STATE_IN_GAME) {

            // lastPressedTime = System.currentTimeMillis();
            isPause = true;
            dialogExit.show();
            return;

            // if (System.currentTimeMillis() - lastPressedTime > 3 * 1000) {
            //
            // }
        }
        super.onBackPressed();
    }

    private OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.button1:
                    isPause = false;
                    break;
                case R.id.button2:
                    modeItem = gameView.getModeItem();
                    int type = MySharepreferences.getInt(mContext, "aa", "settype");
                    MySharepreferences.putString(mContext, "aa", "storage" + type, JSON.toJSONString(modeItem));
                    finish();
                    break;
                case R.id.button3:
                    finish();
                    break;
            }
            dialogExit.dismiss();
        }
    };
    private OnClickListener clickListener2 = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            switch (arg0.getId()) {
                case R.id.button1:
                    if (modeItem != null) {
                        gameView.setModeItem(modeItem);
                    }
                    int type = MySharepreferences.getInt(mContext, "aa", "settype");
                    MySharepreferences.putString(mContext, "aa", "storage" + type, null);
                    break;
                case R.id.button2:
                    break;
                case R.id.button3:
                    finish();
                    break;
            }
            dialogSave.dismiss();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (dialogExit != null && isPause == false && gameView.gameState == GameView.STATE_IN_GAME) {
            isPause = true;
            gameView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogExit.show();
                }
            }, 400);
            return;
        }
    }

    public void playSound() {
        if (isON) {
            AudioManager mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
            float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = streamVolumeCurrent / streamVolumeMax;
            soundPool.play(musicID, volume, volume, 1, 0, 1.0f);
            // 参数：1、Map中取值 2、左声道 3、右声道 4、优先级:默认为1 5、重播次数 6、播放速度
        }
    }

    LoadingDialog dialogExit, dialogSave;
    boolean isPause = false;

    ModeItem modeItem;

    @SuppressWarnings("deprecation")
    @Override
    public void initViews() {

        mineItem = (MineItem) mContext.getIntent().getSerializableExtra("data");
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        scrollView2 = (HorizontalScrollView) findViewById(R.id.scrollView2);

        gameView = (GameView) findViewById(R.id.gameView);
        gameView.init(mineItem);
        imageView2 = (ImageView) findViewById(R.id.button1);
        imageView2.setOnClickListener(this);

        textViewMine = (TextView) findViewById(R.id.textViewMine);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewLevel = (TextView) findViewById(R.id.TextViewLevel);

        textViewPercent = (TextView) findViewById(R.id.textViewPencent);
        textViewFlag = (TextView) findViewById(R.id.textViewFlag);

        textViewBest = (TextView) findViewById(R.id.TextViewBest);

        textViewFlag.setOnClickListener(this);

        findViewById(R.id.textViewBack).setOnClickListener(this);

        findViewById(R.id.textViewTopbang).setOnClickListener(this);
        findViewById(R.id.textViewShuolve).setOnClickListener(this);

        findViewById(R.id.textViewTool).setOnClickListener(this);

        shoulueTuView = (ShoulueTuView) findViewById(R.id.suoluetu);

        shoulueTuView.setOnClick(this);

        shoulueTuView1 = (RelativeLayout) findViewById(R.id.suoluetu1);
        shoulueTuView1.setOnClickListener(this);
        shoulueTuView1.setBackgroundDrawable(getBgDrawable());

        relativeLayout = (RelativeLayout) findViewById(R.id.ll);
        initBtn();

        if (mineItem == null) {
            dialogExit = new LoadingDialog(this, "暂停中...", "继续游戏", "存档退出", "退出", clickListener);
            int type = MySharepreferences.getInt(this, "aa", "settype");

            String string = MySharepreferences.getString(mContext, "aa", "storage" + type);
            if (string != null) {
                dialogSave = new LoadingDialog(this, "发现存档...是否继续?", "继续游戏", "新游戏", "退出", clickListener2);
                try {
                    modeItem = JSON.parseObject(string, ModeItem.class);
                    dialogSave.show();
                } catch (Exception e) {
                    modeItem = null;
                }
            }
        } else {
            findViewById(R.id.textViewTopbang).setVisibility(View.GONE);
            findViewById(R.id.textViewTool).setVisibility(View.GONE);
//            this.setResult(RESULT_OK);
        }
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 100);
        musicID = soundPool.load(this, R.raw.effecttick, 1);// 参数详解(上下问对象,需要播放的音频的ID,

        isON = MySharepreferences.getInt(this, "aa", "sound") == 0;// 0表示打开的

        String string3 = MySharepreferences.getString(mContext, "aa", "isshowaidialog");
        if (string3 == null) {
            MySharepreferences.putString(mContext, "aa", "isshowaidialog", "a");
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.ic_ai);
            new AlertDialog.Builder(this).setTitle("AI使用图解").setView(imageView).setNegativeButton("我知道了", null).show();
        }
        String string2 = MySharepreferences.getString(mContext, "aa", "isshowdialog");
        if (string2 == null) {
            MySharepreferences.putString(mContext, "aa", "isshowdialog", "a");
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.test);
            new AlertDialog.Builder(this).setTitle("游戏使用图解").setView(imageView).setNegativeButton("我知道了", null).show();
        }

    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_game;
    }

    @Override
    protected String getPageName() {
        return "游戏首页";
    }
}
