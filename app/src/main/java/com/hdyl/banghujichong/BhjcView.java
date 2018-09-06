package com.hdyl.banghujichong;


import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class BhjcView extends View {

    public BhjcView(Context context) {
        super(context);
    }

    public BhjcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BhjcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BhjcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
