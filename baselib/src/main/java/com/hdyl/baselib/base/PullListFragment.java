package com.hdyl.baselib.base;

import android.content.Context;
import android.widget.ListView;

import com.hdyl.baselib.R;

/**
 * Created by Administrator on 2017/11/14.
 */

public class PullListFragment extends BasePullFragment<ListView> {
    @Override
    protected ListView createPullView(Context mContext) {
        return new ListView(mContext);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

}
