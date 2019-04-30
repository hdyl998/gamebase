package com.hdyl;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.hdyl.baselib.base.BaseFragment;
import com.hdyl.mine.R;
import com.hdyl.mine.tools.ToastUtils;

/**
 * Created by Administrator on 2017/11/15.
 */

public class TestFragment extends BaseFragment {


    ContentLoadingProgressBar contentLoadingProgressBar;

    //    EditText editText;
    @Override
    public void initViews() {
        contentLoadingProgressBar = findViewByID(R.id.contentLoadingProgressBar);
    }

    @Override
    public void onClick(View v) {
        contentLoadingProgressBar.show();
    }

    @Override
    public int setLayoutID() {
        return R.layout.fragment_test;
    }

    @Override
    public int[] setClickIDs() {
        return new int[]{R.id.button};
    }
}
