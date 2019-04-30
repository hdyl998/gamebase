package com.hdyl.xiangqi.goway;

import com.hdyl.xiangqi.ChessItem;

/**
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public interface IGoWay {

    boolean canGo(ChessItem[][] chessItems, int fromX, int fromY, int toX, int toY);
}
