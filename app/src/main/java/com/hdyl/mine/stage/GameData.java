//package com.hdyl.mine.stage;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.alibaba.fastjson.JSON;
//import com.hdyl.baselib.base.App;
//import com.hdyl.llk.Level;
//import com.hdyl.llk.SaveData;
//import com.hdyl.llk.utils.Tools;
//import com.hdyl.mine.tools.ShareCacheUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Date:2017/10/27 16:24
// * Author:liugd
// * Modification:
// **/
//
//
//public class GameData {
//
//
//    public List<Level> listLevels = new ArrayList<Level>();
//    public int currentLevel;
//    final static String FILENAME_STRING = "gamedata_mine.txt";
//    // 静态实例
//    private static GameData instance;
//
//    public GameData() {
//
//    }
//
//    public GameData(boolean isCreate) {
//        for (int i = 0; i < Level.MAX_LEVEL; i++) {// 生成500关
//            listLevels.add(new Level(i));
//        }
//        Log.e("aa", "生成关卡");
//    }
//
//    public void setLevelInfo(int level, int score) {
//        level--;
//        Level level2 = listLevels.get(level);
//        level2.score = score;
//        currentLevel = level;
//        saveSetting();
//    }
//
//    // public SaveData(SaveData saveData) {
//    // this
//    // }
//    // 读取设置文件
//    public static GameData getInstance() {
//
//        if (instance == null) {
//            String string = ShareCacheUtil.getString(App.getContext(), GameData.class.toString() + "1");
//            if (TextUtils.isEmpty(string)) {
//                string = Tools.readFileByLines(FILENAME_STRING);
//            }
//            if (!TextUtils.isEmpty(string)) {
//                try {
//                    instance = JSON.parseObject(string, GameData.class);
//                } catch (Exception e) {
//                    instance = new GameData(true);
//                }
//            } else {
//                instance = new GameData(true);
//            }
//        }
//        return instance;
//    }
//
//    //
//    // public static void setInstanceAndSave(Context context, SettingItem
//    // instance1) {
//    // instance = instance1;
//    // saveSetting(context);
//    // }
//
//    public List<Level> getListLevels() {
//        return listLevels;
//    }
//
//    // 保存设置文件
//    public static void saveSetting() {
//        if (instance == null) {
//            instance = new GameData(true);
//        }
//        String jsString = JSON.toJSONString(instance);
//        Tools.writeFileByLines(FILENAME_STRING, jsString);
//        ShareCacheUtil.putString(App.getContext(), SaveData.class.toString() + "1", jsString);
//    }
//}
