package com.hdyl.mine.top;

import android.content.Context;
import android.text.TextUtils;

import com.hdyl.mine.tools.MySharepreferences;
import com.hdyl.mine.tools.ShareCacheUtil;

public class TopObject {

	public static int getTop(Context context, int lev) {
		return MySharepreferences.getInt(context, "aa", "" + lev, 100000);
	}

	/**
	 * 是否打破记录
	 *
	 * @return
	 */
	public static boolean saveTopScore(Context context, int lev, int var) {
		if (getTop(context, lev) > var) {
			MySharepreferences.putInt(context, "aa", "" + lev, var);
			return true;
		}
		return false;

	}

	public static String[] getLevelName() {
		String str[] = { "初级", "中级", "高级", "专家级", "自定义" };
		return str;
	}

	public static String getName(Context context) {
		String string = ShareCacheUtil.getString(context, "name");
		if (TextUtils.isEmpty(string)) {
			string = "寒冬已至";
		}
		if (string.length() > 10) {
			string = string.substring(0, 10);
		}
		return string;
	}

	public static void putName(Context context, String name) {
		ShareCacheUtil.putString(context, "name", name);
	}
}
