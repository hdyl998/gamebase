package com.hdyl.xiangqi.goway;

import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.xiangqi.ChessItem;

/**
 * 车的行走方式
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class JuGoWay implements IGoWay {


    @Override
    public boolean canGo(ChessItem[][] chessItems, int fromX, int fromY, int toX, int toY) {

        if (!(fromX == toX || fromY == toY)) {
            return false;
        }
        //这路X相同,则这条Y上面没有棋

        if (fromX == toX) {
            int minY = Math.min(toY, fromY);
            int maxY = Math.max(toY, fromY);

            LogUitls.print("minY" + minY + " maxY" + maxY);
            for (int j = minY + 1; j < maxY; j++) {
                if (chessItems[j][toX] != null) {
                    return false;
                }
            }
            return true;
        }
        if (fromY == toY) {
            int min = Math.min(fromX, toX);
            int max = Math.max(fromX, toX);

            LogUitls.print("minx" + min + " maxx" + max);
            for (int j = min + 1; j < max; j++) {
                if (chessItems[toY][j] != null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
