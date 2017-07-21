package com.hdyl.baselib.cache;

import com.caiyu.qqsd.common.App;

/**
 * 双缓存，首先从内存中取，如果没有则从sp中取
 * Created by liugd on 2017/3/27.
 */

public class MemorySpCache implements ICache {

    MemoryCache memoryCache = MemoryCache.getInstance();
    SpCache spCache = new SpCache(App.getContext());

    @Override
    public void put(String key, Object value) {
        memoryCache.put(key, value);
        spCache.put(key, value);
    }

    @Override
    public Object get(String key) {
        Object obj = memoryCache.get(key);
        if (obj == null) {
            obj = spCache.get(key);
        }
        return obj;
    }

    @Override
    public boolean contains(String key) {
        return memoryCache.contains(key) || spCache.contains(key);
    }

    @Override
    public void remove(String key) {
        memoryCache.remove(key);
        spCache.remove(key);
    }

    @Override
    public void clear() {
        memoryCache.clear();
        spCache.clear();
    }
}
