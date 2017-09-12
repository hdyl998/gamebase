package com.hdyl.llk;


public class Level {
	public int score;// 得分
	public int picNum = 0;// 图片数量

	public int level;
	public int xCount;// x轴图片数量
	public int yCount;// y轴图片数量

	public float rate1 = 1f;// 密度 0.1到1

	public final static int MAX_X_COUNT = 18;
	public final static int MAX_Y_COUNT = 24;

	public final static int MIN_X_COUNT = 5;
	public final static int MIN_Y_COUNT = 6;

	public final static int MAX_LEVEL = 499;// 最大关卡

	public final static int MIN_PIC_COUNT = 5;
	public final static int ONE_PIECE = 50;

	public Level(int level) {
		this.level = level;
		rule();
	}

	// 规则
	private void rule() {
		int LEV = level / ONE_PIECE;// 0-10

		// picNum = getNum(MIN_PIC_COUNT, 36, LEV * ONE_PIECE, 75 + LEV * 100);

		int oneLevel = ONE_PIECE * LEV;

		switch (LEV) {
			case 0:
				xCount = getNum(MIN_X_COUNT, MAX_X_COUNT / 2, oneLevel, 44 + oneLevel);
				yCount = getNum(MIN_Y_COUNT, MAX_Y_COUNT / 2, oneLevel, 44 + oneLevel);
				rate1 = getNumF(0.6f, 0.8f, oneLevel, 44 + oneLevel);
				picNum = MIN_PIC_COUNT;
				break;
			case 1:
				xCount = getNum(MIN_X_COUNT + 2, MAX_X_COUNT / 2, oneLevel, 44 + oneLevel);
				yCount = getNum(MIN_Y_COUNT + 2, MAX_Y_COUNT / 2, oneLevel, 44 + oneLevel);
				picNum = Constants.PIC_NUM / 2;
				break;
			case 2:
				xCount = getNum(MIN_X_COUNT + 2, MAX_X_COUNT / 2, oneLevel, 44 + oneLevel);
				yCount = getNum(MIN_Y_COUNT + 2, MAX_Y_COUNT / 2, oneLevel, 44 + oneLevel);
				picNum = Constants.PIC_NUM;
				break;
			case 3:
				xCount = getNum(MAX_X_COUNT / 2, MAX_X_COUNT - 3, oneLevel, ONE_PIECE + oneLevel);
				yCount = getNum(MAX_Y_COUNT / 2, MAX_Y_COUNT - 3, oneLevel, ONE_PIECE + oneLevel);
				rate1 = getNumF(0.6f, 0.8f, oneLevel, 44 + oneLevel);
				picNum = MIN_PIC_COUNT;
				break;
			case 4:
				xCount = getNum(MAX_X_COUNT / 2, MAX_X_COUNT - 3, oneLevel, ONE_PIECE + oneLevel);
				yCount = getNum(MAX_Y_COUNT / 2, MAX_Y_COUNT - 3, oneLevel, ONE_PIECE + oneLevel);
				picNum = MIN_PIC_COUNT;
				break;
			case 5:
				xCount = getNum(MAX_X_COUNT / 2, MAX_X_COUNT - 3, oneLevel, ONE_PIECE + oneLevel);
				yCount = getNum(MAX_Y_COUNT / 2, MAX_Y_COUNT - 3, oneLevel, ONE_PIECE + oneLevel);
				rate1 = getNumF(0.5f, 0.8f, oneLevel, 44 + oneLevel);
				picNum = getNum(MIN_PIC_COUNT, Constants.PIC_NUM, oneLevel, ONE_PIECE + oneLevel);
				break;
			case 6:
				xCount = getNum(MAX_X_COUNT / 2, MAX_X_COUNT - 3, oneLevel, ONE_PIECE + oneLevel);
				yCount = getNum(MAX_Y_COUNT / 2, MAX_Y_COUNT - 3, oneLevel, ONE_PIECE + oneLevel);
				rate1 = getNumF(0.6f, 0.9f, oneLevel, 44 + oneLevel);
				picNum = Constants.PIC_NUM / 2;
				break;
			case 7:
				xCount = getNum(MAX_X_COUNT / 2, MAX_X_COUNT, oneLevel, ONE_PIECE + oneLevel);
				yCount = getNum(MAX_Y_COUNT / 2, MAX_Y_COUNT, oneLevel, ONE_PIECE + oneLevel);
				rate1 = getNumF(0.7f, 1f, oneLevel, 44 + oneLevel);
				picNum = Constants.PIC_NUM * 2 / 3;
				break;
			case 8:
				xCount = getNum(MAX_X_COUNT / 2, MAX_X_COUNT, oneLevel, ONE_PIECE + oneLevel);
				yCount = getNum(MAX_X_COUNT / 2, MAX_X_COUNT, oneLevel, ONE_PIECE + oneLevel);
				picNum = Constants.PIC_NUM * 2 / 3;
				break;
			default:// 9
				xCount = getNum(MAX_X_COUNT / 2, MAX_X_COUNT, oneLevel, ONE_PIECE + oneLevel);
				yCount = getNum(MAX_Y_COUNT / 2, MAX_Y_COUNT, oneLevel, ONE_PIECE + oneLevel);
				picNum = Constants.PIC_NUM;
				break;
		}
//		System.out.println(JSON.toJSONString(this));
	}

	private int getNum(float minNum, float maxNum, int startLev, int endLev) {
		if (level >= endLev) {
			return (int) maxNum;
		}
		if (level <= startLev) {
			return (int) minNum;
		}
		return (int) (minNum + (maxNum - minNum) * (level - startLev) * 1f / (endLev - startLev));
	}

	private float getNumF(float minNum, float maxNum, int startLev, int endLev) {
		if (level >= endLev) {
			return maxNum;
		}
		if (level <= startLev) {
			return maxNum;
		}
		return (minNum + (maxNum - minNum) * (level - startLev) * 1f / (endLev - startLev));
	}

	// public Level(int picNum, int score) {
	// this.picNum = picNum;
	// this.score = score;
	// }

	public Level() {
	}

	public int getValue() {
		int temp = xCount * yCount - (2 * xCount + 2 * yCount - 4);
		int pare = ((int) (temp * rate1));
		if (pare % 2 == 1) {
			pare--;
		}
//		Log.e("aa", "xCount" + xCount + " ycount" + yCount + " pare" + pare + " rate" + rate1);
		return pare;
	}
}
