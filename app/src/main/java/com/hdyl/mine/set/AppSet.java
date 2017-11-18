package com.hdyl.mine.set;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;
import com.hdyl.mine.tools.MySharepreferences;

public class AppSet {

    public static AppSet instence;

    public static AppSet getInstence() {
        if (instence == null) {
            instence = new AppSet();
        }
        return instence;
    }

    public int theme = 0;


    public int themeBg = 0;


    int resid[] = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4};

    String sss[] = {"主题1", "主题2", "主题3", "主题4"};

    public int getBgId() {
        return resid[themeBg];
    }

    public String getBgText() {
        return sss[themeBg];
    }

    public void increaceBg() {
        themeBg++;
        themeBg = (themeBg) % resid.length;
    }

    private AppSet() {
        theme = MySharepreferences.getInt(App.getContext(), "aa", "themetype");
        themeBg = MySharepreferences.getInt(App.getContext(), "aa", "themetype_bg");
        if(themeBg>=resid.length){
            themeBg=0;
        }
    }

    public void saveTheme() {
        MySharepreferences.putInt(App.getContext(), "aa", "themetype", theme);
        MySharepreferences.putInt(App.getContext(), "aa", "themetype_bg", themeBg);
    }

}
