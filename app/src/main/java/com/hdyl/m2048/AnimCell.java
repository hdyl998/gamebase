package com.hdyl.m2048;

/**
 * Created by Administrator on 2017/4/30.
 */

public class AnimCell extends Cell {
    public final static int TYPE_CREATE = 0;//产生动画
    public final static int TYPE_MOVE = 1;//移动动画
    public final static int TYPE_MERGE = 2;//合并动画

    public final static int TIME_CREATE = 300;
    public final static int TIME_MERGE = 300;
    public final static int TIME_MOVE = 200;

    private int animationType;
    private int costTime;//当前花费时间
    private int animationTime;//总时间
    private int delay;//延时时长


    public AnimCell(int type) {
        this(type, 0, 0);
    }

    public AnimCell(int type, int aimx, int aimy) {
        this.animationType = type;
        this.x = aimx;
        this.y = aimy;
        init();
    }

    private void init() {
        switch (this.animationType) {
            case TYPE_MOVE:
                this.animationTime = TIME_MOVE;
                break;
            case TYPE_MERGE:
                this.animationTime = TIME_MERGE;
                this.delay = TIME_MOVE;
                break;
            case TYPE_CREATE:
                this.animationTime = TIME_CREATE;
                this.delay = TIME_MOVE;
                break;
        }
    }

    @Override
    public String toString() {
        return "AnimCell{" +
                "animationType=" + animationType +
                ", costTime=" + costTime +
                ", animationTime=" + animationTime +
                ", delay=" + delay +
                '}';
    }



    //加时间
    public void ticker(int costTime) {
        this.costTime += costTime;
    }

    //是否结束
    public boolean isEnd() {
        return costTime - delay > animationTime;
    }

    //是否进在动画进行中
    public boolean isActive() {
        return costTime >= delay && isEnd() == false;
    }

    public int getAimX() {
        return x;
    }

    public int getAimY() {
        return y;
    }


    public float getPercent() {
        if (isEnd()) {
            return 1f;
        }
        return (costTime - delay) / (animationTime * 1f);
    }

    public int getAnimationType() {
        return animationType;
    }
}
