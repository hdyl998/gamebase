package com.hdyl.pintu.save;

import com.alibaba.fastjson.JSON;
import com.hdyl.baselib.base.App;
import com.hdyl.mine.tools.ShareCacheUtil;
import com.hdyl.pintu.GameView15;

/**
 * 用户本地设置信息
 *
 * @author liugd
 *
 */
public class SaveData {

	public int arr[][] = new int[GameView15.SIZE][GameView15.SIZE];
	public boolean isWin = false;
	public int countStep;
	public String stringName = "default";
	public boolean isClass = false;
	public boolean isNummode=false;
	public boolean isShowNum=false;
	public String uriImage;
	// 静态实例
	private static SaveData instance;
	public SaveData() {

	}
	//	public SaveData(SaveData saveData) {
//		this
//	}
	// 读取设置文件
	public static SaveData getInstance() {
		if (instance == null) {
			String string = ShareCacheUtil.getString(App.getContext(), SaveData.class.toString());
			if (string != null)
				instance = JSON.parseObject(string, SaveData.class);
			else {
				instance = new SaveData();
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

	// 保存设置文件
	public static void saveSetting() {
		if (instance == null) {
			instance = new SaveData();
		}
		ShareCacheUtil.putString(App.getContext(), SaveData.class.toString(), JSON.toJSONString(instance));
	}

}
