package com.hdyl.xiangqi.goway;

import com.hdyl.xiangqi.ChessItem;

/**
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class BingGoWay implements IGoWay {
    @Override
    public boolean canGo(ChessItem[][] chessItems, int fromX, int fromY, int toX, int toY) {

        //只走一步
        if (fromX == toX && Math.abs(toY - fromY) == 1 || fromY == toY && Math.abs(toX - fromX) == 1) {
            ChessItem curItem = chessItems[fromY][fromX];
            //向上进攻,不能过河
            if (curItem.isAttackTowardUp()) {
                //不能倒退
                if (toY < fromY) {
                    return false;
                }
                //没过河之前横着走
                if (toY > 4 && fromY == toY) {
                    return false;
                }
            } else {//向下进攻
                //不能倒退
                if (toY > fromY) {
                    return false;
                }
                //没过河之前横着走
                if (toY < 5 && fromY == toY) {
                    return false;
                }
            }
        }
        return false;
    }
}
