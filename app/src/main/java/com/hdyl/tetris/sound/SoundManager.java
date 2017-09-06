package com.hdyl.tetris.sound;

import com.hdyl.mine.R;
import com.hdyl.tetris.common.GameConfig;

/**
 * 音乐播放器代理类
 * Created by Administrator on 2017/5/14.
 */

public class SoundManager {

    static SoundManager soundMangaer;

    public static SoundManager getInstance() {
        if (soundMangaer == null) {
            soundMangaer = new SoundManager();
        }
        return soundMangaer;
    }

    private SoundManager() {
        initDatas();
    }


    SoundPlayer soundPlayer = new SoundPlayer();
    BgMusicPlayer bgMusicPlayer = new BgMusicPlayer();


    public final static int BG_MUSIC_READY = 0;
    public final static int BG_MUSIC_PLAYING = 1;

    public final static int SOUND_LEFT_RIGHT = 0;
    public final static int SOUND_FASTDOWN = 1;
    public final static int SOUND_ROTATION = 2;
    public final static int SOUND_DOWN = 3;
    public final static int SOUND_DELETE1 = 4;
    public final static int SOUND_DELETE2 = 5;
    public final static int SOUND_DELETE3 = 6;
    public final static int SOUND_DELETE4 = 7;

    private void initDatas() {
        bgMusicPlayer.initRawRes(BG_MUSIC_READY, R.raw.menubg);
        bgMusicPlayer.initRawRes(BG_MUSIC_PLAYING, R.raw.gamebg);

        soundPlayer.initRawRes(SOUND_LEFT_RIGHT, R.raw.action);
        soundPlayer.initRawRes(SOUND_FASTDOWN, R.raw.fastdown);
        soundPlayer.initRawRes(SOUND_ROTATION, R.raw.rotation);
        soundPlayer.initRawRes(SOUND_DOWN, R.raw.down);
        soundPlayer.initRawRes(SOUND_DELETE1, R.raw.delete1);
        soundPlayer.initRawRes(SOUND_DELETE2, R.raw.delete2);
        soundPlayer.initRawRes(SOUND_DELETE3, R.raw.delete3);
        soundPlayer.initRawRes(SOUND_DELETE4, R.raw.delete4);
    }

    //当前正在播放的背景音乐的ID
    private int flagCurBgMusic;

    public void playReadyBgMusic() {
        playBgMusic(BG_MUSIC_READY);
    }

    public void playPlayingBgMusic() {
        playBgMusic(BG_MUSIC_PLAYING);
    }

    public void playBgMusic(int flag) {
        if (GameConfig.getInstance().isBgMusic()) {
            bgMusicPlayer.play(flag);
            this.flagCurBgMusic=flag;
        } else {
            bgMusicPlayer.release();
        }
    }

    /***
     * 用于外部切换开关时，开关背景音乐
     */
    public void updateBgMusicStatus(){
        playBgMusic(this.flagCurBgMusic);
    }

    public void playSound(int flag) {
        if (GameConfig.getInstance().isSoundEffect()) {
            soundPlayer.play(flag);
        } else {
            soundPlayer.release();
        }
    }
    public void relaseAll() {
        soundPlayer.release();
        bgMusicPlayer.release();
    }

    public void releaseBgMusic(){
       bgMusicPlayer.release();
    }

}
