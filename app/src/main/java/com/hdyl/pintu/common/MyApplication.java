//package com.hdyl.pintu.common;
//
//import android.app.Application;
//import android.content.Context;
//import android.util.DisplayMetrics;
//import android.view.WindowManager;
//
//public class MyApplication extends Application {
//
//	public static MyApplication instance;
//
//	@Override
//	public void onCreate() {
//		super.onCreate();
//		instance = this;
//		getScreenHW(this);
//	}
//
//	public static MyApplication getInstance() {
//		return instance;
//	}
//
//	public void getScreenHW(Context context) {
//		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//		// Display display = manager.getDefaultDisplay();
//		// int width =display.getWidth();
//		// int height=display.getHeight();
//
//		DisplayMetrics dm = new DisplayMetrics();
//		manager.getDefaultDisplay().getMetrics(dm);
//		int width2 = dm.widthPixels;
//		int height2 = dm.heightPixels;
//		WIDTH = width2;
//	}
//
//	public static int WIDTH = 0;
//
//}
