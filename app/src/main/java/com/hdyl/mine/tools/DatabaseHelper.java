package com.hdyl.mine.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "game.db"; // 数据库名称
	private static final int version = 1; // 数据库版本
	private static final String TABLE = "mine_data";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, version);
		// TODO Auto-generated constructor stub
	}

	String ARR_STRINGS[] = { "level", "second", "name", "createtime" };

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE + "(level integer default 0 ,second integer default 0 , name varchar(10) not null,createtime varchar(10) not null);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE, null);
	}

	// public LevelInfo selectInfos(int level) {
	// SQLiteDatabase db = getReadableDatabase();
	// Cursor cursor = db.query(TABLE, new String[] { "level", "levelstring",
	// "beststep" }, "level=" + level, null, null, null, null);
	// LevelInfo userInfo =null;
	// while (cursor.moveToNext()) {
	// userInfo= new LevelInfo();
	// userInfo.level = cursor.getInt(0);
	// userInfo.levelString = cursor.getString(1);
	// userInfo.bestStep = cursor.getInt(2);
	// break;
	// }
	// cursor.close();
	// db.close();
	// return userInfo;
	// }

	public boolean updateInfo(GameInfo info) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();// 实例化ContentValues
		cv.put("level", info.level);
		cv.put("second", info.second);
		cv.put("name", info.name);
		cv.put("createtime", info.createTime);
		db.update(TABLE, cv, "level=" + info.level, null);
		return true;
	}

	public List<GameInfo> selectAllInfos(int level) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE, ARR_STRINGS, "level=" + level, null, null, null, "second asc limit 500");
		List<GameInfo> list = new ArrayList<GameInfo>();
		while (cursor.moveToNext()) {
			GameInfo userInfo = new GameInfo();
			userInfo.level = cursor.getInt(0);
			userInfo.second = cursor.getInt(1);
			userInfo.name = cursor.getString(2);
			userInfo.createTime = cursor.getString(3);
			list.add(userInfo);
		}
		cursor.close();
		db.close();
		return list;
	}

	public float selectFightMan(int level, int time) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from " + TABLE + " where level=" + level + " and second>" + time, null);
		int total = 0, current = 0;
		while (cursor.moveToNext()) {
			current = cursor.getInt(0);

		}
		cursor = db.rawQuery("select count(*) from " + TABLE + " where level=" + level, null);
		while (cursor.moveToNext()) {
			total = cursor.getInt(0);
		}
		Log.v("aa", "total "+total+"  current "+current);
		cursor.close();
		db.close();
		if (total == 0)
			return 100;
		return current* 100 / total ;
	}

	public boolean insert(GameInfo info) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();// 实例化ContentValues
		cv.put("level", info.level);
		cv.put("second", info.second);
		cv.put("name", info.name);
		cv.put("createtime", info.createTime);
		long a = db.insert(TABLE, null, cv);
		db.close();
		return a >= 1;
	}

	public int deleteAll(int jdType) {
		SQLiteDatabase db = getWritableDatabase();
		int len = db.delete(TABLE, null, null);
		db.close();
		return len;
	}

}