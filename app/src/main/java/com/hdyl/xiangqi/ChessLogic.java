package com.hdyl.xiangqi;

/**
 * <p>Created by Administrator on 2018/9/28.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class ChessLogic {

    public ChessLogic() {

    }

    public int xCount = 9;
    public int yCount = 10;


    public ChessItem[][] chessItems = new ChessItem[yCount][xCount];


    public void newGame() {
        //黑方
        setChessItem(0, 0, new ChessItem(ChessType.JU, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 8, new ChessItem(ChessType.JU, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 1, new ChessItem(ChessType.MA, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 7, new ChessItem(ChessType.MA, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 2, new ChessItem(ChessType.XIANG, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 6, new ChessItem(ChessType.XIANG, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 3, new ChessItem(ChessType.SHI, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 5, new ChessItem(ChessType.SHI, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 4, new ChessItem(ChessType.JIANG, ChessItem.PLAYER_TYPE_BLACK));

        setChessItem(2, 2, new ChessItem(ChessType.PAO, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(2, 7, new ChessItem(ChessType.PAO, ChessItem.PLAYER_TYPE_BLACK));

        setChessItem(3, 0, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(3, 2, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(3, 4, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(3, 6, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(3, 8, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));

        //红方
        setChessItem(9, 0, new ChessItem(ChessType.JU, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 8, new ChessItem(ChessType.JU, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 1, new ChessItem(ChessType.MA, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 7, new ChessItem(ChessType.MA, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 2, new ChessItem(ChessType.XIANG, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 6, new ChessItem(ChessType.XIANG, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 3, new ChessItem(ChessType.SHI, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 5, new ChessItem(ChessType.SHI, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 4, new ChessItem(ChessType.JIANG, ChessItem.PLAYER_TYPE_RED));

        setChessItem(7, 2, new ChessItem(ChessType.PAO, ChessItem.PLAYER_TYPE_RED));
        setChessItem(7, 7, new ChessItem(ChessType.PAO, ChessItem.PLAYER_TYPE_RED));

        setChessItem(6, 0, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
        setChessItem(6, 2, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
        setChessItem(6, 4, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
        setChessItem(6, 6, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
        setChessItem(6, 8, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
    }

    public void setChessItem(int y, int x, ChessItem item) {
        chessItems[y][x] = item;
    }
}
