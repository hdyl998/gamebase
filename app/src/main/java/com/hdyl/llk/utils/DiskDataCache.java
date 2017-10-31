package com.hdyl.llk.utils;

import com.hdyl.baselib.cache.ICache;
import com.hdyl.baselib.utils.*;

/**
 * Date:2017/10/30 11:09
 * Author:liugd
 * Modification:
 **/


public class DiskDataCache implements IDataCache {
    @Override
    public void put(String key, String value) {
        Tools.writeFileByLines(fileName, key, value);
    }

    @Override
    public String get(String key) {
        return Tools.readFileByLines(fileName, key);
    }

}
