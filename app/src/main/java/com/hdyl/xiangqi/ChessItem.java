package com.hdyl.xiangqi;

/**
 * <p>Created by Administrator on 2018/9/28.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class ChessItem {
    public final static int PLAYER_TYPE_RED = 0;
    public final static int PLAYER_TYPE_BLACK = 1;


    public int playerType = PLAYER_TYPE_RED;


    public int chessType = ChessType.JU;

    public ChessItem() {

    }


    public ChessItem(int chessType, int playerType) {
        this.chessType = chessType;
        this.playerType = playerType;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getChessType() {
        return chessType;
    }

    public int getPlayerType() {
        return playerType;
    }


    public void setChessType(int chessType) {
        this.chessType = chessType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }
}
