package com.hdyl.baselib.appconfig;

/**
 * Created by liugd on 2017/7/21.
 */

public class AppConfigFactory {
    private static BaseAppConfig instance;

    //这里配置打包时间
    public static BaseAppConfig getConfig() {
        if (instance == null) {
            synchronized (AppConfigFactory.class) {
                instance = new OnlineAppConfig();
            }
        }
        return instance;
    }

    public static void setAppConfig(BaseAppConfig config) {
        instance = config;
    }
}
