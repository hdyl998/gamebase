package com.hdyl.mine.game;


public class ModeItem {
	public int[][] arr;
	public int[][] arr2;
	//	public int size = 100;
	public int WIDTH = 10;
	public int HEIGHT = 10;
	public int mineNum = 10;
	public int gameType;
	public boolean isFirstClick = true;
	public int gameState = 0;// 未开始，1进行中，2游戏胜利，3，游戏结束
	public int rest;
	public int time;
	public int x;//scrollX
	public int y;//scrollY
}
