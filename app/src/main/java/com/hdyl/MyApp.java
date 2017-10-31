package com.hdyl;

import com.hdyl.baselib.base.App;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by liugd on 2017/9/6.
 */

public class MyApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();
//        AppConfigFactory.setAppConfig(new TestAppConfig());
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
}
