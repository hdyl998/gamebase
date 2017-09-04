package com.hdyl.mine.base;

import com.hdyl.baselib.appconfig.AppConfigFactory;
import com.hdyl.baselib.appconfig.TestAppConfig;
import com.hdyl.baselib.base.App;

public class MyApplication extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfigFactory.setAppConfig(new TestAppConfig());
    }

}
