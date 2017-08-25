package com.hdyl.mine.tools;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hdyl.mine.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

	@SuppressLint("SimpleDateFormat")
	public static String getDateString() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
	public static String getSimpleDateString() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		return time;
	}
	public static void setDisableForAWhile(final View view) {
		view.setEnabled(false);
		view.postDelayed(new Runnable() {

			@Override
			public void run() {
				view.setEnabled(true);
			}
		}, 100);
	}

	public static Bitmap getBitmapFromUri(Context context, Uri uri) {

		try {
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			Log.e("my1", e.toString());
			return null;
		}
	}

	/**
	 * @return String
	 * @方法名: getVerName
	 * @功能描述:获得APK版本名称
	 */
	public static String getVerName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getPostTime(String timeStr) {
		String time = null;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr);// 2015-03-28
		} catch (ParseException e) {
			return timeStr;
		}
		long timeStrLong = date.getTime();
		Date now = new Date();
		long currentTime = now.getTime();// System.currentTimeMillis();一样的效果
		long distance = (currentTime - timeStrLong) / 1000;// 得到秒数
		long day = distance / (3600 * 24);
		long hour = distance / 3600;// 小时
		long min = distance / 60 % 60;// 分钟
		// long sec=distance%60;//秒
		if (date.getYear() != now.getYear()) {// 年不等，显示如 2015-03-28 15:37
			time = timeStr.substring(0, 16);
		} else if (!(date.getMonth() == now.getMonth() && date.getDate() == now.getDate())) {//
			// 同一年，不同月，不同日，就是指不是同一天，显示如03-28
			// 15:37
			time = timeStr.substring(5, 16);
		}
		// else if (hour > 12) { // 天等，并大于12小时
		// time = timeStr.substring(11, 16);// 年等，天等，显示15:37
		// }
		else if (hour >= 1) {
			time = hour + "小时前";
		} else if (min >= 1) {
			time = min + "分钟前";
		} else
			time = "刚刚";
		return time;
	}

	/**
	 * 设置textView drawable
	 *
	 * @param textView
	 *            控件
	 * @param resID
	 *            资源ID
	 * @param direction
	 *            方向 0-左，1-上 2-右 3下
	 */
	public static void setTextViewDrawable(TextView textView, int resID, int direction) {
		Drawable rightDrawable = textView.getResources().getDrawable(resID);
		rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
		switch (direction) {
			case 0:
				textView.setCompoundDrawables(rightDrawable, null, null, null);
				break;
			case 1:
				textView.setCompoundDrawables(null, rightDrawable, null, null);
				break;
			case 2:
				textView.setCompoundDrawables(null, null, rightDrawable, null);
				break;
			default:
				textView.setCompoundDrawables(null, null, null, rightDrawable);
				break;
		}
	}

	public static String download(String urlStr) {
		System.out.println("下载开始：" + urlStr);
		try {
			/*
			 * 通过URL取得HttpURLConnection 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
			 * <uses-permission android:name="android.permission.INTERNET" />
			 */
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setRequestProperty("contentType", "GBK");
			conn.setConnectTimeout(10 * 1000);
			// conn.setRequestMethod("GET");
			// 取得inputStream，并进行读取
			InputStream input = conn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input, "utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void showNoifatication(Context context,String tag, String title, String message) {
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		// API level 11
		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentTitle(title);
		builder.setContentText(message);
		builder.setSmallIcon(R.drawable.ic_launcher);
		Notification notification = builder.getNotification();
		manager.notify(tag, R.drawable.ic_launcher, notification);
	}
}
