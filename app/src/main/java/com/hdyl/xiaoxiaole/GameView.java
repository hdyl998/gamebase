package com.hdyl.xiaoxiaole;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hdyl.mine.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {
    private int[][] map;//数组地图
    private int SWidth = 20;//图片宽度
    private int SHeight = 20;//图片高度
    private Bitmap[] bts;//图片数组
    private int iconCount = 5;//图片数量
    private int cCount = 12;//x轴上摆20个
    private int rCount = 16;//y轴上摆10个
    private int noPicture = -1;//不绘制的地方，就是已经消除的地方
    private List<Point> listPoint = new ArrayList<Point>();//记录可以消除的点的

    private int curType = -1;//当前选定的水果类型

    private int msgNotice = 0;//通知画点消息


    private int score;//游戏得分
    private int highScore;//最高分
    MainXxlActivity mainAct;

    private boolean isReady = false;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainAct = (MainXxlActivity) context;
        init();
    }

    public GameView(Context context, AttributeSet attrs, int d) {
        super(context, attrs, d);
        this.mainAct = (MainXxlActivity) context;
        init();
    }

    public GameView(Context context) {
        super(context);
        this.mainAct = (MainXxlActivity) context;
        init();
    }

    private void init() {
        isReady = false;
        bts = new Bitmap[iconCount];
        Resources r = mainAct.getResources();
        bts[0] = BitmapFactory.decodeResource(r, R.drawable.fruit1);
        bts[1] = BitmapFactory.decodeResource(r, R.drawable.fruit2);
        bts[2] = BitmapFactory.decodeResource(r, R.drawable.fruit3);
        bts[3] = BitmapFactory.decodeResource(r, R.drawable.fruit4);
        bts[4] = BitmapFactory.decodeResource(r, R.drawable.fruit5);
        p1 = new Paint();
        p1.setColor(Color.RED);
        p1.setStyle(Style.STROKE);//空心矩形框
//		cCount=this.getWidth()/SWidth;
//		rCount=this.getHeight()/SHeight;
    }

    boolean isMeasured = false;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isMeasured == false) {
            isMeasured = true;
            SWidth = getWidth() / cCount;
            SHeight = getHeight() / rCount;
            SWidth = SHeight = Math.min(SWidth, SHeight);
            newGame();
            requestLayout();
        }
        //		cCount=this.getWidth()/SWidth;
//		rCount=this.getHeight()/SHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isMeasured) {
            setMeasuredDimension(SWidth * cCount, SHeight * rCount);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    Paint p1;

    Rect rect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        if (isReady) {

            for (byte i = 0; i < rCount; i++)
                for (byte j = 0; j < cCount; j++) {
                    if (map[i][j] != noPicture) {

                        rect.left = SWidth * j;
                        rect.right = rect.left + SWidth;

                        rect.top = SHeight * i;
                        rect.bottom = rect.top + SHeight;

                        canvas.drawBitmap(bts[map[i][j]], null, rect, null);
                    }
                }
            if (msgNotice == 1) {

                for (Point p : listPoint) {
                    canvas.drawRect(p.y * SWidth, p.x * SHeight, (p.y + 1) * SWidth, (p.x + 1) * SHeight, p1);
                }
//             if(listPoint.size()>1)
//                 g.DrawString(countScore().ToString(),new Font("宋体",20),new SolidBrush(Color.Black),listPoint[0].Y*SHeight,listPoint[0].X*SWidth);
                msgNotice = 0;
            }
        }
        super.onDraw(canvas);
    }

    public void newGame() {
        initMap();
        score = 0;
        isReady = true;
        mainAct.setScore(score);
        this.invalidate();
    }

    private void initMap() {
        map = new int[rCount][cCount];
        int x = new Random().nextInt(iconCount);
        boolean y = false;
        for (int i = 0; i < rCount; i++) //产生成对的
        {
            for (int j = 0; j < cCount; j++) {
                map[i][j] = x;
                if (y) {
                    x++;
                    y = false;
                    if (x == iconCount)
                        x = 1;
                } else
                    y = true;
            }
        }
        change();
    }

    private void change()//随机交换
    {
        Random random = new Random();
        int tmpV, tmpX, tmpY;
        for (int x = 0; x < rCount - 1; x++) {
            for (int y = 0; y < cCount - 1; y++) {
                tmpX = random.nextInt(rCount - 1);
                tmpY = random.nextInt(cCount - 1);
                tmpV = map[x][y];
                map[x][y] = map[tmpX][tmpY];
                map[tmpX][tmpY] = tmpV;
            }
        }
        if (isGameOver())//开始一来就游戏没解就重新刷新一次
            change();
    }

    private boolean isGameOver() {
        for (int r = rCount - 1; r >= 0; r--)
            for (int c = 0; c < cCount; c++) {
                int data = map[r][c];
                if (data != noPicture)//不为图片的空白不考虑
                {
                    if (isInArray(r + 1, c)) {
                        if (data == map[r + 1][c])
                            return false;
                    }
                    if (isInArray(r - 1, c)) {
                        if (data == map[r - 1][c])
                            return false;
                    }
                    if (isInArray(r, c + 1)) {
                        if (data == map[r][c + 1])
                            return false;
                    }
                    if (isInArray(r, c - 1)) {
                        if (data == map[r][c - 1])
                            return false;
                    }
                }
            }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            int c = (int) (e.getX() / SHeight);
            int r = (int) (e.getY() / SWidth);
            if (r < 0 || c < 0 || r > rCount - 1 || c > cCount - 1)
                return super.onTouchEvent(e);
            if (map[r][c] != noPicture)//点到不是空白的地方
            {
                if (isNotExistInPointArray(r, c))//当前选中的颜色 水果，和上一次选中的不同色
                {
                    listPoint.clear();//请空链表
                    curType = map[r][c];//改换当前类型
                    listPoint.add(new Point(r, c));//记录当前点
                    getPoint(r, c);//记录当前类型的点
                    msgNotice = 1;//画点消息
                    this.invalidate();
                } else if (listPoint.size() > 1) //否则是同一色，且链表中有可消除的节点
                {
                    for (Point p : listPoint) {
                        map[p.x][p.y] = noPicture;//改地图中的数值
                    }
                    down();//将 noPicture 的地方清空，格子向下掉
                    left();//左移

                    Score();

                    listPoint.clear();//清空链表

                    this.invalidate();//通知重绘制
                    if (score > highScore)//设置高分
                        mainAct.setHighScore(score);

                    if (isGameOver()) {

                        if (score > highScore) {
                            highScore = score;
                            mainAct.setHighScore(highScore);
                            mainAct.saveScore(highScore);//保存分数
                            Toast.makeText(mainAct, "恭喜您打破纪录！您的得分是：" + score, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(mainAct, "游戏结束！你的得分是：" + score, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return super.onTouchEvent(e);
    }

    private int countScore()//计算得分
    {
        return (int) Math.pow(2, listPoint.size()) * 10;//指数式算得分
    }

    private void Score() {
        score += countScore();
        mainAct.setScore(score);
    }

    private void left()//整体一列一列向左移
    {
        int curCLeft = 0;
        for (int c1 = 0; c1 < cCount; c1++) {
            if (!isColThrough(c1))//判断一行不是贯通的
            {
                curCLeft++;
                pushLeft(c1, curCLeft - 1);
            }
        }
    }

    private boolean pushLeft(int cRight, int cLeft) {
        if (cRight == cLeft)
            return false;
        for (int r = 0; r < rCount; r++) {
            map[r][cLeft] = map[r][cRight];//右边的向左边放
            map[r][cRight] = noPicture;//右边的改成 noPicture
        }
        return true;
    }

    private boolean isColThrough(int col) //判断某一列是不是贯通的,是返回 真，否返回 假
    {
        for (int row = 0; row < rCount; row++) {
            if (map[row][col] != noPicture)
                return false;
        }
        return true;
    }

    private void down()//将 noPicture 的地方清空，格子向下掉
    {
        //最外层循环是竖列
        for (int c1 = 0; c1 < cCount; c1++)//列
        {
            int cur = rCount + 1;//游标指向最大+1,这个是有原因的，我也不知道 为什么
            for (int r1 = rCount - 1; r1 >= 0; r1--)//行，比较1到rCount-1之间的数据
            {
                if (map[r1][c1] != noPicture)
                    cur--;
                if (cur - 1 != r1)//游标与当前指向的相同，不用交换数据 （cur-1才是实际 的游标）
                {
                    if (map[r1][c1] != noPicture) //比较到的R1的数据不为空，将其赋值到游标上
                    {
                        map[cur - 1][c1] = map[r1][c1];
                        map[r1][c1] = noPicture;
                    }
                }
            }
        }
    }

    private void getPoint(int r, int c)//得到点
    {
        for (int i = r - 1; i < r + 2; i++)
            for (int j = c - 1; j < c + 2; j++) {
                if (i + j == r + c + 1 || i + j == r + c - 1)//上下左右四格
                {
                    if (isInArray(i, j) && map[i][j] == curType && isNotExistInPointArray(i, j)) {
                        listPoint.add(new Point(i, j));//记录点
                        getPoint(i, j);//递归调用
                    }
                }
            }
    }

    private boolean isNotExistInPointArray(int r, int c)//判断传过来的行列不存在于已记录的行列链表中
    {
        for (Point p : listPoint) {
            if (p.x == r && p.y == c)
                return false;
        }
        return true;
    }

    private boolean isInArray(int r, int c) //检验是否越界
    {
        if (r < 0 || c < 0 || r > rCount - 1 || c > cCount - 1)
            return false;
        return true;
    }

}
