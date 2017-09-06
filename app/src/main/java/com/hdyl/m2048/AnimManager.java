package com.hdyl.m2048;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/1.
 */

public class AnimManager {
    private List<AnimCell> listAnims = new ArrayList<>(3);

    /***
     * 添加移动动画
     *
     * @param aimX 目标X
     * @param aimY 目标Y
     */
    public void addMoveAnim(int aimX, int aimY, int value) {
        AnimCell anim = new AnimCell(AnimCell.TYPE_MOVE, aimX, aimY);
        anim.setValue(value);
        listAnims.add(anim);
    }

    public void addMoveAnim(Cell cell, int value) {
        addMoveAnim(cell.getX(), cell.getY(), value);
    }

    /***
     * 添加产生方块的动画
     */
    public void addCreateAnim() {
        AnimCell anim = new AnimCell(AnimCell.TYPE_CREATE);
        listAnims.add(anim);
    }


    /***
     * 添加合并动画
     */
    public void addHeibingAnim(int value) {
        AnimCell anim = new AnimCell(AnimCell.TYPE_MERGE);
        anim.setValue(value);
        listAnims.add(anim);
    }

    /***
     * 是否有动画
     *
     * @return
     */
    public boolean isAnim() {
        if (listAnims.isEmpty()) {
            return false;
        }
        for (AnimCell anim : listAnims) {
            //有一个没有结束，有动画
            if (!anim.isEnd()) {
                return true;
            }
        }
        //全部结束返回false无动画
        return false;
    }


    /**
     * 请掉动画
     */
    public void clearAnimation() {
        listAnims.clear();
    }

    public List<AnimCell> getListAnims() {
        return listAnims;
    }

    public void ticker(int costTime) {
        for (AnimCell anim : listAnims) {
            anim.ticker(costTime);
        }
    }

}
