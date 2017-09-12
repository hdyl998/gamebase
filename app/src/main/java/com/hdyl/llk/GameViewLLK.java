package com.hdyl.llk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hdyl.llk.sound.SoundPlayer;
import com.hdyl.mine.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameViewLLK extends View {

	final static int EMPTY = -1;
	int xCount = 0;
	int yCount = 0;

	public Level levelInfoLevel;
	int[][] arr;
	public List<Point> listPath = new ArrayList<Point>();// 记录路径的点
	public List<Point> selectIcon = new ArrayList<Point>();// 记录选择的点

	public Point[] pointsRecord = new Point[2];
	public Object[] listPathUser;
	boolean isInGame = false;

	public int leftTime = 0;

	// 线条厚度
	public int lineWidth = 3;

	MainLlkActivity mainActivity;

	public GameViewLLK(Context context, AttributeSet attrs) {
		super(context, attrs);
		mainActivity = (MainLlkActivity) context;
		init();
	}

	public GameViewLLK(Context context) {
		super(context);
		mainActivity = (MainLlkActivity) context;
		init();
	}

	public void setLevel(int level) {
		this.mLevel = level;

	}

	SaveData saveData = SaveData.getInstance();

	public void gameOver() {
		SoundPlayer.playSound(SoundPlayer.ID_GAME_OVER);
		isInGame = false;
		ResultDialog resultDialog = new ResultDialog(mainActivity, false, LevelAdapter.getIndex(mLevel, mScore, levelInfoLevel.getValue() / 2));
		resultDialog.show();
	}

	public void newGame() {
		this.clearAnimation();
		Animation aUtils = AnimationUtils.loadAnimation(mainActivity, R.anim.trans_in);
		this.startAnimation(aUtils);

		this.removeCallbacks(runnable2);
		postDelayed(runnable2, DelayCOUNT);

		levelInfoLevel = saveData.getListLevels().get(mLevel);
		mRefreshTimes = 3;
		isShowGameTips = false;
		xCount = levelInfoLevel.xCount;
		yCount = levelInfoLevel.yCount;

		leftTime = (int) (levelInfoLevel.getValue() * 1.6 + 10);
		mainActivity.setLeftTimeMax(leftTime);
		mainActivity.setTvRefreshNum();// 设置刷新次数
		if (WIDTH * 1f / HEIGHT > xCount * 1f / yCount) {
			SIZE = HEIGHT / yCount;
		} else {
			SIZE = WIDTH / xCount;
		}
		// Log.v("aa", xCount * SIZE + " width" + SIZE * yCount + " heigth");

		arr = new int[xCount][yCount];

		mScore = 0;// 清空得分
		Constants.OFFSET = new Random().nextInt(Constants.PIC_NUM);

		Constants.shalfer();

		mainActivity.setHighScore(levelInfoLevel.score);
		listPath.clear();
		selectIcon.clear();

		for (int i = 0; i < xCount; i++)
			for (int j = 0; j < yCount; j++)
				arr[i][j] = EMPTY;

		int degreed = levelInfoLevel.getValue();

		int x = 0;
		boolean y = false;
		aa: for (int i = 1; i < xCount - 1; i++) // 产生成对的
		{
			for (int j = 1; j < yCount - 1; j++) {

				arr[i][j] = x;
				if (y) {
					x++;
					y = false;
					if (x == levelInfoLevel.picNum)// 图片数量限制
						x = 0;
				} else
					y = true;

				degreed--;
				if (degreed == 0) {
					break aa;
				}
			}
		}
		change();
		isInGame = true;
		mainActivity.setLevel();
		mainActivity.setScore();
		requestLayout();
		invalidate();
		mainActivity.startTimer();

		int currentLevel = 0;
		if (mLevel % Level.ONE_PIECE == 0) {
			currentLevel = mLevel / Level.ONE_PIECE + 1;
			String string = getResources().getString(R.string.app_detail);
			int aaa = string.indexOf("($)".replace("$", currentLevel + ""));
			string = string.substring(aaa);
			int bbb = string.indexOf("\n");
			InfoDialog infoDialog = new InfoDialog(mainActivity, string.substring(0, bbb));
			infoDialog.show();
		}

	}

	public void change()// 随机交换
	{
		selectIcon.clear();
		Random random = new Random();
		int tmpV, tmpX, tmpY;
		int xc = xCount;
		int yc = yCount;
		for (int x = 1; x < xCount - 1; x++) {
			for (int y = 1; y < yCount - 1; y++) {
				tmpX = 1 + random.nextInt(xc - 2);
				tmpY = 1 + random.nextInt(yc - 2);
				tmpV = arr[x][y];
				arr[x][y] = arr[tmpX][tmpY];
				arr[tmpX][tmpY] = tmpV;
			}
		}
		if (die())// 死掉了
		{
			change();// 递归
		}
	}

	/************* 判断有没有死掉，要记录能连通的路径 **************/
	public boolean die() {

		for (int y = 1; y < yCount - 1; y++) {
			for (int x = 1; x < xCount - 1; x++) {
				if (arr[x][y] != EMPTY) {
					for (int j = y; j < yCount - 1; j++) {
						if (j == y) {// 两个点在同一行
							for (int i = x + 1; i < xCount - 1; i++) {
								if (arr[i][j] == arr[x][y] && link(new Point(x, y), new Point(i, j))) {
									pointsRecord[0] = new Point(i, j);
									pointsRecord[1] = new Point(x, y);
									return false;
								}
							}
						} else// 一个点在同一行，另个点在别的行
						{
							for (int i = 1; i < xCount - 1; i++) {
								if (arr[i][j] == arr[x][y] && link(new Point(x, y), new Point(i, j))) {
									pointsRecord[0] = new Point(i, j);
									pointsRecord[1] = new Point(x, y);
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	public void clearSelectIcon() {// 清掉一对图案
		Point point = selectIcon.get(0);
		arr[point.x][point.y] = EMPTY;
		point = selectIcon.get(1);
		arr[point.x][point.y] = EMPTY;
	}

	/********* 以下是核心算法 ***************/
	public boolean link(Point p1, Point p2) {// 判断两个点之间是否能相连
		if (p1.equals(p2))// 点相同
			return false;
		if (arr[p1.x][p1.y] != arr[p2.x][p2.y])// 不同返回false
			return false;

		List<Point> pathTemp = new ArrayList<Point>(listPath);// 先存起来
		listPath.clear();
		if (arr[p1.x][p1.y] == arr[p2.x][p2.y]) {// 值同，则图案相同
			if (linkD(p1, p2)) {// 两个点在同一直线上
				listPath.add(p1);
				listPath.add(p2);
				return true;
			}
			// 转一个角
			Point p = new Point(p1.x, p2.y);
			if (arr[p.x][p.y] == EMPTY) {
				if (linkD(p1, p) && linkD(p, p2)) {
					listPath.add(p1);
					listPath.add(p);
					listPath.add(p2);
					return true;
				}
			}
			p = new Point(p2.x, p1.y);
			if (arr[p.x][p.y] == EMPTY) {
				if (linkD(p1, p) && linkD(p, p2)) {
					listPath.add(p1);
					listPath.add(p);
					listPath.add(p2);
					return true;
				}
			}
			List<Point> p1E = new ArrayList<Point>();
			List<Point> p2E = new ArrayList<Point>();
			expandX(p1, p1E);// 向X方向扩展
			expandX(p2, p2E);// 向X方向扩展

			for (Point pt1 : p1E) {
				for (Point pt2 : p2E) {
					if (pt1.x == pt2.x) {
						if (linkD(pt1, pt2)) {
							listPath.add(p1);
							listPath.add(pt1);
							listPath.add(pt2);
							listPath.add(p2);
							return true;
						}
					}
				}
			}
			expandY(p1, p1E);// 向Y方向扩展
			expandY(p2, p2E);
			for (Point pt1 : p1E) {
				for (Point pt2 : p2E) {
					if (pt1.y == pt2.y) {
						if (linkD(pt1, pt2)) {
							listPath.add(p1);
							listPath.add(pt1);
							listPath.add(pt2);
							listPath.add(p2);
							return true;
						}
					}
				}
			}
		}
		listPath = pathTemp;
		return false;
	}

	private boolean linkD(Point p1, Point p2) {// 判断两个点是否在同一X轴或Y轴，且之间是否全为0
		if (p1.x == p2.x) {
			int y1 = Math.min(p1.y, p2.y);
			int y2 = Math.max(p1.y, p2.y);
			boolean flag = true;
			for (int y = y1 + 1; y < y2; y++) {
				if (arr[p1.x][y] != EMPTY) {
					flag = false;
					break;
				}
			}
			if (flag)
				return true;
		}
		if (p1.y == p2.y) {
			int x1 = Math.min(p1.x, p2.x);
			int x2 = Math.max(p1.x, p2.x);
			boolean flag = true;
			for (int x = x1 + 1; x < x2; x++) {
				if (arr[x][p1.y] != EMPTY) {
					flag = false;
					break;
				}
			}
			if (flag)
				return true;
		}
		return false;
	}

	private void expandX(Point p, List<Point> l) {// 向X方向扩展直到遇到不为0时停止
		l.clear();
		for (int x = p.x + 1; x < xCount; x++) {
			if (arr[x][p.y] != EMPTY) {
				break;
			}
			l.add(new Point(x, p.y));
		}
		for (int x = p.x - 1; x >= 0; x--) {
			if (arr[x][p.y] != EMPTY) {
				break;
			}
			l.add(new Point(x, p.y));
		}
	}

	private void expandY(Point p, List<Point> l) {// 向Y方向扩展直到遇到不为0时停止
		l.clear();
		for (int y = p.y + 1; y < yCount; y++) {
			if (arr[p.x][y] != EMPTY) {
				break;
			}
			l.add(new Point(p.x, y));
		}
		for (int y = p.y - 1; y >= 0; y--) {
			if (arr[p.x][y] != EMPTY) {
				break;
			}
			l.add(new Point(p.x, y));
		}
	}

	public boolean isWin() {// 胜利
		for (int x = 0; x < xCount; x++) {
			for (int y = 0; y < yCount; y++) {
				if (arr[x][y] != EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	private Point indexToScreenPoint(Point point) {
		return new Point(point.x * SIZE, point.y * SIZE);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (arr != null) {
			// 画线
			int offset = SIZE / 2;

			if (listPathUser != null && listPathUser.length >= 2) {
				paint.setStyle(Style.STROKE);
				for (int i = 0; i < listPathUser.length - 1; i++) {
					Point p1 = indexToScreenPoint((Point) listPathUser[i]);
					Point p2 = indexToScreenPoint((Point) listPathUser[i + 1]);
					canvas.drawLine(p1.x + offset, p1.y + offset, p2.x + offset, p2.y + offset, paint);
				}
				paint.setStyle(Style.FILL);
				// 画圆
				for (int i = 0; i < listPathUser.length; i++) {
					Point p1 = indexToScreenPoint((Point) listPathUser[i]);
					canvas.drawCircle(p1.x + offset, p1.y + offset, DensityUtil.dip2px(mainActivity, lineWidth) / 2, paint);
				}
				paint.setStyle(Style.STROKE);
				listPathUser = null;
			}
			// canvas.drawRect(new Rect(0, 0, xCount * SIZE, yCount * SIZE),
			// paint);
			// 画图
			for (int x = 0; x < xCount; x++)
				for (int y = 0; y < yCount; y++)
					if (arr[x][y] != EMPTY) {
						canvas.drawBitmap(Constants.getBitmap(arr[x][y]), null, getRect(x, y), null);
					}
			// 画选中
			for (Point point : selectIcon) {
				canvas.drawCircle(point.x * SIZE + SIZE / 2, point.y * SIZE + SIZE / 2, SIZE / 2, paint);
				// canvas.drawRect(getRect(point.x, point.y), paint);
			}

			if (curPointMove != null) {
				canvas.drawCircle(curPointMove.x, curPointMove.y, SIZE / 2, paint);
			}
			if (isShowGameTips == true) {
				paint.setColor(colorThemeGreen);
				isShowGameTips = false;
				for (Point point : pointsRecord) {
					canvas.drawRect(getRect(point.x, point.y), paint);
				}

			}
			paint.setColor(colorTheme);

		}
	}

	private Rect getRect(int x, int y) {
		return new Rect(x * SIZE, y * SIZE, (x + 1) * SIZE, (y + 1) * SIZE);
	}

	boolean isChanged = false;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		if (isChanged == false) {
			isChanged = true;
			WIDTH = w;
			HEIGHT = h;
			newGame();
			// Log.v("aa", "onSizeChanged");
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (isChanged == true) {
			setMeasuredDimension(xCount * SIZE, SIZE * yCount);
		}
		// Log.v("aa", "onMeasure");
	}

	private int WIDTH;
	private int HEIGHT;
	private int SIZE;
	Paint paint = new Paint();

	int colorTheme = Color.parseColor("#FA0000");
	int colorThemeGreen = Color.parseColor("#FB00FA");

	private void init() {
		paint.setColor(colorTheme);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(DensityUtil.dip2px(getContext(), lineWidth));
		paint.setAntiAlias(true);
	}

	private Runnable runnable2 = new Runnable() {

		@Override
		public void run() {
			isShowGameTips = true;
			GameViewLLK.this.invalidate();
		}
	};

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			GameViewLLK.this.invalidate();
		}
	};

	void countScore() {// 计算分数
		Point last = listPath.get(0);
		int len = listPath.size();
		int count = 0;
		for (int i = 1; i < len; i++) {
			Point p = listPath.get(i);
			count += Math.abs(last.x - p.x) + Math.abs(last.y - p.y);
			last = p;
		}
		Random rm = new Random();
		int curScore = rm.nextInt(10) + count * 10;// 得分是由运气分加连线长度决定的
		mainActivity.transUp(curScore);
		mScore += curScore;
		mainActivity.setScore();
		// 最佳
		if (count >= 10) {
			SoundPlayer.playSound(SoundPlayer.ID_PREFRECT);
		}
		// 设置高分
		mainActivity.setHighScore(Math.max(mScore, levelInfoLevel.score));
	}

	Point curPointMove;

	boolean isEndGame = false;// 游戏快要结束了

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isInGame == false)
			return true;
		int i = (int) (event.getX() / SIZE);
		int j = (int) (event.getY() / SIZE);

		boolean isInvate = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {// down

			} else {// up
				curPointMove = null;
				if (selectIcon.size() == 1) {
					Point point = selectIcon.get(0);
					if (i == point.x && j == point.y) {
						return true;
					}
				}
			}
			if (i < xCount && i >= 0 && j < yCount && j >= 0 && arr[i][j] != EMPTY) {// 选中的不是空白
				Point curPoint = new Point(i, j);
				if (selectIcon.size() == 1)// 已经选中了一个
				{
					if (link(selectIcon.get(0), curPoint)) // 选中的图标能连
					{
						listPathUser = listPath.toArray();
						selectIcon.add(curPoint);// 添加进来
						clearSelectIcon();// 清除其数据
						selectIcon.clear();
						countScore();// 计算得分
						if (isWin()) {
							invalidate();
							this.removeCallbacks(runnable2);
							SoundPlayer.playSound(SoundPlayer.ID_WIN);
							curPointMove = null;
							LevelActivity.isChange = true;
							isInGame = false;

							boolean isSave = false;
							boolean isPo = false;
							if (mScore > levelInfoLevel.score) {
								levelInfoLevel.score = mScore;
								InfoDialog infoDialog = new InfoDialog(mainActivity, "打破记录！您一共得到了\n " + mScore + "分!\n真了不起哈哈！", onDismissListener1);
								infoDialog.show();
								isPo = true;
								isSave = true;
							}
							int level = mLevel + 1;
							if (level > saveData.currentLevel) {
								saveData.currentLevel = level;
								isSave = true;
							}
							if (isSave == true) {
								SaveData.saveSetting();
							}
							listPathUser = null;

							if (level >= saveData.getListLevels().size()) {
								if (isPo == false) {// 没有破记录
									InfoDialog infoDialog = new InfoDialog(mainActivity, "关卡挑战完毕！感谢支持！you are the winner!", onDismissListener);
									infoDialog.show();
								} else {
									isEndGame = true;
								}
							} else {
								// mainActivity.makeText("胜利啦！自动进入下一关 LEVEL "
								// +
								// (mLevel + 1));
								// newGame();
								if (isPo == false) {// 没有破记录
									ResultDialog resultDialog = new ResultDialog(mainActivity, true, LevelAdapter.getIndex(mLevel, mScore, levelInfoLevel.getValue() / 2));
									resultDialog.show();
								}
							}
							return true;
						} else {
							// 是否死掉
							if (die()) {
								selectIcon.clear();
								change();
							}
							SoundPlayer.playSound(SoundPlayer.ID_CLEAR);
							this.removeCallbacks(runnable);
							postDelayed(runnable, DELAY);

						}
					} else// 选中图标不能连
					{
						SoundPlayer.playSound(SoundPlayer.ID_SELECT);
						selectIcon.clear();
						selectIcon.add(curPoint);

					}
				} else// 选中0个
				{
					SoundPlayer.playSound(SoundPlayer.ID_SELECT);
					selectIcon.add(curPoint);// 已选中一个
				}
				isInvate = true;
				this.removeCallbacks(runnable2);
				postDelayed(runnable2, DelayCOUNT);
			}
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
			if (selectIcon.size() == 1) {
				Point point = selectIcon.get(0);
				if (!(i == point.x && j == point.y)) {
					isInvate = true;
					if (curPointMove == null) {
						curPointMove = new Point();
					}
					// if (!(curPointMove.x == i && curPointMove.y == j)) {
					// curPointMove.x = i;
					// curPointMove.y = j;
					//
					// }
					curPointMove.x = (int) event.getX();
					curPointMove.y = (int) event.getY();
				}
			}

		}
		if (isInvate)
			invalidate();
		return true;
	}

	private OnDismissListener onDismissListener = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface arg0) {
			mainActivity.finish();
		}
	};
	private OnDismissListener onDismissListener1 = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface arg0) {
			if (isEndGame == false) {
				ResultDialog resultDialog = new ResultDialog(mainActivity, true, LevelAdapter.getIndex(mLevel, mScore, levelInfoLevel.getValue() / 2));
				resultDialog.show();
			} else {
				InfoDialog infoDialog = new InfoDialog(mainActivity, "关卡挑战完毕！感谢支持！you are the winner!", onDismissListener);
				infoDialog.show();
			}
		}
	};

	final static int DELAY = 500;
	final static int DelayCOUNT = 5000;// 倒计时
	public int mScore = 0;
	public int mLevel = 0;
	public int mRefreshTimes = 0;
	public boolean isShowGameTips = false;

}
