package com.hdyl.xiangqi.goway;

import com.hdyl.xiangqi.ChessItem;

/**
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class ShiGoWay implements IGoWay {
    @Override
    public boolean canGo(ChessItem[][] chessItems, int fromX, int fromY, int toX, int toY) {
        ChessItem curItem = chessItems[toY][toX];
        //向上进攻
        if (curItem.isAttackTowardUp()) {


        } else {  //向下进攻

        }


        return false;
    }
}
