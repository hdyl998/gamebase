package com.hdyl.banghujichong;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hdyl.baselib.utils.Tools;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class BhjcView extends View {


    public BhjcView(Context context) {
        super(context);
        initViews();
    }

    public BhjcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BhjcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @TargetApi(21)
    public BhjcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
    }

    BhjcLogic bhjcLogic = new BhjcLogic();

    Paint mPaint;

    void initViews() {
        bhjcLogic.setOnGameEvent((BhjcLogic.IOnGameEvent) getContext());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(Tools.getDimen750Px(5));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0) {
            return;
        }
        bhjcLogic.drawBoard(canvas, size, mPaint, size / 8);


    }

    int size;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bhjcLogic.newGame();
        int wid = w / bhjcLogic.getWIDTH();
        int hei = h / bhjcLogic.getHEIGHT();
        size = Math.min(wid, hei);
        mPaint.setTextSize(size / 3);

    }
}
