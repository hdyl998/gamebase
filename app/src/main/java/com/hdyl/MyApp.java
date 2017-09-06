package com.hdyl;

import com.hdyl.baselib.appconfig.AppConfigFactory;
import com.hdyl.baselib.appconfig.TestAppConfig;
import com.hdyl.baselib.base.App;

/**
 * Created by liugd on 2017/9/6.
 */

public class MyApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfigFactory.setAppConfig(new TestAppConfig());
    }
}
