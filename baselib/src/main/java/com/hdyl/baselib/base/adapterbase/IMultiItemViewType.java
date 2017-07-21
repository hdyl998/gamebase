package com.hdyl.baselib.base.adapterbase;

/**
 * Created by liugd on 2017/4/24.
 */

public interface IMultiItemViewType<T> {
    int[] getLayoutIDs();

    int getLayoutIDsIndex(T t, int position);
}
