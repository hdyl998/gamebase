package com.hdyl.baselib.base;

import android.app.Application;

import com.hdyl.baselib.appconfig.AppConfigFactory;
import com.hdyl.baselib.utils.log.AndroidLogAdapter;
import com.hdyl.baselib.utils.log.LogLevel;
import com.hdyl.baselib.utils.log.Logger;

/**
 * Created by Administrator on 2017/4/23.
 */

public class App extends Application {

    private static App app;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        initLog();
    }

    private void initLog() {
        if (AppConfigFactory.getConfig().isPrintLog()) {
            Logger.init("lgdx")                 // default PRETTYLOGGER or use just init()
                    .methodCount(3)                 // default 2
                    .hideThreadInfo()               // default shown
                    .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                    .methodOffset(3)                // default 0
                    .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
        }
    }
}
