package com.hdyl.llk;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;

public class Constants {

	public static Bitmap[] bitmaps = null;
	public static int PIC_NUM = 36;
	public static int OFFSET = 0;
	public static int WIDTH_BITMAP = 0;
	public static int HEIGHT_BITMAP = 0;

	public static int PIC_TYPE = 1;

	public static void initBitmap() {

		if (PIC_TYPE != 0) {
			initMap2();
			return;
		}

		int res[] = { R.drawable.aa2, R.drawable.aa3, R.drawable.aa7, R.drawable.aa8, R.drawable.aa9, R.drawable.aa12, R.drawable.aa13, R.drawable.aa15, R.drawable.aa16, R.drawable.aa18, R.drawable.aa19, R.drawable.aa20, R.drawable.aa21, R.drawable.aa22, R.drawable.aa23, R.drawable.aa24, R.drawable.aa25, R.drawable.aa26, R.drawable.aa27, R.drawable.aa28, R.drawable.aa29, R.drawable.aa30, R.drawable.aa31, R.drawable.aa32, R.drawable.aa33, R.drawable.aa35, R.drawable.aa36, R.drawable.aa37, R.drawable.aa38, R.drawable.aa39, R.drawable.aa40, R.drawable.aa42, R.drawable.aa43, R.drawable.ic_01, R.drawable.ic_02, R.drawable.ic_03 };
		if (bitmaps != null) {
			for (Bitmap bitmap : bitmaps) {
				bitmap.recycle();
			}
		}

		Log.e("aaa", res.length + "  ");
		bitmaps = new Bitmap[res.length];
		for (int i = 0; i < res.length; i++) {
			bitmaps[i] = BitmapFactory.decodeResource(App.getContext().getResources(), res[i]);
		}
		WIDTH_BITMAP = bitmaps[0].getWidth();
		HEIGHT_BITMAP = bitmaps[0].getHeight();
	}

	public static void initMap2() {
		if (bitmaps != null) {
			for (Bitmap bitmap : bitmaps) {
				bitmap.recycle();
			}
		}
		bitmaps = new Bitmap[PIC_NUM];
		Bitmap numBitmap = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.pics);
		int width = numBitmap.getWidth() / 6;
		int height = numBitmap.getHeight() / 6;
		for (int i = 0; i < PIC_NUM; i++) {
			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 设置一个iconSize大小的块的位图
			Canvas canvas = new Canvas(bitmap);// 获取bitMap的画布
			int yTimes = i / 6;
			canvas.drawBitmap(numBitmap, new Rect(width * (i % 6), yTimes * height, width * (i % 6 + 1), yTimes * height + height), new Rect(0, 0, width, height), null);
			bitmaps[i] = bitmap;
		}
		numBitmap.recycle();
		WIDTH_BITMAP = width;
		HEIGHT_BITMAP = height;
	}

	public static Bitmap getBitmap(int num) {
		return bitmaps[(num + OFFSET) % PIC_NUM];
	}
	/***
	 * 混合
	 */
	public static void shalfer() {
		Random random = new Random();
		for (int i = 0; i < bitmaps.length; i++) {
			Bitmap bitmap = bitmaps[i];
			int index = random.nextInt(bitmaps.length);
			bitmaps[i] = bitmaps[index];
			bitmaps[index] = bitmap;
		}
	}
}
