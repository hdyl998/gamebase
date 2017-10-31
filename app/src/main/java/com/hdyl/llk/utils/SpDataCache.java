package com.hdyl.llk.utils;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.tools.MySharepreferences;

/**
 * Date:2017/10/30 11:09
 * Author:liugd
 * Modification:
 **/


public class SpDataCache implements IDataCache {
    @Override
    public void put(String key, String value) {
        MySharepreferences.putString(App.getContext(), fileName, key, value);
    }

    @Override
    public String get(String key) {
        return MySharepreferences.getString(App.getContext(), fileName, key);
    }


}
