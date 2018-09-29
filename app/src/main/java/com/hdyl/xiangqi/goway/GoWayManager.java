package com.hdyl.xiangqi.goway;

import com.hdyl.mine.R;
import com.hdyl.xiangqi.ChessType;

import java.util.HashMap;

/**
 * <p>Created by Administrator on 2018/9/29.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class GoWayManager {

    final static GoWayManager manager = new GoWayManager();

    public static GoWayManager getInstance() {
        return manager;
    }

    private HashMap<Integer, IGoWay> hashMap = new HashMap<>(7);


    public IGoWay getGoWay(int chessType) {
        IGoWay goWay = hashMap.get(chessType);
        if (goWay == null) {
            goWay = createGoWay(chessType);
            hashMap.put(chessType, goWay);
        }
        return goWay;
    }

    private IGoWay createGoWay(int chessType) {
        IGoWay goWay = null;
        switch (chessType) {
            case ChessType.JU:
                goWay = new JuGoWay();
                break;
            case ChessType.MA:
                goWay = new MaGoWay();
                break;
            case ChessType.XIANG:
                goWay = new XiangGoWay();
                break;
            case ChessType.BING:
                goWay = new BingGoWay();
                break;
            case ChessType.JIANG:
                goWay = new JiangGoWay();
                break;
            case ChessType.PAO:
                goWay = new PaoGoWay();
                break;
            case ChessType.SHI:
                goWay = new ShiGoWay();
                break;
        }
        return goWay;
    }
}
