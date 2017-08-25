package com.hdyl.mine.tools;

public class GameInfo {

	public int level;// 0--初 1--中 2--高 3--专家级
	public int second;// 时间成绩
	public String name;// 创建时间
	public String createTime;// 创建时间

	public GameInfo(){

	}

	public GameInfo(int level, int second, String name) {
		this.level = level;
		this.second = second;
		this.name = name;
		this.createTime=Tools.getDateString();
	}
}
