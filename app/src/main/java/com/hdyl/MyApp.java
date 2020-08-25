package com.hdyl;

import android.app.Activity;
import android.os.Bundle;

import com.hdyl.baselib.appconfig.AppConfigFactory;
import com.hdyl.baselib.appconfig.TestAppConfig;
import com.hdyl.baselib.base.App;
import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.mine.tools.ToastUtils;
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

//        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }


    private static final String TAG = "MyApp";

    private class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        private int foregroundActivities;
        private boolean isChangingConfiguration;
        private long time;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            foregroundActivities++;
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                // 应用进入前台
                time = System.currentTimeMillis();
            }
            isChangingConfiguration = false;

            LogUitls.print(TAG,"onActivityStarted "+foregroundActivities+activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityResumed(Activity activity) {
            LogUitls.print(TAG,"onActivityResumed "+foregroundActivities+activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogUitls.print(TAG,"onActivityPaused "+foregroundActivities+activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            foregroundActivities--;
            if (foregroundActivities == 0) {
                // 应用切入后台
                long bgTime = System.currentTimeMillis();
                long diff = (bgTime - time) / 1000;
                ToastUtils.makeTextAndShow(MyApp.getContext(),"应用切入后台");
            }
            isChangingConfiguration = activity.isChangingConfigurations();

            LogUitls.print(TAG,"onActivityStopped "+foregroundActivities+activity.getClass().getSimpleName());
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
