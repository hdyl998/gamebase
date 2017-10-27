package com.hdyl.pintu;

import android.content.Context;
import android.view.View;

import com.hdyl.baselib.base.IBaseDialog;
import com.hdyl.baselib.base.inter.IComCallBacks;
import com.hdyl.mine.R;

/**
 * Date:2017/10/27 15:44
 * Author:liugd
 * Modification:
 **/


public class MenuDialog extends IBaseDialog {
    public MenuDialog(Context context) {
        super(context);
    }

    @Override
    public int setLayoutID() {
        return R.layout.view_menu_pintu;
    }


    @Override
    public int[] setClickIDs() {
        return new int[]{R.id.imageViewExit, R.id.imageViewAbout, R.id.imageViewHelp, R.id.imageViewTop, R.id.imageViewClass, R.id.imageViewNewStart};
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    IComCallBacks<Integer> callBacks;


    public void setCallBacks(IComCallBacks<Integer> callBacks) {
        this.callBacks = callBacks;
    }
}
