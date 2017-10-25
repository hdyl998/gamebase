package com.hdyl.mine.stage;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.base.MineBaseActivity;

/**
 * Date:2017/10/25 17:59
 * Author:liugd
 * Modification:
 **/


public class MineStageActivity extends MineBaseActivity {

    TextView tvTitle;
    GridView gridView;



    @Override
    protected void initData() {
        tvTitle = findViewByID(R.id.title);
        gridView = findViewByID(R.id.gameView);
    }

    @Override
    protected int setView() {
        return R.layout.activity_mine_select;
    }


    @Override
    public int[] setClickIDs() {
        return new int[]{R.id.textViewBack};
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewBack:
                mContext.finish();
                break;
        }
    }
}
