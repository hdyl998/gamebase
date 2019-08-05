package com.hdyl;

import com.hdyl.baselib.appconfig.AppConfigFactory;
import com.hdyl.baselib.appconfig.TestAppConfig;
import com.hdyl.baselib.base.App;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * Created by liugd on 2017/9/6.
 */

public class MyApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfigFactory.setAppConfig(new TestAppConfig());
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.openActivityDurationTrack(false);
    }
}
