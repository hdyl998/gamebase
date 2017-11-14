package com.hdyl.baselib.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.hdyl.baselib.R;

/**
 * Created by Administrator on 2017/11/14.
 */

public abstract class BasePullFragment<T extends View> extends BaseFragment {
    SwipeRefreshLayout layout;

    T pullView;

    public T getPullView() {
        return pullView;
    }

    @Override
    public void initViews() {
        layout=findViewByID(R.id.refreshLayout);
        layout.addView(pullView=createPullView(mContext));
    }


    protected abstract T createPullView(Context mContext);


    @Override
    public int setLayoutID() {
        return R.layout.fragment_layout_base_pull;
    }

    public void setPullEnable(boolean isEnable){
        layout.setEnabled(isEnable);
    }
}
