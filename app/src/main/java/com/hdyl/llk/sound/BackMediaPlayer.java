package com.hdyl.llk.sound;

import java.util.Random;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.hdyl.mine.R;


public class BackMediaPlayer {

	private MediaPlayer music;

	private boolean musicSt = true; // 音乐开关
	private Context context;
	// 要使用的背景音效全加在下面的数组中
	private final int[] musicId = { R.raw.bg1_xiyangyang, R.raw.ic_jn, R.raw.bg3, R.raw.bg_songsu, R.raw.liangzhu };

	public BackMediaPlayer(Context c, boolean sw) {
		context = c;
		this.musicSt = sw;
		initMusic();
	}

	// 下面是音乐控制

	int lastid = -1;

	// 初始化音乐播放器
	private void initMusic() {
		Random random = new Random();
		int r = random.nextInt(musicId.length);
		while (lastid == r) {
			r = random.nextInt(musicId.length);
		}
		lastid = r;

		music = MediaPlayer.create(context, musicId[r]);
		music.setLooping(false);
		music.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				changeAndPlayMusic();
			}
		});
	}

	/**
	 * 暂停音乐
	 */
	public void pauseMusic() {
		if (music.isPlaying())
			music.pause();
	}

	/**
	 * 播放音乐
	 */
	public void startMusic() {
		if (music == null)
			return;
		if (musicSt)
			music.start();
	}

	/**
	 * 切换一首音乐并播放
	 */
	public void changeAndPlayMusic() {
		if (music != null)
			music.release();
		initMusic();
		startMusic();
	}

	/**
	 * 获得音乐开关状态
	 *
	 * @return
	 */
	public boolean isMusicSt() {
		return musicSt;
	}

	/**
	 * 设置音乐开关
	 *
	 * @param musicSt
	 */
	public void setMusicSt(boolean musicSt) {
		this.musicSt = musicSt;
		if (musicSt)
			music.start();
		else
			music.stop();
	}

	public void relaseMusic() {
		music.release();
	}
}