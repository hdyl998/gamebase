package com.hdyl.mine.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ShoulueTuView extends View {

	Paint paint = new Paint();

	public boolean isFirstInit = false;
	public int fullXCount = -1;
	public int fullYCount = -1;

	boolean isNeedDrawing = false;

	public ShoulueTuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setStrokeWidth(dip2px(context, 1));
		paint.setStyle(Style.STROKE);
	}

	GameView gameView;

	int size;
	Bitmap bitmapTemp;

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		if (gameView == null) {
			return;
		}
		// canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

		if (isNeedDrawing == true) {
			isNeedDrawing = false;
			if (bitmapTemp == null)
				bitmapTemp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas1 = new Canvas(bitmapTemp);
			// if (AppSet.getInstence().theme == 1) {
			// Rect rrRect = new Rect();
			// for (int i = 0; i < gameView.WIDTH; i++) {
			// for (int j = 0; j < gameView.HEIGHT; j++) {
			// if (gameView.arrCover[i][j] == 1) {
			// if (gameView.arr[i][j] != 0) {
			// rrRect.set(i * size, j * size, i * size + size, j * size +
			// size);// 消除警告
			// bitmap = gameView.bitmaps[0];
			// canvas1.drawBitmap(bitmap, null, rrRect, null);
			// }
			// } else if (gameView.arrCover[i][j] == 2) {
			// bitmap = gameView.bitmaps[gameView.ID_COVER];
			// rrRect.set(i * size, j * size, i * size + size, j * size +
			// size);// 消除警告
			// canvas1.drawBitmap(bitmap, null, rrRect, null);
			// }
			// }
			// }
			// }
			Bitmap bitmap = null;
			for (int i = 0; i < gameView.WIDTH; i++) {
				for (int j = 0; j < gameView.HEIGHT; j++) {
					if (gameView.arrCover[i][j] == 0) {
						bitmap = gameView.bitmaps[gameView.ID_COVER];
					} else if (gameView.arrCover[i][j] == 2) {
						bitmap = gameView.bitmaps[gameView.ID_FLAG];
						// if (gameView.gameState == gameView.STATE_LOSE) {
						// if (gameView.arr[i][j] != 9) {
						// bitmap = gameView.bitmaps[gameView.ID_ERROR_BLACK];
						// }
						// }
					} else {
						bitmap = gameView.bitmaps[gameView.arr[i][j]];
					}
					canvas1.drawBitmap(bitmap, null, new Rect(i * size, j * size, i * size + size, j * size + size), null);
				}
			}
		}
		canvas.drawBitmap(bitmapTemp, 0, 0, paint);

		if (gameView.size <= this.size) {
			return;
		}
		float xx = x - fullXCount / 2;
		if (xx < 0) {
			xx = 0;
		}
		float x1 = getRealX();
		float y1 = getRealY();
		float x2 = x1 + fullXCount;
		float y2 = y1 + fullYCount;

		paint.setColor(Color.RED);
		canvas.drawRect(x1 * size, y1 * size, x2 * size, y2 * size, paint);





		if(isNeedDrawCover()){
			paint.setColor(Color.GREEN);
			for (int i = 0; i < gameView.WIDTH; i++) {
				for (int j = 0; j < gameView.HEIGHT; j++) {
					if (gameView.arrCover[i][j] == 0) {

						canvas.drawRect(i * size, j * size, i * size+size, j * size+size, paint);

					}
				}
			}
		}

		// Log.e("aa", "selecteview");
		//
		// Log.e("aa", "ondraw");
		// int one=dip2px(getContext(), 1);
	}

	// 让画布行重置
	public void setIsNeedDrawing() {
		isNeedDrawing = true;
	}


	public boolean isNeedDrawCover(){

		int cover=0;
		for (int i = 0; i < gameView.WIDTH; i++) {
			for (int j = 0; j < gameView.HEIGHT; j++) {
				if (gameView.arrCover[i][j] == 0) {
					cover++;
				}
			}
		}
		if(cover<gameView.WIDTH*gameView.HEIGHT*0.1){
			return true;
		}
		return false;

	}


	public float getRealX() {
		if (x - fullXCount / 2 < 0) {
			x = fullXCount / 2;
			return 0;
		}
		if (x + fullXCount - fullXCount / 2 > gameView.WIDTH) {
			x = gameView.WIDTH - (fullXCount - fullXCount / 2);
			return gameView.WIDTH - fullXCount;
		}
		return x - fullXCount / 2;
	}

	public float getRealY() {
		if (y - fullYCount / 2 < 0) {
			y = fullYCount / 2;
			return 0;
		}
		if (y + fullYCount - fullYCount / 2 > gameView.HEIGHT) {
			y = gameView.HEIGHT - (fullYCount - fullYCount / 2);
			return gameView.HEIGHT - fullYCount;
		}
		return y - fullYCount / 2;
	}

	/**
	 * 计算尺寸重新绘制
	 */
	public void initPiciture(GameView gameView, int width, int height) {
		this.gameView = gameView;
		int temp = (width - dip2px(getContext(), 5)) / gameView.WIDTH;
		int temp2 = (height - dip2px(getContext(), 5)) / gameView.HEIGHT;
		size = Math.min(temp, temp2);
		if (size > gameView.size) {
			size = gameView.size;
		}
		requestLayout();
	}

	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.setMeasuredDimension((size - 0) * gameView.WIDTH, (size - 0) * gameView.HEIGHT);
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // if (event.getAction() == MotionEvent.ACTION_DOWN) {
	// //
	// // } else {
	// //
	// // }
	// x = (int) (event.getX() / size);
	// y = (int) (event.getY() / size);
	// invalidate();
	// return super.onTouchEvent(event);
	// }
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x = (int) (event.getX() / size);
		y = (int) (event.getY() / size);
		invalidate();
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (onClickListener != null) {
				onClickListener.onClick(this);
			}
		}
		return true;
	}

	public int x = -1, y = -1;// x,y percent

	private OnClickListener onClickListener;

	public void setOnClick(OnClickListener onCli) {
		this.onClickListener = onCli;
	}
}
