package com.hdyl.baselib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by liugd on 2017/7/21.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    protected Activity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        int layoutID = setLayoutID();
        if (layoutID != 0)
            setContentView(layoutID);
        initViews();
        initClickListener();
    }

    //初始化布局
    public abstract void initViews();

    //需要重写的方法
    public abstract int setLayoutID();


    /***
     * findview方法
     *
     * @param id
     * @param <T>
     * @return
     */
    public final <T extends View> T findViewByID(@IdRes int id) {
        return (T) findViewById(id);
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化点击事件
     */
    public final void initClickListener() {
        int ids[] = setClickIDs();// 设置点击ID
        if (ids != null && ids.length > 0) {
            for (int id : ids) {
                findViewByID(id).setOnClickListener(this);
            }
        }
    }

    /**
     * 设置点击事件
     *
     * @return
     */
    public int[] setClickIDs() {
        return null;
    }
}
