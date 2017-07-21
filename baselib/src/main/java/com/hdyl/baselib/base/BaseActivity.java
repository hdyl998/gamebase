package com.hdyl.baselib.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by liugd on 2017/7/21.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(setLayoutID());
        initViews();
        initClickListener();
    }

    //初始化布局
    public abstract void initViews();

    //需要重写的方法
    public int setLayoutID() {
        return 0;
    }


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
