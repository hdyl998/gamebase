package com.hdyl.llk;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hdyl.baselib.base.App;
import com.hdyl.llk.utils.Tools;
import com.hdyl.mine.tools.ShareCacheUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户本地设置信息
 *
 * @author liugd
 *
 */
public class SaveData {

	public List<Level> listLevels = new ArrayList<Level>();
	public int currentLevel;
	final static String FILENAME_STRING = "gamedata.txt";
	// 静态实例
	private static SaveData instance;

	public SaveData() {

	}

	public SaveData(boolean isCreate) {
		for (int i = 0; i < Level.MAX_LEVEL; i++) {// 生成500关
			listLevels.add(new Level(i));
		}
		Log.e("aa", "生成关卡");
	}

	public void setLevelInfo(int level, int score) {
		level--;
		Level level2 = listLevels.get(level);
		level2.score = score;
		currentLevel = level;
		saveSetting();
	}

	// public SaveData(SaveData saveData) {
	// this
	// }
	// 读取设置文件
	public static SaveData getInstance() {

		if (instance == null) {
			String string = ShareCacheUtil.getString(App.getContext(), SaveData.class.toString() + "1");
			if (TextUtils.isEmpty(string)) {
				string = Tools.readFileByLines(FILENAME_STRING);
			}
			if (!TextUtils.isEmpty(string)) {
				try {
					instance = JSON.parseObject(string, SaveData.class);
				} catch (Exception e) {
					instance = new SaveData(true);
				}
			} else {
				instance = new SaveData(true);
			}
		}
		return instance;
	}

	//
	// public static void setInstanceAndSave(Context context, SettingItem
	// instance1) {
	// instance = instance1;
	// saveSetting(context);
	// }

	public List<Level> getListLevels() {
		return listLevels;
	}

	// 保存设置文件
	public static void saveSetting() {
		if (instance == null) {
			instance = new SaveData(true);
		}
		String jsString = JSON.toJSONString(instance);
		Tools.writeFileByLines(FILENAME_STRING, jsString);
		ShareCacheUtil.putString(App.getContext(), SaveData.class.toString() + "1", jsString);
	}
}
