package com.hdyl.baselib.base;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hdyl.baselib.R;
import com.hdyl.baselib.base.BasePullFragment;

/**
 * Created by Administrator on 2017/11/14.
 */

public abstract class PullScrollFragment extends BasePullFragment<ScrollView> {
    @Override
    protected ScrollView createPullView(Context mContext) {
        return new ScrollView(mContext);
    }

    @Override
    public void initViews() {
        super.initViews();
        View.inflate(mContext,setBodyID(),pullView);

    }

    protected abstract int setBodyID();


}
