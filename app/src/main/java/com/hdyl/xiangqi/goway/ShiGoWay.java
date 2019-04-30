package com.hdyl.xiangqi.goway;

import com.hdyl.xiangqi.ChessItem;

/**
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class ShiGoWay implements IGoWay {
    @Override
    public boolean canGo(ChessItem[][] chessItems, int fromX, int fromY, int toX, int toY) {
        if (toX >= 3 && toX <= 5 && Math.abs(toX - fromX) * Math.abs(toY - fromY) == 1) {
            ChessItem curItem = chessItems[fromY][fromX];
            //向上进攻
            if (curItem.isAttackTowardUp()) {
                if (toY >= 7) {
                    return true;
                }
            } else {  //向下进攻
                if (toY <= 2) {
                    return true;
                }
            }
        }
        return false;
    }
}
