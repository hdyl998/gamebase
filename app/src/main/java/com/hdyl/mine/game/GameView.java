package com.hdyl.mine.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hdyl.mine.MineItem;
import com.hdyl.mine.R;
import com.hdyl.mine.base.LoadingDialog;
import com.hdyl.mine.base.ScoreUtils;
import com.hdyl.mine.set.AppSet;
import com.hdyl.mine.set.SetActivity;
import com.hdyl.mine.tools.DatabaseHelper;
import com.hdyl.mine.tools.GameInfo;
import com.hdyl.mine.tools.MySharepreferences;
import com.hdyl.mine.tools.ShareCacheUtil;
import com.hdyl.mine.tools.ToastUtils;
import com.hdyl.mine.top.TopObject;

import java.util.Random;
import java.util.Stack;

public class GameView extends View {

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameActivity = (GameActivity) context;
    }

    public GameView(Context context) {
        super(context);
        gameActivity = (GameActivity) context;
    }

    MineItem mineItem;

    public void init(MineItem item) {
        this.mineItem = item;
        if (item == null) {
            helper = new DatabaseHelper(gameActivity);
        }
        boolean isOnVer = MySharepreferences.getInt(gameActivity, "aa", "vibrator") == 0;// 0表示打开的
        if (isOnVer) {
            // 震动
            vibrator = (Vibrator) gameActivity.getSystemService(Service.VIBRATOR_SERVICE);
        }
        isFlag = MySharepreferences.getBoolean(gameActivity, "aa", "isFlag");

        int []ids = null;
        if (AppSet.getInstence().theme == 0) {
            ids = new int[]{R.drawable.open, R.drawable._1, R.drawable._2, R.drawable._3, R.drawable._4, R.drawable._5, R.drawable._6, R.drawable._7, R.drawable._8, R.drawable.mine,// 9雷
                    R.drawable.unopen,// 10打开的
                    R.drawable.flag,// 标志11
                    R.drawable.mineclick,// 点到雷12
                    R.drawable.minewrong,// 非雷，但标记错误13
            };
        } else if (AppSet.getInstence().theme == 1) {
            ids = new int[]{R.drawable.mine_open_normal, R.drawable.mine_open_1, R.drawable.mine_open_2, R.drawable.mine_open_3, R.drawable.mine_open_4, R.drawable.mine_open_5, R.drawable.mine_open_6, R.drawable.mine_open_7, R.drawable.mine_open_8, R.drawable.bomb_revealed,// 9雷
                    R.drawable.mine_normal,// 10没开的
                    R.drawable.i_flag_down,// 标志11
                    R.drawable.bomb_death,// 点到雷12
                    R.drawable.bomb_mis_flagged,// 非雷，但标记错误13
            };
        }
        bitmaps = new Bitmap[ids.length];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), ids[i]);
        }

        if (AppSet.getInstence().theme == 1) {
            for (int i = 1; i < 11; i++) {
                bitmaps[i] = reDrawBitmap(bitmaps[i], bitmaps[0]);
            }
            bitmaps[13] = reDrawBitmap(bitmaps[13], bitmaps[0]);
            bitmaps[11] = reDrawBitmap(bitmaps[11], bitmaps[10]);
        }

        String str = ShareCacheUtil.getString(gameActivity, "size");
        if (str == null) {
            str = "4";
        }
        int var = Integer.parseInt(str);
        float times = (var - 5) * 0.2f + 1.5f;
        size = (int) (bitmaps[0].getWidth() * times);

    }

    final static int ID_COVER = 10;
    final static int ID_FLAG = 11;
    final static int ID_ERROR_MINE = 12;
    final static int ID_ERROR_BLACK = 13;

    final static int ID2_COVER = 0;


    /**
     * 重新绘制BITMAP
     *
     * @param source
     * @param bgBitmap
     */
    private Bitmap reDrawBitmap(Bitmap source, Bitmap bgBitmap) {
        Bitmap output = Bitmap.createBitmap(bgBitmap.getWidth(), bgBitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Rect rect = new Rect(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        canvas.drawBitmap(bgBitmap, rect, rect, null);
        canvas.drawBitmap(source, rect, rect, null);
        return output;
    }

    boolean isFisrt = false;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Log.e("aa", "onSizeChanged");
        if (isFisrt == false) {
            isFisrt = true;
            loadConfig();
            initMap();
            this.post(new Runnable() {
                @Override
                public void run() {
                    GameView.this.requestLayout();
                }
            });
        }
    }

    /**
     * 加载配置
     */
    public void loadConfig() {
        int type = MySharepreferences.getInt(gameActivity, "aa", "settype");
        if (mineItem != null) {
            type = 5;
        }
        gameType = type;
        switch (type) {
            case 0:
                this.setWidthHeight(8, 8, 10);// 10,10,8 0.08
                break;// 0.15625
            case 1:
                this.setWidthHeight(16, 16, 40);// 20,20,40 0.1
                break;// 0.15625
            case 2:
                this.setWidthHeight(16, 30, 99);// 20 40 100 0.125
                break;// 0.20625
            case 3:
                this.setWidthHeight(80, 80, 1000);// 0.15625
                break;
            case 4:// 自定义
                int width = MySharepreferences.getInt(gameActivity, "aa", "width");
                int height = MySharepreferences.getInt(gameActivity, "aa", "height");
                int num = MySharepreferences.getInt(gameActivity, "aa", "num");
                int []arr = MineUtils.checkCorrectUserDefineMineNum(width, height, num);
                width = arr[0];
                height = arr[1];
                num = arr[2];
                this.setWidthHeight(width, height, num);
                break;
            case 5://
                this.setWidthHeight(mineItem.width, mineItem.height, mineItem.mineNum);
                break;

        }
        gameActivity.setLevelText(type);
    }

    /**
     * 设置宽度高度
     *
     * @param width
     * @param height
     */
    public void setWidthHeight(int width, int height, int mine) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.mineNum = mine;
    }

    /**
     * 创建地图根据第一次按下去生成地图
     *
     * @param row
     * @param col
     */
    public void createMap(int row, int col) {
        /**
         * 以按下去为中心的9格子全黑金
         */
        for (int x = row - 1; x < row + 2; x++)
            for (int y = col - 1; y < col + 2; y++) {
                if (isIn(x, y)) {
                    arr[x][y] = -1;
                }
            }

        // 交换
        Random random1 = new Random();
        Random random2 = new Random();

        // 初始化
        int count = 0;

        int r1;
        int c1;
        while (count < mineNum) {
            r1 = random1.nextInt(WIDTH);
            c1 = random2.nextInt(HEIGHT);
            if (arr[r1][c1] != -1 && arr[r1][c1] != 9) {
                arr[r1][c1] = 9;
                count++;
            }
        }

        for (int x = row - 1; x < row + 2; x++)
            for (int y = col - 1; y < col + 2; y++) {
                if (isIn(x, y)) {
                    arr[x][y] = 0;
                }
            }
        // 计算数值
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (arr[i][j] != 9) {
                    for (int x = i - 1; x < i + 2; x++)
                        for (int y = j - 1; y < j + 2; y++) {
                            if (isIn(x, y))
                                if (arr[x][y] == 9)
                                    arr[i][j]++;
                        }
                }
            }
        }
    }

    public void initMap() {
        arr = new int[WIDTH][HEIGHT];
        arrCover = new int[WIDTH][HEIGHT];// 0 COVER 1 OPEN 2 COVER WITH_FLAG
        gameState = STATE_NO_START;
        gameActivity.initGameActivity();
        isFirstClick = true;
        pointsChecked.clear();// 检查过的
        rest = this.mineNum;
    }

    private boolean isIn(int i, int j)// 检查是否是出界了
    {
        if (i > -1 && i < WIDTH && j > -1 && j < HEIGHT)
            return true;
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = null;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (arrCover[i][j] == 0) {
                    bitmap = bitmaps[ID_COVER];
                } else if (arrCover[i][j] == 2) {
                    bitmap = bitmaps[ID_FLAG];
                } else {
                    bitmap = bitmaps[arr[i][j]];
                }
                rrRect.set(i * size, j * size, i * size + size, j * size + size);// 消除警告
                canvas.drawBitmap(bitmap, null, rrRect, null);
            }
        }
    }

    Rect rrRect = new Rect();
    int row, col;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                row = (int) (event.getX() / size);
                col = (int) (event.getY() / size);
                break;
            case MotionEvent.ACTION_UP:
                int r = (int) (event.getX() / size);
                int c = (int) (event.getY() / size);
                if (r == row && c == col) {
                    if (gameState == STATE_LOSE || gameState == STATE_WIN) {
                        if (gameState == STATE_LOSE) {
                            showGameInfo(GameMsgInfo.LOSED);
                        } else {
                            showGameInfo(GameMsgInfo.WINED);
                        }
                        return true;
                    }
                    // 点击了NUM，打的的数字
                    if (arrCover[r][c] == 1 && arr[r][c] > 0 && arr[r][c] < 9) {// 点数字
                        clickNumber(r, c);
                        gameActivity.playSound();
                        return true;
                    }

                    if (isFlag == true && isFirstClick == false) {
                        if (arrCover[r][c] == 0) {// 覆盖着的
                            arrCover[r][c] = 2;
                            rest--;
                            gameActivity.setMineNum(rest);
                            gameActivity.playSound();
                            invalidate();
                        } else if (arrCover[r][c] == 2) {
                            arrCover[r][c] = 0;
                            rest++;
                            gameActivity.setMineNum(rest);
                            gameActivity.playSound();
                            invalidate();
                        }
                    } else {

                        if (arrCover[r][c] == 0) {// 表示覆盖着的
                            if (isFirstClick == true) {
                                isFirstClick = false;
                                gameState = STATE_IN_GAME;
                                createMap(r, c);
                                gameActivity.startTimer();
                            }
                            openArea(r, c);// 打开
                            if (isWin()) {
                            }
                            gameActivity.playSound();
                            invalidate();
                        }
                    }

                }
                break;
        }
        return true;
    }

    Stack<Point> pointsReady2Checked = new Stack<Point>();// 待检查
    Stack<Point> pointsChecked = new Stack<Point>();// 已查

    public void check(int i, int j) {
        for (int x = i - 1; x < i + 2; x++)
            for (int y = j - 1; y < j + 2; y++) {
                if (isIn(x, y) && arrCover[x][y] == 0) {// 检查9格没出界
                    arrCover[x][y] = 1;
                    if (arr[x][y] == 0) {
                        Point point = new Point(x, y);
                        if (!pointsChecked.contains(point))
                            pointsReady2Checked.push(point);
                    } else if (arr[x][y] == 9) {
                        click9(x, y);
                    }
                }
            }
    }

    /**
     * 打开区域
     *
     * @param i
     * @param j
     */
    private void openArea(int i, int j) {
        if (gameState == STATE_LOSE) {
            return;
        }
        arrCover[i][j] = 1;
        if (arr[i][j] == 0) {
            pointsReady2Checked.clear();
            pointsChecked.push(new Point(i, j));// 已查i,j
            check(i, j);// 检查i,j
            while (!pointsReady2Checked.isEmpty() && gameState != STATE_LOSE) {
                Point p = pointsReady2Checked.pop();
                check(p.x, p.y);// 检查i,j
            }
            pointsChecked.clear();
            // for (int x = i - 1; x < i + 2; x++)
            // for (int y = j - 1; y < j + 2; y++) {
            // if (isIn(x, y) && arrCover[x][y] == 0) {// 检查9格没出界
            // arrCover[x][y] = 1;
            // if (arr[x][y] == 0)
            // openArea(x, y);
            // else if (arr[x][y] == 9) {
            // click9(x, y);
            // }
            // }
            // }
        } else if (arr[i][j] == 9) {
            click9(i, j);
        }
    }

    private void click9(int i1, int j1) {
        arr[i1][j1] = ID_ERROR_MINE;
        arrCover[i1][j1] = 1;// 打开了
        gameState = STATE_LOSE;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (arrCover[i][j] == 2 && arr[i][j] != 9) {// 标记是雷但实际不是雷，则被打开
                    arr[i][j] = ID_ERROR_BLACK;
                    arrCover[i][j] = 1;// 打开了
                }
                if (arrCover[i][j] == 0 && arr[i][j] == 9) {
                    arrCover[i][j] = 1;// 打开
                }
            }
        }
        if (vibrator != null)
            vibrator.vibrate(200);
        showGameInfo(GameMsgInfo.LOSED);
        gameActivity.cancelTimer(false);
        gameActivity.initGameStateUI();
    }

    private boolean isOpenWithNum(int i, int j, int num) {
        return arr[i][j] == num && arrCover[i][j] == COVER_OPEN;
    }

    private boolean isCover(int i, int j) {
        return arrCover[i][j] == COVER_COVER || arrCover[i][j] == COVER_FLAG;
    }

    // 寻找特殊雷标志1
    private Point getAimPoint(int i, int j) {
        int count = 0;
        int xx = 0, yy = 0;
        for (int x = i - 1; x < i + 2; x++)
            for (int y = j - 1; y < j + 2; y++) {
                if (!isIn(x, y)) {// 检查9格没出界,且检查标记数量
                    return null;
                }
                if (isCover(x, y)) {
                    count++;
                    if (count > 1)
                        return null;
                    xx = x;
                    yy = y;
                }
            }
        return new Point(xx, yy);
    }

    public boolean isInGame() {
        return gameState == STATE_IN_GAME;
    }

    // some special easy ai
    public void aiCalc() {
        // 检查1的对角情况
        int count = 0;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Point point = null;
                if (isOpenWithNum(i, j, 1)) {
                    if ((point = getAimPoint(i, j)) != null && arrCover[point.x][point.y] != COVER_FLAG) {
                        arrCover[point.x][point.y] = COVER_FLAG;
                        Log.e("aa", point + "");
                        count++;
                    }
                }
            }
        }
        // 检查121的情况
        for (int i = 0; i < WIDTH - 2; i++) {
            for (int j = 0; j < HEIGHT - 2; j++) {
                // 竖的
                if (isOpenWithNum(i, j, 1) && isOpenWithNum(i, j + 1, 2) && isOpenWithNum(i, j + 2, 1)) {
                    int offset = 1;

                    int offsetB = 1;
                    if (isCover(i + 1, j)) {//
                        offset = 1;
                        offsetB = -1;
                    } else {
                        offset = -1;
                        offsetB = 1;
                    }
                    if (COVER_FLAG == arrCover[i + offset][j] && COVER_FLAG == arrCover[i + offset][j + 2]) {
                        continue;
                    }
                    // // 越界认为可行,不越界对三个中有一个不为open，不操作
                    if (isIn(i + offsetB, j) && (arrCover[i + offsetB][j] != COVER_OPEN || arrCover[i + offsetB][j + 1] != COVER_OPEN || arrCover[i + offsetB][j + 2] != COVER_OPEN)) {
                        continue;
                    }
                    if (COVER_FLAG != arrCover[i + offset][j]) {
                        count++;
                    }
                    if (COVER_FLAG != arrCover[i + offset][j + 2]) {
                        count++;
                    }
                    Log.e("aa", i + " " + (j + 1));
                    arrCover[i + offset][j] = COVER_FLAG;
                    arrCover[i + offset][j + 1] = COVER_OPEN;
                    arrCover[i + offset][j + 2] = COVER_FLAG;

                    // clickNumber(i + offset, j + 1);// 打开
                    // clickNumber(i, j);// 打开
                    // clickNumber(i, j + 2);// 打开
                } else if (isOpenWithNum(i, j, 1) && isOpenWithNum(i + 1, j, 2) && isOpenWithNum(i + 2, j, 1)) {
                    // 横的
                    int offset = 1;
                    int offsetB = 1;
                    if (isCover(i, j + 1)) {//
                        offset = 1;
                        offsetB = -1;
                    } else {
                        offset = -1;
                        offsetB = 1;
                    }

                    // 两个中有一个曾标记过不作处理
                    if (COVER_FLAG == arrCover[i][j + offset] && COVER_FLAG == arrCover[i + 2][j + offset]) {
                        continue;
                    }
                    // 越界认为可行,不越界对三个中有一个不为open，不操作
                    if (isIn(i, j + offsetB) && (arrCover[i][j + offsetB] != COVER_OPEN || arrCover[i + 1][j + offsetB] != COVER_OPEN || arrCover[i + 2][j + offsetB] != COVER_OPEN)) {
                        continue;
                    }
                    if (COVER_FLAG != arrCover[i][j + offset]) {
                        count++;
                    }
                    if (COVER_FLAG != arrCover[i + 2][j + offset]) {
                        count++;
                    }

                    Log.e("aa", i + " " + (j + offset));
                    arrCover[i][j + offset] = COVER_FLAG;
                    arrCover[i + 1][j + offset] = COVER_OPEN;
                    arrCover[i + 2][j + offset] = COVER_FLAG;

                    // clickNumber(i + 1, j + offset);// 打开
                    // clickNumber(i, j);// 打开
                    // clickNumber(i + 2, j);// 打开
                }
            }
        }

        // 检查3的情况
        for (int i = 0; i < WIDTH - 2; i++) {
            for (int j = 0; j < HEIGHT - 2; j++) {
                // 竖的
                if (isOpenWithNum(i, j + 1, 3) && isCover(i, j) == false && isCover(i, j + 2) == false) {
                    int offset = 1;
                    int offsetB = 1;
                    if (isCover(i + 1, j)) {
                        offset = 1;
                        offsetB = -1;
                    } else {
                        offset = -1;
                        offsetB = 1;
                    }
                    if (COVER_FLAG == arrCover[i + offset][j] && COVER_FLAG == arrCover[i + offset][j + 2] && COVER_FLAG == arrCover[i + offset][j + 1]) {
                        continue;
                    }
                    // // 越界认为可行,不越界对三个中有一个不为open，不操作
                    if (isIn(i + offsetB, j) && (arrCover[i + offsetB][j] != COVER_OPEN || arrCover[i + offsetB][j + 1] != COVER_OPEN || arrCover[i + offsetB][j + 2] != COVER_OPEN)) {
                        continue;
                    }
                    if (COVER_FLAG != arrCover[i + offset][j]) {
                        count++;
                    }
                    if (COVER_FLAG != arrCover[i + offset][j + 1]) {
                        count++;
                    }
                    if (COVER_FLAG != arrCover[i + offset][j + 2]) {
                        count++;
                    }
                    Log.e("aa", i + " " + (j + 1));
                    arrCover[i + offset][j] = COVER_FLAG;
                    arrCover[i + offset][j + 1] = COVER_FLAG;
                    arrCover[i + offset][j + 2] = COVER_FLAG;

                    // clickNumber(i + offset, j + 1);// 打开
                    // clickNumber(i, j);// 打开
                    // clickNumber(i, j + 2);// 打开
                } else if (isOpenWithNum(i + 1, j, 3) && isCover(i, j) == false && isCover(i + 2, j) == false) {
                    // 横的
                    int offset = 1;
                    int offsetB = 1;
                    if (isCover(i, j + 1)) {//
                        offset = 1;
                        offsetB = -1;
                    } else {
                        offset = -1;
                        offsetB = 1;
                    }

                    // 两个中有一个曾标记过不作处理
                    if (COVER_FLAG == arrCover[i][j + offset] && COVER_FLAG == arrCover[i + 2][j + offset] && COVER_FLAG == arrCover[i + 1][j + offset]) {
                        continue;
                    }
                    // 越界认为可行,不越界对三个中有一个不为open，不操作
                    if (isIn(i, j + offsetB) && (arrCover[i][j + offsetB] != COVER_OPEN || arrCover[i + 1][j + offsetB] != COVER_OPEN || arrCover[i + 2][j + offsetB] != COVER_OPEN)) {
                        continue;
                    }
                    if (COVER_FLAG != arrCover[i][j + offset]) {
                        count++;
                    }
                    if (COVER_FLAG != arrCover[i + 1][j + offset]) {
                        count++;
                    }
                    if (COVER_FLAG != arrCover[i + 2][j + offset]) {
                        count++;
                    }

                    arrCover[i][j + offset] = COVER_FLAG;
                    arrCover[i + 1][j + offset] = COVER_FLAG;
                    arrCover[i + 2][j + offset] = COVER_FLAG;

                    // clickNumber(i + 1, j + offset);// 打开
                    // clickNumber(i, j);// 打开
                    // clickNumber(i + 2, j);// 打开
                }
            }
        }

        if (count != 0) {
            rest -= count;
            gameActivity.setMineNum(rest);
            invalidate();
            LoadingDialog dialog = new LoadingDialog(gameActivity, "使用AI技能成功~\nhaha~\n标记了【$】颗雷！\n节省时间 # 秒！".replace("$", "" + count).replace("#", count * 1.23 + ""));
            dialog.setIconResID(R.drawable.ic_smill);
            dialog.show();
        } else {
            String aaaString = "使用AI技能失败！\n技能点无法满足~";
            LoadingDialog dialog1 = new LoadingDialog(gameActivity, aaaString);
            dialog1.setIconResID(R.drawable.ic_sad);
            dialog1.show();
            // ToastUtils.makeTextAndShow(gameActivity, aaaString);
        }
    }

    // 点击了数字
    private void clickNumber(int i, int j) {
        int count = 0;
        for (int x = i - 1; x < i + 2; x++)
            for (int y = j - 1; y < j + 2; y++) {
                if (isIn(x, y) && arrCover[x][y] == 2) {// 检查9格没出界,且检查标记数量
                    count++;
                }
            }
        boolean isIn = false;
        if (arr[i][j] == count) {// 相同
            for (int x = i - 1; x < i + 2; x++)
                for (int y = j - 1; y < j + 2; y++) {
                    if (isIn(x, y) && arrCover[x][y] == 0) {// 检查9格没出界,且检查标记数量
                        isIn = true;
                        openArea(x, y);
                    }
                }
        }
        if (isIn) {
            if (isWin()) {

            }
            invalidate();
        }
    }

    private boolean isWin() {
        if (gameState == STATE_LOSE) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++) {
                if (arrCover[i][j] == 0 || arrCover[i][j] == 2)
                    count++;
            }

        if (count == mineNum) {
            for (int i = 0; i < WIDTH; i++)
                for (int j = 0; j < HEIGHT; j++) {
                    if (arrCover[i][j] == 0)// 剩下没标记的全部做标记
                        arrCover[i][j] = 2;
                }
            gameState = STATE_WIN;
            gameActivity.cancelTimer(false);
            gameActivity.initGameStateUI();
            if (gameType < 4) {
                String name = TopObject.getName(gameActivity);
                boolean isPo = TopObject.saveTopScore(gameActivity, gameType, gameActivity.time);
                gameActivity.setLevelText(gameType);
                if (isPo) {// 破记录
                    showGameInfo(GameMsgInfo.PO);
                } else {
                    showGameInfo(GameMsgInfo.WIN);
                }
                helper.insert(new GameInfo(gameType, gameActivity.time, name));
            } else {
                if (gameActivity.time != 0)// 为0时很B，胡舍去了
                    if (gameActivity.time < gameActivity.highScore) {
                        gameActivity.highScore = gameActivity.time;
                        gameActivity.setLevelText(gameType);
                    }
                showGameInfo(GameMsgInfo.WINDEFILE);
                if (gameType == 5) {
                    gameActivity.setResult(Activity.RESULT_OK);
                }
            }
            gameActivity.setPercentText(100);
            return true;
        } else {
            gameActivity.setPercentText(100 - count * 100f / (WIDTH * HEIGHT));
        }
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size * WIDTH, size * HEIGHT);

        // Log.e("aa", "onMeasure");

    }

    public void showGameInfo(GameMsgInfo type) {
        String msg = "";
        int res = R.drawable.ic_smill;
        switch (type) {
            case LOSED:
                msg = "游戏结束！";
                res = R.drawable.ic_sad;
                break;
            case WINED:
                msg = "你已通关了！";
                break;
            case WIN:
                msg = "通关了！\n通关时间：" + gameActivity.time / 10f + " s\n你打败了 " + helper.selectFightMan(gameType, gameActivity.time) + "% 的用户";
                break;
            case PO:
                msg = "打破记录！\n最佳记录为: " + gameActivity.time / 10f + " s";
                ToastUtils.makeTextAndShow(gameActivity, msg);
                final int ressss = res;
                LoadingDialog dialog2 = new LoadingDialog(gameActivity, msg, "好开心", "哈哈", new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        LoadingDialog dialog = new LoadingDialog(gameActivity, "陛下龙颜大悦，打破了游戏记录，是否起驾去360市场好给个好评啦~ ^_^", "五星好评", "以后再评", new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                switch (arg0.getId()) {
                                    case R.id.button1:
                                        ScoreUtils.getToMarket(gameActivity);
                                        break;
                                    case R.id.button2:
                                        break;
                                }
                            }
                        });
                        dialog.setIconResID(ressss);
                        dialog.show();
                    }
                });
                dialog2.setIconResID(ressss);
                dialog2.setCancelable(false);
                dialog2.show();
                break;
            case WINDEFILE:
                msg = "通关了！\n通关时间：" + gameActivity.time / 10f + " s";
                break;
        }
        LoadingDialog dialog = new LoadingDialog(gameActivity, msg, "新开始", "关闭", clickListener);
        dialog.setIconResID(res);
        dialog.show();
    }

    /**
     * 按扭点击事件
     */
    private OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.button1:
                    gameActivity.newGame();
                    break;
                case R.id.button2:
                    break;
            }
        }
    };

    /***
     * 获得比率
     *
     * @return
     */
    public float getRate() {
        return mineNum / (1f * WIDTH * HEIGHT);
    }

    public enum GameMsgInfo {
        LOSED, WINED, WIN, PO, WINDEFILE;// 失败，已成功，赢，破记录,赢自定义
    }

    public void setModeItem(ModeItem item) {
        // Log.e("aa", JSON.toJSONString(item));
        this.arr = item.arr;
        this.arrCover = item.arr2;
        // this.size = item.size;
        this.WIDTH = item.WIDTH;
        this.HEIGHT = item.HEIGHT;
        this.mineNum = item.mineNum;
        this.gameType = item.gameType;
        this.isFirstClick = item.isFirstClick;
        this.gameState = item.gameState;
        this.rest = item.rest;
        setWidthHeight(WIDTH, HEIGHT, mineNum);
        requestLayout();
        gameActivity.setLevelText(gameType);
        gameActivity.initGameStateUI();
        gameActivity.setMineNum(rest);
        gameActivity.setPercentText(0f);
        gameActivity.startTimer();
        gameActivity.time = item.time;

        gameActivity.scrollView2.scrollTo(item.x, 0);
        gameActivity.scrollView1.scrollTo(0, item.y);
        isWin();
        this.invalidate();

    }

    public ModeItem getModeItem() {
        ModeItem item = new ModeItem();
        item.arr = this.arr;
        item.arr2 = this.arrCover;
        // item.size = this.size;
        item.WIDTH = this.WIDTH;
        item.HEIGHT = this.HEIGHT;
        item.mineNum = this.mineNum;
        item.gameType = this.gameType;
        item.isFirstClick = this.isFirstClick;
        item.gameState = this.gameState;
        item.rest = this.rest;
        item.time = gameActivity.time;
        item.x = gameActivity.scrollView2.getScrollX();
        item.y = gameActivity.scrollView1.getScrollY();
        return item;
    }

    int[][] arr;// 数字数组
    int[][] arrCover;// 标记数组
    Bitmap bitmap;
    int size = 100;
    Bitmap[] bitmaps;

    int WIDTH = 10;
    int HEIGHT = 10;
    int mineNum = 10;
    public int gameType;
    public boolean isFlag = false;

    private Vibrator vibrator;
    DatabaseHelper helper;

    public boolean isFirstClick = true;
    public int gameState = 0;// 未开始，1进行中，2游戏胜利，3，游戏结束
    public int rest;
    GameActivity gameActivity;
    public final static int STATE_NO_START = 0;
    public final static int STATE_IN_GAME = 1;
    public final static int STATE_WIN = 2;
    public final static int STATE_LOSE = 3;

    public final static int COVER_COVER = 0;
    public final static int COVER_OPEN = 1;
    public final static int COVER_FLAG = 2;
}
