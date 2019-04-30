package com.hdyl.xiangqi.goway;

import com.hdyl.xiangqi.ChessItem;

/**
 * 相不能过河,需要确定红方是否在下面
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class XiangGoWay implements IGoWay {
    @Override
    public boolean canGo(ChessItem[][] chessItems, int fromX, int fromY, int toX, int toY) {

        ChessItem curItem = chessItems[fromY][fromX];
        //向上进攻,不能过河
        if (curItem.isAttackTowardUp()) {
            if (toY < 5) {
                return false;
            }
        } else {//向下进攻
            if (toY > 4) {
                return false;
            }
        }
        int absX = toX - fromX;
        int absY = toY - fromY;

        if (Math.abs(absX) == 2 && Math.abs(absY) == 2) {
            //检查相眼
            int newX = fromX + absX / 2;
            int newY = fromY + absY / 2;
            if (chessItems[newY][newX] == null) {
                return true;
            }
        }
        return false;
    }
}
