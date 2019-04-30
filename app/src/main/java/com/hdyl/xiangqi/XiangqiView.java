package com.hdyl.xiangqi;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hdyl.baselib.utils.log.LogUitls;

/**
 * <p>Created by Administrator on 2018/9/28.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class XiangqiView extends View {
    public XiangqiView(Context context) {
        super(context);
        initViews();
    }


    public XiangqiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public XiangqiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @TargetApi(21)
    public XiangqiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
    }

    XiangqiLogic xiangqiLogic = new XiangqiLogic();


    public XiangqiLogic getXiangqiLogic() {
        return xiangqiLogic;
    }

    private void initViews() {
        xiangqiLogic.setXiangqiGameEvent((IXiangqiGameEvent) getContext());
    }

    int xOffset;
    int yOffset;
    int size;

    Rect rect = new Rect();

    int qiSpace;//棋子的空隔

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0) {
            return;
        }
        //画背景
        rect.top = yOffset;
        rect.left = xOffset;
        rect.bottom = rect.top + size * xiangqiLogic.getyCount();
        rect.right = rect.left + size * xiangqiLogic.getxCount();
        canvas.drawBitmap(XiangqiResourcesProvider.getInstance().getBitmapBg(), null, rect, null);
        xiangqiLogic.drawBoard(canvas, size, xOffset, yOffset,qiSpace);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int wid = w / xiangqiLogic.getxCount();
        int hei = h / xiangqiLogic.getyCount();
        size = Math.min(wid, hei);
        qiSpace = size / 6;
        xOffset = (w - (size * xiangqiLogic.getxCount())) / 2;//线条是从中心线画的所以需要减掉一半
        yOffset = (h - (size * xiangqiLogic.getyCount())) / 2;
        xiangqiLogic.newGame();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return true;
        }
        int x = (int) ((event.getX() - xOffset) / size);
        int y = (int) ((event.getY() - yOffset) / size);
        if (x >= 0 && x < xiangqiLogic.getxCount() && y >= 0 && y < xiangqiLogic.getyCount()) {
            LogUitls.print("x=" + x + " y=" + y);
            xiangqiLogic.clickPosition(x, y);
        }
        return true;
    }


}
