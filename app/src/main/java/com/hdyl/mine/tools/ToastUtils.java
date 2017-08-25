package com.hdyl.mine.tools;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	private static Toast mToast;

	public static void makeTextAndShow(Context context, String content, int time) {
		if (mToast == null) {
			mToast = Toast.makeText(context, content, time);
		} else {
			mToast.setDuration(time);
			mToast.setText(content);
		}
		mToast.show();
	}

	public static void makeTextAndShow(Context context, String content) {
		makeTextAndShow(context, content, 0);
	}


}
