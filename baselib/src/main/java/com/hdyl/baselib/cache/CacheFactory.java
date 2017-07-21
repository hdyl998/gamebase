package com.hdyl.baselib.cache;

/**
 * Created by liugd on 2017/3/27.
 */

public class CacheFactory {


    private static ICache cache;

    public static ICache getCache() {
        if (cache == null) {
            cache = MemoryCache.getInstance();
        }
        return cache;
    }
}
