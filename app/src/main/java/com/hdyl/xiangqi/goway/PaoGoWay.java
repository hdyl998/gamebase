package com.hdyl.xiangqi.goway;

import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.xiangqi.ChessItem;
import com.hdyl.xiangqi.ChessType;

/**
 * 炮的走法
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class PaoGoWay implements IGoWay {
    @Override
    public boolean canGo(ChessItem[][] chessItems, int fromX, int fromY, int toX, int toY) {
        ChessItem aimItem = chessItems[toY][toX];
        if (aimItem == null) {//平移,同车一样
            return GoWayManager.getInstance().getGoWay(ChessType.JU).canGo(chessItems, fromX, fromY, toX, toY);
        } else {//翻山炮,中间必须隔一个
            if (!(fromX == toX || fromY == toY)) {
                return false;
            }
            if (fromX == toX) {
                int minY = Math.min(toY, fromY);
                int maxY = Math.max(toY, fromY);
                int count = 0;
                for (int j = minY + 1; j < maxY; j++) {
                    if (chessItems[j][toX] != null) {
                        count++;
                        if (count > 1) {
                            return false;
                        }
                    }
                }
                return count == 1;
            }
            if (fromY == toY) {
                int min = Math.min(fromX, toX);
                int max = Math.max(fromX, toX);
                int count = 0;
                for (int j = min + 1; j < max; j++) {
                    if (chessItems[toY][j] != null) {
                        count++;
                        if (count > 1) {
                            return false;
                        }
                    }
                }
                return count == 1;
            }
        }
        return false;
    }
}
