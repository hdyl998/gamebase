package com.hdyl.baselib.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liugd on 2017/7/19.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    View rootView;
    Activity mContext;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = setLayoutView();
        if (view != null) {
            rootView = view;
        } else {
            if (setLayoutID() == 0) {
                throw new IllegalArgumentException("setLayoutView 和setLayoutID方法你必须实现一个");
            }
            rootView = inflater.inflate(setLayoutID(), container, false);
        }
        return rootView;
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initViews();
        initClickListener();
    }

    //需要重写的方法
    public int setLayoutID() {
        return 0;
    }

    //需要重写的方法
    public View setLayoutView() {
        return null;
    }

    //初始化布局
    public abstract void initViews();

    /***
     * findview方法
     *
     * @param id
     * @param <T>
     * @return
     */
    public final <T extends View> T findViewByID(@IdRes int id) {
        return (T) rootView.findViewById(id);
    }

    /***
     * findview方法
     *
     * @param id
     * @param <T>
     * @return
     */
    public final <T extends View> T $(@IdRes int id) {
        return (T) rootView.findViewById(id);
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
