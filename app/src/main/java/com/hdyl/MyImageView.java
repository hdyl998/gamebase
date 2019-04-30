package com.hdyl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hdyl.mine.R;

/**
 * Created by Administrator on 2017/12/9.
 */

public class MyImageView extends ImageView {
    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    float rate = 0;//0-1f
    private Bitmap decodeBitmap(int resId) {
        return BitmapFactory.decodeResource(getContext().getResources(), resId);
    }

    public void setRate(float rate) {
        if (rate > 1) {
            rate = 1f;
        } else if (rate < 0) {
            rate = 0;
        }
        if (this.rate != rate) {
            this.rate = rate;
            invalidate();
        }
    }

    public float getRate() {
        return rate;
    }

    private Bitmap bg;
//    private Bitmap photo;

    private int bg_width;
    private int bg_height;

    private void init() {
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (getWidth() == 0) {
            return;
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        RectF rectf = new RectF(0, 0, getWidth(), getHeight());
        canvas.saveLayer(rectf, null, Canvas.ALL_SAVE_FLAG);
//        canvas.drawColor(getCurrentColor(getRate(), 0xffffffff, 0xff666666));

        Rect rectf1 = new Rect(0, 0, getWidth(), getHeight());

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//src_in（取两层绘制交集。显示上层（下层用到paint）） 与DST_IN（取两层绘制交集。显示下层（上层用到paint）） 是相反的过程，
        //paint用时都是不需要的地方
        canvas.drawColor(getCurrentColor(getRate(), 0xffffffff, 0xffFF1111));//下层
        canvas.drawBitmap(getBitmap(), null, rectf1, paint);//上层
        paint.setXfermode(null);
        canvas.restore();

    }

    /**
     * 根据fraction值来计算当前的颜色。
     */
    private int getCurrentColor(float fraction, int startColor, int endColor) {
        int redCurrent;
        int blueCurrent;
        int greenCurrent;
        int alphaCurrent;

        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        redCurrent = (int) (redStart + fraction * redDifference);
        blueCurrent = (int) (blueStart + fraction * blueDifference);
        greenCurrent = (int) (greenStart + fraction * greenDifference);
        alphaCurrent = (int) (alphaStart + fraction * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }

    private Bitmap getBitmap() {
        return ((BitmapDrawable) getDrawable()).getBitmap();
    }
//
//    private void scaleImage(){
//
//        if(photo!=null){
//
//            int widht=photo.getWidth();
//            int height=photo.getHeight();
//
//            int new_width=0;
//            int new_height=0;
//
//            if(widht!=height){
//                if(widht>height){
//                    new_height=bg_height;
//                    new_width=widht*new_height/height;
//                }else{
//                    new_width=bg_width;
//                    new_height=height*new_width/widht;
//                }
//            }else{
//                new_width=bg_width;
//                new_height=bg_height;
//            }
//            photo = Bitmap.createScaledBitmap(photo, new_width, new_height, true);
//        }
//    }
}
