package com.hdyl.pintu;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "mydata.db"; // 数据库名称
	private static final int version = 1; // 数据库版本
	private static final String TABLE = "top_score";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE + "(username varchar(20) not null , timesecond integer, isjd integer default 0, createDate varchar(20) not null );";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("DROP TABLE "+TABLE, null);
	}

	public List<UserInfo> selectInfos(int jdType, int limitNum) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE, new String[] { "username", "timesecond", "createdate", "isjd" }, getWhereType(jdType), null, null, null, "timesecond asc limit " + limitNum);
		List<UserInfo> list = new ArrayList<UserInfo>();
		while (cursor.moveToNext()) {
			UserInfo userInfo = new UserInfo();
			userInfo.username = cursor.getString(0);
			userInfo.timeSecond = cursor.getInt(1);
			userInfo.createDate = cursor.getString(2);
			userInfo.isClasscal = cursor.getInt(3) == 1;
			list.add(userInfo);
		}
		cursor.close();
		db.close();
		return list;
	}

	public int selectHighScore(boolean isRandom) {
		int jdType = 0;
		if (isRandom)
			jdType = 0;
		else {
			jdType = 1;
		}
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE, new String[] { "min(timesecond)" }, getWhereType(jdType), null, null, null, null);
		int highsore = 999;
		while (cursor.moveToNext()) {
			int num = cursor.getInt(0);
			if (num != 0) {
				highsore = num;
			}
		}
		cursor.close();
		db.close();
		return highsore;
	}

	public boolean insert(UserInfo info) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();// 实例化ContentValues
		cv.put("username", info.username);
		cv.put("timesecond", info.timeSecond);
		cv.put("createdate", Tools.getDateString());
		cv.put("isjd", info.isClasscal ? 1 : 0);
		long a = db.insert(TABLE, null, cv);
		db.close();
		return a == 1;
	}

	private String getWhereType(int jdType) {
		String jdString = "";
		switch (jdType) {
			case 0:
				jdString = "isjd=0";
				break;

			case 1:
				jdString = "isjd=1";
				break;
		}
		return jdString;
	}

	public int deleteAll(int jdType) {

		SQLiteDatabase db = getWritableDatabase();
		int len = db.delete(TABLE, getWhereType(jdType), null);
		db.close();
		return len;
	}

}