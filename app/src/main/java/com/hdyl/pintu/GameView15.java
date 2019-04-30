package com.hdyl.pintu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hdyl.mine.R;
import com.hdyl.pintu.common.Constants;
import com.hdyl.pintu.save.SaveData;

import java.util.Random;

public class GameView15 extends View {

    public static int SIZE = 4;


    MainPintuActivity mainActivity;

    public GameView15(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainActivity = (MainPintuActivity) context;
        paint.setColor(getResources().getColor(R.color.orange));
        paint.setStrokeWidth(3);
        paint.setStyle(Style.STROKE);
    }

    public GameView15(Context context) {
        super(context);
        mainActivity = (MainPintuActivity) context;
    }

    // 显示数字或者图片
    public void changeMode() {
        saveData.isNummode = !saveData.isNummode;
        this.invalidate();
    }

    // 是否有解
    private boolean hasJie() {
        int len = SIZE * SIZE;
        int source[] = new int[len];
        // int target[]=new int[len];
        // for(int i=0;i<len;i++)
        // target[i]=(i+1)%len;
        // 空白格子的坐标
        Point pointBlack = new Point();
        Point pointRightDown = new Point(SIZE - 1, SIZE - 1);
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                source[count] = saveData.arr[i][j];
                if (saveData.arr[i][j] == 0) {
                    source[count] = len;
                    pointBlack.x = i;
                    pointBlack.y = j;
                }
                count++;
            }
        }
        // 逆序数计算
        int countNum = 0;
        // 冒泡算法
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (source[j] > source[j + 1]) {
                    countNum++;
                    // 交换（重要）
                    int temp = source[j + 1];
                    source[j + 1] = source[j];
                    source[j] = temp;
                }
            }
        }

        int d = pointBlack.x - pointRightDown.x + pointBlack.y - pointRightDown.y;
        if ((countNum + d) % 2 == 0)
            return true;
        return false; // 一奇一偶无解
    }

    // 是否是经典
    public void newGame(boolean classCal) {
        saveData.isClass = classCal;
        saveData.isWin = false;
        saveData.countStep = 0;
        saveData.times = 0;
        mainActivity.stopTime();
        mainActivity.setTvTimeText("" + saveData.times);
        int count = SIZE * SIZE;
        for (int i = 0; i < saveData.arr.length; i++)
            for (int j = 0; j < saveData.arr[0].length; j++) {
                saveData.arr[i][j] = (count--) % (SIZE * SIZE);
            }
        if (classCal == false) {
            randomData();
            while (hasJie() == false) {
                randomData();
            }
        }
        while (hasJie() == false) {
            randomData();
        }
        initUI();
        this.invalidate();
    }

    private boolean isWin() {
        int count = 0;
        for (int i = 0; i < saveData.arr.length; i++)
            for (int j = 0; j < saveData.arr[0].length; j++) {
                count++;
                if (saveData.arr[i][j] != count % (SIZE * SIZE)) {
                    return false;
                }
            }
        saveData.isWin = true;
        return true;
    }

    private void randomData() {
        Random random = new Random();
        for (int i = 0; i < saveData.arr.length; i++)
            for (int j = 0; j < saveData.arr[0].length; j++) {
                int r = random.nextInt(SIZE);
                int c = random.nextInt(SIZE);
                int temp = saveData.arr[i][j];
                saveData.arr[i][j] = saveData.arr[r][c];
                saveData.arr[r][c] = temp;
            }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, w, oldw, oldh);
        this.WIDTH = w / SIZE;
        saveData = SaveData.getInstance();
        // 数组没初始化直接为false
        if (saveData.arr[0][0] == saveData.arr[0][1]) {
            newGame(false);
        } else {
            initUI();
            this.invalidate();
        }
    }

    private void initUI() {
        if (saveData.isWin)
            mainActivity.setText("Win!" + saveData.countStep);
        else {
            mainActivity.setText(saveData.countStep + "");
        }
        mainActivity.setMode(saveData.isClass);
    }

    SaveData saveData;
    public int WIDTH;

    Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        if (saveData.arr != null) {
            if (anim.isAnim()) {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        int num = saveData.arr[i][j];
                        if (i == anim.startX && j == anim.startY) {
//                            LogUitls.print(""+num);
                            num = anim.value;
                            int xValue = i - anim.aimX;
                            int yValue = j - anim.aimY;
                            int xTemp = 0;
                            int yTemp = 0;
                            if (yValue == 0) {//x上的移动
                                xTemp = (int) (xValue * (anim.getPercent() - 1) * WIDTH);
                            } else if (xValue == 0) {//y上的移动
                                yTemp = (int) (yValue * (anim.getPercent() - 1) * WIDTH);
                            }
                            int temp = xTemp;
                            xTemp = yTemp;
                            yTemp = temp;

                            if (num != 0) {
                                Bitmap bitmap = Constants.getBitmap(num);
                                canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(WIDTH * j + xTemp, WIDTH * i + yTemp, WIDTH * j + WIDTH + xTemp, WIDTH * i + WIDTH + yTemp), null);
                                // 显示数字
                                if (!saveData.isNummode && saveData.isShowNum && !saveData.isWin) {
                                    bitmap = Constants.getNumBitmap(num);
                                    canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(WIDTH * j + (int) (2f / 3 * WIDTH) + xTemp, WIDTH * i + (int) (2f / 3 * WIDTH) + yTemp, WIDTH * j + WIDTH + xTemp, WIDTH * i + WIDTH + yTemp), null);
                                }
                            }

                            canvas.drawRect(j == 0 ? 1 : WIDTH * j, i == 0 ? 1 : WIDTH * i, WIDTH * j + WIDTH, WIDTH * i + WIDTH, paint);
                        } else {
                            drawCell(canvas, num, i, j);
                        }
                    }
                }
                anim.ticker(40);
                invalidate();
            } else {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        int num = saveData.arr[i][j];
                        drawCell(canvas, num, i, j);
                    }
                }
            }

        }
    }

    BaseAnim anim = new BaseAnim();

    private void drawCell(Canvas canvas, int num, int i, int j) {
        if (num != 0) {
            Bitmap bitmap = Constants.getBitmap(num);
            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(WIDTH * j, WIDTH * i, WIDTH * j + WIDTH, WIDTH * i + WIDTH), null);
            // 显示数字
            if (!saveData.isNummode && saveData.isShowNum && !saveData.isWin) {
                bitmap = Constants.getNumBitmap(num);
                canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(WIDTH * j + (int) (2f / 3 * WIDTH), WIDTH * i + (int) (2f / 3 * WIDTH), WIDTH * j + WIDTH, WIDTH * i + WIDTH), null);
            }
        } else {// 为0且胜利全部的图都画出来
            if (!saveData.isNummode && saveData.isWin) {
                Bitmap bitmap = Constants.getBitmap(SIZE * SIZE);
                canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(WIDTH * j, WIDTH * i, WIDTH * j + WIDTH, WIDTH * i + WIDTH), null);
            }
        }
        if (saveData.isNummode || !saveData.isWin)
            canvas.drawRect(j == 0 ? 1 : WIDTH * j, i == 0 ? 1 : WIDTH * i, WIDTH * j + WIDTH, WIDTH * i + WIDTH, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN && saveData.isWin == false) {
            int i = (int) (event.getY() / WIDTH);
            int j = (int) (event.getX() / WIDTH);
            if (isInArray(i, j)) {
                if (saveData.arr[i][j] != 0) {
                    if (isInArrayZero(i - 1, j)) {
                        change(i - 1, j, i, j);
                    } else if (isInArrayZero(i + 1, j)) {
                        change(i + 1, j, i, j);
                    } else if (isInArrayZero(i, j - 1)) {
                        change(i, j - 1, i, j);
                    } else if (isInArrayZero(i, j + 1)) {
                        change(i, j + 1, i, j);
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /***
     * 交换
     * @param r 目标r 0
     * @param c 目标c 0
     * @param r2 原r
     * @param c2 原c
     */
    private void change(int r, int c, int r2, int c2) {
        saveData.countStep++;
        if (saveData.countStep == 1) {
            mainActivity.startTime();
        }
        mainActivity.setText(saveData.countStep + "");
        int temp = saveData.arr[r][c];
        saveData.arr[r][c] = saveData.arr[r2][c2];
        saveData.arr[r2][c2] = temp;
        if (isWin()) {
            Toast.makeText(getContext(), "胜利!!步数为：" + saveData.countStep, 1).show();
            mainActivity.setText("Win!" + saveData.countStep);
            saveData.times = mainActivity.curTime;
            mainActivity.stopTime();
            if (alertDialog == null) {
                alertDialog = buildAlertDialog_input();
            }
            editText.setText(saveData.stringName);
            alertDialog.show();
        }
        anim.setAimX(r2);
        anim.setAimY(c2);//为目标点
        anim.setStartX(r);
        anim.setStartY(c);//起始点 值为0
        anim.setValue(saveData.arr[r][c]);
        anim.start();
        this.invalidate();
    }

    public boolean isInArray(int r, int c) {
        return r > -1 && c > -1 && r < SIZE && c < SIZE;
    }

    public boolean isInArrayZero(int r, int c) {
        return r > -1 && c > -1 && r < SIZE && c < SIZE && saveData.arr[r][c] == 0;
    }

    Dialog alertDialog;

    EditText editText;

    /**
     * 含可以输入文本的弹出框
     */
    private Dialog buildAlertDialog_input() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setIcon(R.drawable.ic_luncher);
        builder.setTitle("请输入您的尊姓大名");
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View view = inflater.inflate(R.layout.activity_input, null);
        builder.setView(view);
        editText = (EditText) view.findViewById(R.id.editText1);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveData.stringName = editText.getText().toString();
                if (saveData.stringName.length() == 0)
                    saveData.stringName = "default";
                insertData();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertData();

            }
        });
        return builder.create();
    }

    public void insertData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        UserInfo userInfo = new UserInfo();
        userInfo.isClasscal = saveData.isClass;
        userInfo.steps = saveData.countStep;
        userInfo.times = saveData.times;
        userInfo.username = saveData.stringName;
        databaseHelper.insert(userInfo);

        initUI();
    }
}
