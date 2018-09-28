package com.hdyl.banghujichong;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hdyl.baselib.base.App;
import com.hdyl.baselib.utils.Tools;
import com.hdyl.m2048.Cell;
import com.hdyl.m2048.GameLogic;

/**
 * 棒虎鸡虫view
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class BhjcView extends View {

    int size;
    int xOffset;
    int yOffset;
    int lineWidth = Tools.getDimen750Px(6);
    int divider;

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


    public BhjcLogic getBhjcLogic() {
        return bhjcLogic;
    }

    void initViews() {
        bhjcLogic.setOnGameEvent((BhjcLogic.IOnGameEvent) getContext());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0) {
            return;
        }
        mPaint.setColor(0xff787878);
        canvas.drawBitmap(bitmapBackground, 0, 0, null);
        bhjcLogic.drawBoard(canvas, size, mPaint, divider, xOffset, yOffset);
    }

    Bitmap bitmapBackground;
    int radious = Tools.dip2px(App.getContext(), 5);


    private void createBg() {
        //背景一次性去绘制
        if (bitmapBackground == null) {
            bitmapBackground = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(bitmapBackground);
            RectF r2 = new RectF(); // RectF对象
            float halfDivider = divider / 2;
            //画背景
            r2.left = xOffset - halfDivider;
            r2.right = getWidth() + halfDivider - xOffset;
            r2.bottom = getHeight() + halfDivider - yOffset;
            r2.top = yOffset - halfDivider;
            mPaint.setColor(0XFFbbada0);
            canvas1.drawRoundRect(r2, radious * 2, radious * 2, mPaint);
            Cell cell = new Cell(0);

            mPaint.setColor(cell.getColor());
            for (int i = 0; i < bhjcLogic.getHEIGHT(); i++) {
                for (int j = 0; j < bhjcLogic.getWIDTH(); j++) {
                    r2.left = xOffset + j * size + halfDivider; // 左边
                    r2.top = yOffset + i * size + halfDivider; // 上边
                    r2.right = xOffset + j * size + size - halfDivider; // 右边
                    r2.bottom = yOffset + i * size + size - halfDivider; // 下边
                    canvas1.drawRoundRect(r2, radious, radious, mPaint);
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bhjcLogic.newGame();
        int wid = w / bhjcLogic.getWIDTH();
        int hei = h / bhjcLogic.getHEIGHT();
        size = Math.min(wid, hei);
        divider = size / 8;
        xOffset = (w - (size * bhjcLogic.getWIDTH())) / 2 - lineWidth / 2;//线条是从中心线画的所以需要减掉一半
        yOffset = (h - (size * bhjcLogic.getHEIGHT())) / 2 - lineWidth / 2;
        mPaint.setTextSize(size / 3);
        createBg();
    }
}
