package com.hdyl.xiangqi.goway;

import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.xiangqi.ChessItem;

/**
 * 马的走法
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class MaGoWay implements IGoWay {
    @Override
    public boolean canGo(ChessItem[][] chessItems, int fromX, int fromY, int toX, int toY) {
        int chaX = toX - fromX;
        int chaY = toY - fromY;
        int absX = Math.abs(chaX);
        int absY = Math.abs(chaY);
        LogUitls.print("maGoWay", "chaX " + chaX + " chaY" + chaY);
        //马走日
        if (absX == 2 && absY == 1) {
            int newX = fromX + chaX / 2;
            int newY = fromY;
            //马脚
            if (chessItems[newY][newX] == null) {
                return true;
            }
        } else if (absX == 1 && absY == 2) {
            int newX = fromX;
            int newY = fromY + chaY / 2;
            //马脚
            if (chessItems[newY][newX] == null) {
                return true;
            }
        }
        return false;
    }
}
