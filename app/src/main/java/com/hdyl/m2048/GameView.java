package com.hdyl.m2048;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hdyl.mine.R;

/**
 * Created by Administrator on 2017/4/16.
 */

public class GameView extends View {


    private float normalTextSize;//小于10000的字的大小
    private float bigTextSize;//超过10000的字的大小

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        logic.setOnGameStateChangedLinstener((GameLogic.OnGameStateChangedLinstener) context);

    }

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private void initView() {
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "ClearSans-Bold.ttf");
        paint.setTypeface(font);
        TEXT_WHITE = getResources().getColor(R.color.text_white);
        TEXT_BLACK = getResources().getColor(R.color.text_black);
//        LayoutInflater.from(getContext()).inflate(R.layout.layout_game,this,true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (isInEditMode()) {
            return;
        }

//        int textSize = (int) (size * size / Math.max(size, paint.measureText("0000")));
        //1:8 的比率
        divider = w * 1f / (GameLogic.xCOUNT * 8 + (3 + GameLogic.xCOUNT));
        size = divider * 9;//每一个是9份，实际上下左右少了0.5份，就是一份，则是8份，1:8的比率
        startXY = divider * 3 / 2;
        halfDivider = divider / 2;
        paint.setTextSize(size);
        normalTextSize = size * size / Math.max(size, paint.measureText("0000")) * 0.9f;
        bigTextSize = size * size / Math.max(size, paint.measureText("00000")) * 0.9f;
//        paint.setTextSize(bigTextSize);
//        fontHeight = (int) getFontHeight(paint);
    }


    private int TEXT_WHITE;
    private int TEXT_BLACK;

    GameLogic logic = new GameLogic();
    GameAnimBoard animBoard = logic.getAnimBoard();


    int radious = dip2px(5);
    float size;
    int fontHeight;

    float divider;
    RectF r2 = new RectF(); // RectF对象

    float startXY;
    float halfDivider;
    Bitmap bitmapBackground;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0 || isInEditMode()) {
            return;
        }
        //背景一次性去绘制
        if (bitmapBackground == null) {
            bitmapBackground = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(bitmapBackground);
            //画背景
            r2.left = divider;
            r2.right = getWidth() - divider;
            r2.bottom = getHeight() - divider;
            r2.top = divider;
            paint.setColor(0XFFbbada0);
            canvas1.drawRoundRect(r2, radious * 2, radious * 2, paint);
            Cell cell = new Cell(0);
            paint.setColor(cell.getColor());
            for (int i = 0; i < GameLogic.yCOUNT; i++) {
                for (int j = 0; j < GameLogic.xCOUNT; j++) {
                    r2.left = startXY + j * size + halfDivider; // 左边
                    r2.top = startXY + i * size + halfDivider; // 上边
                    r2.right = startXY + j * size + size - halfDivider; // 右边
                    r2.bottom = startXY + i * size + size - halfDivider; // 下边
                    canvas1.drawRoundRect(r2, radious, radious, paint);
                }
            }

        }
        canvas.drawBitmap(bitmapBackground, 0, 0, null);
        //画变化的
        Cell[][] arrData = logic.arrData;
        boolean isNeedInvite = false;
        for (int i = 0; i < GameLogic.yCOUNT; i++) {
            for (int j = 0; j < GameLogic.xCOUNT; j++) {
                Cell cell = arrData[i][j];
                if (cell == null) {
                    return;
                }
                if (!cell.isValueZero()) {
                    AnimManager manager = animBoard.getAnimManager(j, i);
                    //无动画
                    if (manager.isAnim() == false) {
                        r2.left = startXY + j * size + halfDivider; // 左边
                        r2.top = startXY + i * size + halfDivider; // 上边
                        r2.right = startXY + j * size + size - halfDivider; // 右边
                        r2.bottom = startXY + i * size + size - halfDivider; // 下边
                        paint.setColor(cell.getColor());
                        canvas.drawRoundRect(r2, radious, radious, paint);
                        paint.setColor(cell.getValue() > 4 ? TEXT_WHITE : TEXT_BLACK);
                        paint.setTextSize(cell.isBigValue() ? bigTextSize : normalTextSize);
                        canvas.drawText(GameConfig.getInstance().getStringShow(cell), startXY + (j * size + size / 2), startXY + i * size + size / 2 + getFontHeight(paint) / 2, paint);
                    } else {//有动画
                        for (AnimCell anim : manager.getListAnims()) {
                            if (anim.isActive()) {
                                switch (anim.getAnimationType()) {
                                    case AnimCell.TYPE_CREATE: {
                                        float percent = anim.getPercent();
                                        isNeedInvite = true;
                                        float tempSize = percent * (size - 2 * halfDivider) / 2;
                                        r2.left = startXY + j * size + size / 2 - tempSize; // 左边
                                        r2.top = startXY + i * size + size / 2 - tempSize; // 上边
                                        r2.right = startXY + j * size + size / 2 + tempSize; // 右边
                                        r2.bottom = startXY + i * size + size / 2 + tempSize; // 下边
                                        paint.setColor(cell.getColor());
                                        canvas.drawRoundRect(r2, radious, radious, paint);
                                        paint.setColor(cell.getValue() > 4 ? TEXT_WHITE : TEXT_BLACK);
                                        float currentTextSize = cell.isBigValue() ? bigTextSize : normalTextSize;
                                        paint.setTextSize(currentTextSize * percent);
                                        canvas.drawText(GameConfig.getInstance().getStringShow(cell), startXY + (j * size + size / 2), startXY + i * size + size / 2 + getFontHeight(paint) / 2, paint);
                                    }
                                    break;
                                    case AnimCell.TYPE_MERGE: {
                                        float percent = anim.getPercent();
                                        isNeedInvite = true;
                                        float tempSize = percent * halfDivider;
                                        r2.left = startXY + j * size + halfDivider - tempSize; // 左边
                                        r2.top = startXY + i * size + halfDivider - tempSize; // 上边
                                        r2.right = startXY + j * size + size - halfDivider + tempSize; // 右边
                                        r2.bottom = startXY + i * size + size - halfDivider + tempSize; // 下边
                                        paint.setColor(cell.getColor());
                                        canvas.drawRoundRect(r2, radious, radious, paint);
                                        paint.setColor(cell.getValue() > 4 ? TEXT_WHITE : TEXT_BLACK);
                                        float currentTextSize = cell.isBigValue() ? bigTextSize : normalTextSize;
                                        paint.setTextSize(currentTextSize + tempSize);
                                        canvas.drawText(GameConfig.getInstance().getStringShow(cell), startXY + (j * size + size / 2), startXY + i * size + size / 2 + getFontHeight(paint) / 2, paint);
                                    }
                                    break;
                                    case AnimCell.TYPE_MOVE:
                                        float percent = anim.getPercent();
                                        isNeedInvite = true;
                                        int xValue = -cell.getX() + anim.getAimX();
                                        int yValue = -cell.getY() + anim.getAimY();
                                        float xTemp = 0;
                                        float yTemp = 0;
                                        if (yValue == 0) {//x上的移动
                                            xTemp = xValue * (1 - percent) * size;
                                        } else if (xValue == 0) {//y上的移动
                                            yTemp = yValue * (1 - percent) * size;
                                        }
                                        r2.left = startXY + j * size + halfDivider + xTemp; // 左边
                                        r2.top = startXY + i * size + halfDivider + yTemp; // 上边
                                        r2.right = startXY + j * size + size - halfDivider + xTemp; // 右边
                                        r2.bottom = startXY + i * size + size - halfDivider + yTemp; // 下边
                                        paint.setColor(anim.getColor());
                                        canvas.drawRoundRect(r2, radious, radious, paint);
                                        paint.setColor(anim.getValue() > 4 ? TEXT_WHITE : TEXT_BLACK);
                                        float currentTextSize = cell.isBigValue() ? bigTextSize : normalTextSize;
                                        paint.setTextSize(currentTextSize);
                                        canvas.drawText(GameConfig.getInstance().getStringShow(anim), startXY + (j * size + size / 2) + xTemp, startXY + i * size + size / 2 + yTemp + getFontHeight(paint) / 2, paint);
                                        break;
                                }
                            } else {//这里是画还没合并前原始值的动画
                                isNeedInvite = true;
                                switch (anim.getAnimationType()) {
                                    case AnimCell.TYPE_MERGE: {
                                        r2.left = startXY + j * size + halfDivider; // 左边
                                        r2.top = startXY + i * size + halfDivider; // 上边
                                        r2.right = startXY + j * size + size - halfDivider; // 右边
                                        r2.bottom = startXY + i * size + size - halfDivider; // 下边
                                        paint.setColor(anim.getColor());
                                        canvas.drawRoundRect(r2, radious, radious, paint);
                                        paint.setColor(anim.getValue() > 4 ? TEXT_WHITE : TEXT_BLACK);
                                        paint.setTextSize(cell.isBigValue() ? bigTextSize : normalTextSize);
                                        canvas.drawText(GameConfig.getInstance().getStringShow(anim), startXY + (j * size + size / 2), startXY + i * size + size / 2 + getFontHeight(paint) / 2, paint);
                                    }
                                    break;
                                }
                            }
                        }
                        manager.ticker(60);
                    }
                }
            }
        }
        if (isNeedInvite) {
            invalidate();//10ms绘制一次，1秒绘制100次，1秒60次以上就很流畅
        }
    }


    /**
     * @return 返回指定的文字高度
     */

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return (fm.descent - fm.ascent) / 2;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, size);
    }


    float x, y;
    /***
     * 灵敏度
     */
    int sensibility = dip2px(5);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
//            case MotionEvent.ACTION_UP:
                float upx = event.getX();
                float upy = event.getY();
                float senX = Math.abs(upx - x);
                float senY = Math.abs(upy - y);
                if (senX > sensibility || senY > sensibility) {
                    if (senX > senY) {
                        logic.push(x > upx ? GameLogic.GAME_DIRECTION_LEFT : GameLogic.GAME_DIRECTION_RIGHT);
                    } else {
                        logic.push(y > upy ? GameLogic.GAME_DIRECTION_UP : GameLogic.GAME_DIRECTION_DOWN);
                    }
                }
                break;
        }
        return true;
    }
}
