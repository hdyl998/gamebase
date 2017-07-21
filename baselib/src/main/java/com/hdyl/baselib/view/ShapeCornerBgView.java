package com.hdyl.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.hdyl.baselib.R;
import com.hdyl.baselib.utils.Tools;

/**
 * 圆角背景控件
 */
public class ShapeCornerBgView extends TextView {
    int mBorderWidth = 1;// 默认1dimpx
    boolean isHasBorder = false;

    int mColorBorder;// 线条的颜色，默认与字的颜色相同
    int mColorBg;// 背景的颜色，默认是透明的
    int mRadius = 3;// 默认3

    int mColorText;

    private Rect rect = new Rect();// 方角

    // 四个角落是否是全是圆角
    boolean isTopLeftCorner = true;
    boolean isBottomLeftCorner = true;
    boolean isTopRightCorner = true;
    boolean isBottomRightCorner = true;

    int mColorBgEnableFalse;
    int mColorTextEnableFalse;
    int mColorBorderEnableFalse;
    boolean isHasBorderEnableFalse = false;


    int mColorBgUnChecked;
    int mColorTextUnchecked;
    int mColorBorderUnchecked;
    boolean isHasBorderUnchecked = false;

    //默认是选中状态
    boolean isChecked = true;

    public ShapeCornerBgView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBorderWidth = Tools.getDimen750Px(context, mBorderWidth);
        mRadius = Tools.getDimen750Px(context, mRadius);
        mColorText = mColorTextEnableFalse = mColorBorder = getCurrentTextColor();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeCornerBgView);
        isHasBorder = mTypedArray.getBoolean(R.styleable.ShapeCornerBgView_appBorder, isHasBorder);// 默认无边框
        mBorderWidth = mTypedArray.getDimensionPixelSize(R.styleable.ShapeCornerBgView_appBorderWidth, mBorderWidth);
        mRadius = mTypedArray.getDimensionPixelSize(R.styleable.ShapeCornerBgView_appRadius, mRadius);

        mColorBorder = mTypedArray.getColor(R.styleable.ShapeCornerBgView_appBorderColor, mColorBorder);

        mColorBg = isHasBorder ? Color.TRANSPARENT : Color.RED;

        mColorBg = mTypedArray.getColor(R.styleable.ShapeCornerBgView_appBgColor, mColorBg);
        // 四个角落是否全是圆角,默认全是真的
        isTopLeftCorner = mTypedArray.getBoolean(R.styleable.ShapeCornerBgView_appTopLeftCorner, isTopLeftCorner);
        isBottomLeftCorner = mTypedArray.getBoolean(R.styleable.ShapeCornerBgView_appBottomLeftCorner, isBottomLeftCorner);
        isTopRightCorner = mTypedArray.getBoolean(R.styleable.ShapeCornerBgView_appTopRightCorner, isTopRightCorner);
        isBottomRightCorner = mTypedArray.getBoolean(R.styleable.ShapeCornerBgView_appBottomRightCorner, isBottomRightCorner);

        mColorBgEnableFalse = mTypedArray.getColor(R.styleable.ShapeCornerBgView_appEnableFalseBgColor, mColorBg);
        mColorTextEnableFalse = mTypedArray.getColor(R.styleable.ShapeCornerBgView_appEnableFalseTextColor, mColorTextEnableFalse);
        mColorBorderEnableFalse = mTypedArray.getColor(R.styleable.ShapeCornerBgView_appUnCheckedBorderColor, mColorBorder);
        isHasBorderEnableFalse = mTypedArray.getBoolean(R.styleable.ShapeCornerBgView_appUnCheckedBorder, isHasBorderEnableFalse);

        mColorBgUnChecked = mTypedArray.getColor(R.styleable.ShapeCornerBgView_appUnCheckedBgColor, mColorBg);
        mColorTextUnchecked = mTypedArray.getColor(R.styleable.ShapeCornerBgView_appUnCheckedTextColor, mColorTextUnchecked);
        mColorBorderUnchecked = mTypedArray.getColor(R.styleable.ShapeCornerBgView_appUnCheckedBorderColor, mColorBorder);
        isHasBorderUnchecked = mTypedArray.getBoolean(R.styleable.ShapeCornerBgView_appUnCheckedBorder, isHasBorderUnchecked);

        isChecked = mTypedArray.getBoolean(R.styleable.ShapeCornerBgView_appChecked, isChecked);

        mTypedArray.recycle();
        shapeDrawable = new ShapeDrawable();
        this.setGravity(Gravity.CENTER);// 全部居中显示
        this.setIncludeFontPadding(false);//设置居中时要用到，不然会不能居中
        this.setChecked(isChecked);
        this.setEnabled(isEnabled());
    }

    ShapeDrawable shapeDrawable;

    @Override
    protected void onDraw(Canvas canvas) {
        if (getWidth() == 0) // 没初始化完成不需要绘制
            return;
        float ffVar[] = getOutterRadii();
        int colorBG = mColorBg;
        boolean hasBorder = isHasBorder;
        int borderColor = mColorBorder;
        //禁用
        if (isEnabled() == false) {
            colorBG = mColorBgEnableFalse;
            hasBorder = isHasBorderEnableFalse;
            borderColor = mColorBorderEnableFalse;
        } else {
            if (isChecked() == false) {//无选中时
                colorBG = mColorBgUnChecked;
                borderColor = mColorBorderUnchecked;
                hasBorder = isHasBorderUnchecked;
            }
        }
        // 先画背景
        if (colorBG != Color.TRANSPARENT) {// 透明就不用画了
            shapeDrawable.setShape(new RoundRectShape(ffVar, null, null));
            Paint paint = shapeDrawable.getPaint();
            paint.setColor(colorBG);
            shapeDrawable.setBounds(rect);
            shapeDrawable.draw(canvas);
        }
        // 有边框
        if (hasBorder) {
            RectF rectF = new RectF(mBorderWidth, mBorderWidth, mBorderWidth, mBorderWidth);
            shapeDrawable.setShape(new RoundRectShape(ffVar, rectF, ffVar));
            Paint paint = shapeDrawable.getPaint();
            paint.setColor(borderColor);
            shapeDrawable.setBounds(rect);
            shapeDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    //获得圆角的度数
    private float[] getOutterRadii() {
        float fRandis[] = {mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius};

        if (isTopLeftCorner == false) {
            fRandis[0] = 0;
            fRandis[1] = 0;
        }
        if (isTopRightCorner == false) {
            fRandis[2] = 0;
            fRandis[3] = 0;
        }
        if (isBottomLeftCorner == false) {
            fRandis[4] = 0;
            fRandis[5] = 0;
        }
        if (isBottomRightCorner == false) {
            fRandis[6] = 0;
            fRandis[7] = 0;
        }
        return fRandis;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        rect.left = 0;
        rect.top = 0;
        rect.bottom = h;
        rect.right = w;
    }

    // 设置是否可用更改颜色
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setTextColor(enabled ? mColorText : mColorTextEnableFalse);
        invalidate();
    }

    public void setmColorBorder(int mColorBorder) {
        this.mColorBorder = mColorBorder;
        invalidate();
    }

    public void setmColorBg(int mColorBg) {
        this.mColorBg = mColorBg;
        invalidate();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        setTextColor(checked ? mColorText : mColorTextUnchecked);
        invalidate();
    }

    public boolean exchangeChecked() {
        setChecked(!isChecked);
        return isChecked;
    }

}