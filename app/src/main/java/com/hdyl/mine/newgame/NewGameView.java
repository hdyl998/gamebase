package com.hdyl.mine.newgame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class NewGameView extends View {
    public NewGameView(Context context) {
        super(context);
    }

    public NewGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    MineGameBoard gameBoard=new MineGameBoard();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        gameBoard.newGame();
    }
}
