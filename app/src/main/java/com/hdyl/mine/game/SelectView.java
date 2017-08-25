package com.hdyl.mine.game;//package com.hdyl.mine.game;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//
//public class SelectView extends ShoulueTuView {
//
//	public SelectView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//	}
//
//	@Override
//	protected void onDraw(Canvas canvas) {
//		if (gameView == null) {
//			return;
//		}
//		if (gameView.size <= this.size) {
//			return;
//		}
//		float xx = x - fullXCount / 2;
//		if (xx < 0) {
//			xx = 0;
//		}
//		float x1 = getRealX();
//		float y1 = getRealY();
//		float x2 = x1 + fullXCount;
//		float y2 = y1 + fullYCount;
//		canvas.drawRect(x1 * size, y1 * size, x2 * size, y2 * size, paint);
//		Log.e("aa", "selecteview");
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		x = (int) (event.getX() / size);
//		y = (int) (event.getY() / size);
//		 if (event.getAction() == MotionEvent.ACTION_DOWN) {
//		
//		 } else {
//		
//		 }
//		invalidate();
//		return true;
//	}
//}
