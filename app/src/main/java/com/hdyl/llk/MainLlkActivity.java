package com.hdyl.llk;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyl.llk.base.BaseActivity;
import com.hdyl.llk.sound.BackMediaPlayer;
import com.hdyl.llk.sound.SoundPlayer;
import com.hdyl.llk.utils.Tools;
import com.hdyl.mine.R;
import com.hdyl.mine.tools.ShareCacheUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MainLlkActivity extends BaseActivity {

	GameViewLLK gameViewLLK;
	TextView levelTextView, scoreTextView, highScoreTextView;
	ViewGroup view;

	boolean isMusic = true;
	TextView imageView;

	BackMediaPlayer backMediaPlayer;

	TextView tvLeftTime;
	TextView tvRefreshNum;

	// 新游戏
	public void newGame() {
		gameViewLLK.newGame();



		if (isMusic) {
			backMediaPlayer.changeAndPlayMusic();
		}
	}

	public void nextLevel() {
		gameViewLLK.mLevel++;
		gameViewLLK.newGame();
	}

	public void setTvRefreshNum() {
		tvRefreshNum.setText(gameViewLLK.mRefreshTimes + "");
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
			case R.id.btn_new:
				newGame();
				break;

			case R.id.btn_end:
				finish();
				break;
			case R.id.btn_rand:
				SoundPlayer.playSound(SoundPlayer.ID_REFRESH);
				if (gameViewLLK.mRefreshTimes > 0) {
					gameViewLLK.mRefreshTimes--;
					setTvRefreshNum();
					gameViewLLK.change();
					gameViewLLK.invalidate();
				} else {
					SoundPlayer.playSound(SoundPlayer.ID_TOOL_ERROR);
					arg0.clearAnimation();
					Animation aUtils = AnimationUtils.loadAnimation(this, R.anim.shake);
					arg0.startAnimation(aUtils);
				}
				break;
			case R.id.btn_voice:
				isMusic = !isMusic;
				SoundPlayer.setSoundSt(isMusic);
				backMediaPlayer.setMusicSt(isMusic);
				Tools.setTextViewDrawable(imageView, isMusic ? R.drawable.ic_voice : R.drawable.ic_voice_no, 1);
				if (isMusic) {
					backMediaPlayer.changeAndPlayMusic();
				} else {
					SoundPlayer.playSound(SoundPlayer.ID_PREFRECT);
				}
				String str = isMusic ? "" : "aa";
				ShareCacheUtil.putString(this, "music", str);
				break;
		}
	}

	public void setScore() {
		scoreTextView.setText("" + gameViewLLK.mScore);
	}

	public void setLevel() {
		levelTextView.setText("关卡: " + (gameViewLLK.mLevel + 1));
	}

	public void setHighScore(int score) {
		highScoreTextView.setText("最佳: " + score);
	}

	public void makeText(String string) {
		Toast.makeText(this, string, 0).show();
	}

	Animation upAnimation;
	TextView textAdd;

	public void transUp(int addScore) {// 慢慢向上然后消失
		textAdd.clearAnimation();
		textAdd.setVisibility(View.VISIBLE);
		textAdd.setText("+" + addScore);
		textAdd.startAnimation(upAnimation);
	}

	@Override
	protected void onPause() {
		backMediaPlayer.pauseMusic();
		isPause = true;
		super.onPause();
	}

	@Override
	protected void onResume() {
		backMediaPlayer.startMusic();
		isPause = false;
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		backMediaPlayer.relaseMusic();
		if (timer != null) {
			timer.cancel();
		}
		super.onDestroy();
	}

	@Override
	protected void initData() {
		upAnimation = AnimationUtils.loadAnimation(this, R.anim.text_up);
		gameViewLLK = (GameViewLLK) findViewById(R.id.gameViewLLK1);
		levelTextView = (TextView) findViewById(R.id.tv_level);
		scoreTextView = (TextView) findViewById(R.id.tv_score);
		highScoreTextView = (TextView) findViewById(R.id.tv_high_score);

		String str = ShareCacheUtil.getString(this, "music");
		if (str == null || str.length() == 0)
			isMusic = true;
		else {
			isMusic = false;
		}
		backMediaPlayer = new BackMediaPlayer(this, isMusic);
		SoundPlayer.setSoundSt(isMusic);
		imageView = (TextView) findViewById(R.id.btn_voice);

		Tools.setTextViewDrawable(imageView, isMusic ? R.drawable.ic_voice : R.drawable.ic_voice_no, 1);

		textAdd = (TextView) findViewById(R.id.tv_add_score);
		view = (ViewGroup) findViewById(R.id.main);
		int resid[] = { R.id.btn_end, R.id.btn_new, R.id.btn_rand };
		for (int i : resid)
			findViewById(i).setOnClickListener(this);
		imageView.setOnClickListener(this);
		upAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				textAdd.setVisibility(View.GONE);
			}
		});
		int level = getIntent().getExtras().getInt("position");
		gameViewLLK.setLevel(level);

		seekBar = (SeekBar) findViewById(R.id.seekBar_Time);

		seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.tiao));
		seekBar.setOnTouchListener(new OnTouchListener() {// 不响应Touch事件
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				return true;
			}
		});
		startTimer();
		tvLeftTime = (TextView) findViewById(R.id.tv_time_left);

		tvRefreshNum = (TextView) findViewById(R.id.textRefreshNum);
	}

	public void startTimer() {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (!isPause && gameViewLLK.isInGame) {
					handler.sendEmptyMessage(0);
				}
			}
		}, 1000, 1000);// 计时线程每秒种计一次时
	}

	Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			if (gameViewLLK.leftTime >= 1) {
				gameViewLLK.leftTime--;
				setProgress();
				if (gameViewLLK.leftTime == 0) {
					gameViewLLK.gameOver();
				} else if (gameViewLLK.leftTime < 6) {
					SoundPlayer.playSound(SoundPlayer.ID_LESS_TIME);
				}
			} else {
				gameViewLLK.gameOver();
			}
		};
	};
	private Timer timer;
	private boolean isPause = false;

	private SeekBar seekBar;

	public void setLeftTimeMax(int var) {
		seekBar.setProgress(var);
		seekBar.setMax(var);
		tvLeftTime.setText(var + "");
	}

	public void setProgress() {
		seekBar.setProgress(gameViewLLK.leftTime);
		tvLeftTime.setText(gameViewLLK.leftTime + "");
	}

	@Override
	protected int setView() {
		return R.layout.activity_main_llk;
	}

	@Override
	protected String getPageName() {
		return "首页";
	}

}
