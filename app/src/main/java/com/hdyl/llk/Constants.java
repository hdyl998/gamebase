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

		int res[] = 		{
			R.drawable.ss_11010n,
					R.drawable.ss_11011n,
					R.drawable.ss_11020n,
					R.drawable.ss_11021n,
					R.drawable.ss_11030n,
					R.drawable.ss_11031n,
					R.drawable.ss_11040n,
					R.drawable.ss_11041n,
					R.drawable.ss_11050n,
					R.drawable.ss_11051n,
					R.drawable.ss_11060n,
					R.drawable.ss_11061n,
					R.drawable.ss_11070n,
					R.drawable.ss_11071n,
					R.drawable.ss_11080n,
					R.drawable.ss_11081n,
					R.drawable.ss_11090n,
					R.drawable.ss_11091n,
					R.drawable.ss_11100n,
					R.drawable.ss_11101n,
					R.drawable.ss_11110n,
					R.drawable.ss_11111n,
					R.drawable.ss_11120n,
					R.drawable.ss_11121n,
					R.drawable.ss_11130n,
					R.drawable.ss_11131n,
					R.drawable.ss_11140n,
					R.drawable.ss_11141n,
					R.drawable.ss_11150n,
					R.drawable.ss_11151n,
					R.drawable.ss_11280n,
					R.drawable.ss_11281n,
					R.drawable.ss_11290n,
					R.drawable.ss_11291n,
					R.drawable.ss_11300n,
					R.drawable.ss_11301n,
					R.drawable.ss_11310n,
					R.drawable.ss_11311n,
					R.drawable.ss_11360n,
					R.drawable.ss_12010n,
					R.drawable.ss_12020n,
					R.drawable.ss_12030n,
					R.drawable.ss_12040n,
					R.drawable.ss_12050n,
					R.drawable.ss_12060n,
					R.drawable.ss_12070n,
					R.drawable.ss_12080n,
					R.drawable.ss_12090n,
					R.drawable.ss_12100n,
					R.drawable.ss_12110n,
					R.drawable.ss_12120n,
					R.drawable.ss_12130n,
					R.drawable.ss_12140n,
					R.drawable.ss_12150n,
					R.drawable.ss_12240n,
					R.drawable.ss_12250n,
					R.drawable.ss_12260n,
					R.drawable.ss_12270n,
					R.drawable.ss_12360n,
					R.drawable.ss_13010n,
					R.drawable.ss_13020n,
					R.drawable.ss_13030n,
					R.drawable.ss_13040n,
					R.drawable.ss_13050n,
					R.drawable.ss_13060n,
					R.drawable.ss_13070n,
					R.drawable.ss_13080n,
					R.drawable.ss_13090n,
					R.drawable.ss_13100n,
					R.drawable.ss_13110n,
					R.drawable.ss_13120n,
					R.drawable.ss_13130n,
					R.drawable.ss_13140n,
					R.drawable.ss_13150n,
					R.drawable.ss_13360n,
					R.drawable.ss_14010n,
					R.drawable.ss_14020n,
					R.drawable.ss_14030n,
					R.drawable.ss_14040n,
					R.drawable.ss_14050n,
					R.drawable.ss_14060n,
					R.drawable.ss_14070n,
					R.drawable.ss_14080n,
					R.drawable.ss_14090n,
					R.drawable.ss_14100n,
					R.drawable.ss_14110n,
					R.drawable.ss_14120n,
					R.drawable.ss_14130n,
					R.drawable.ss_14140n,
					R.drawable.ss_14150n,
					R.drawable.ss_14360n,
					R.drawable.ss_15010n,
					R.drawable.ss_15020n,
					R.drawable.ss_15030n,
					R.drawable.ss_15040n,
					R.drawable.ss_15050n,
					R.drawable.ss_15060n,
					R.drawable.ss_15070n,
					R.drawable.ss_15080n,
					R.drawable.ss_15090n,
					R.drawable.ss_15100n,
					R.drawable.ss_15110n,
					R.drawable.ss_15120n,
					R.drawable.ss_15130n,
					R.drawable.ss_15140n,
					R.drawable.ss_15150n,
					R.drawable.ss_15320n,
					R.drawable.ss_15330n,
					R.drawable.ss_15340n,
					R.drawable.ss_15350n,
					R.drawable.ss_15360n,
					R.drawable.ss_21160n,
					R.drawable.ss_21161n,
					R.drawable.ss_21170n,
					R.drawable.ss_21171n,
					R.drawable.ss_21180n,
					R.drawable.ss_21181n,
					R.drawable.ss_21190n,
					R.drawable.ss_21191n,
					R.drawable.ss_21240n,
					R.drawable.ss_21241n,
					R.drawable.ss_21250n,
					R.drawable.ss_21251n,
					R.drawable.ss_21260n,
					R.drawable.ss_21261n,
					R.drawable.ss_21270n,
					R.drawable.ss_21271n,
					R.drawable.ss_21370n,
					R.drawable.ss_22160n,
					R.drawable.ss_22170n,
					R.drawable.ss_22180n,
					R.drawable.ss_22190n,
					R.drawable.ss_22280n,
					R.drawable.ss_22290n,
					R.drawable.ss_22300n,
					R.drawable.ss_22310n,
					R.drawable.ss_22370n,
					R.drawable.ss_23160n,
					R.drawable.ss_23170n,
					R.drawable.ss_23180n,
					R.drawable.ss_23190n,
					R.drawable.ss_23320n,
					R.drawable.ss_23330n,
					R.drawable.ss_23340n,
					R.drawable.ss_23350n,
					R.drawable.ss_23370n,
					R.drawable.ss_31200n,
					R.drawable.ss_31210n,
					R.drawable.ss_31220n,
					R.drawable.ss_31230n,
					R.drawable.ss_31280n,
					R.drawable.ss_31290n,
					R.drawable.ss_31300n,
					R.drawable.ss_31310n,
					R.drawable.ss_31380n,
					R.drawable.ss_32200n,
					R.drawable.ss_32210n,
					R.drawable.ss_32220n,
					R.drawable.ss_32230n,
					R.drawable.ss_32240n,
					R.drawable.ss_32250n,
					R.drawable.ss_32260n,
					R.drawable.ss_32270n,
					R.drawable.ss_32380n,
					R.drawable.ss_33200n,
					R.drawable.ss_33201n,
					R.drawable.ss_33210n,
					R.drawable.ss_33211n,
					R.drawable.ss_33220n,
					R.drawable.ss_33221n,
					R.drawable.ss_33230n,
					R.drawable.ss_33231n,
					R.drawable.ss_33320n,
					R.drawable.ss_33321n,
					R.drawable.ss_33330n,
					R.drawable.ss_33331n,
					R.drawable.ss_33340n,
					R.drawable.ss_33341n,
					R.drawable.ss_33350n,
					R.drawable.ss_33351n,
					R.drawable.ss_33380n
		};

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
