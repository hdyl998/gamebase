package com.hdyl.llk.utils;

/**
 * Date:2017/10/30 11:09
 * Author:liugd
 * Modification:双缓存
 **/


public class SpDiskDoubleCache implements IDataCache {

    IDataCache spCache = new SpDataCache();
    IDataCache diskCache = new DiskDataCache();

    @Override
    public void put(String key, String value) {
        spCache.put(key, value);
        diskCache.put(key, value);
    }

    @Override
    public String get(String key) {
        String str = spCache.get(key);
        if (str == null) {
            str = diskCache.get(key);
        }
        return str;
    }
}
