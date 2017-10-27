package com.hdyl.pintu;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.hdyl.baselib.base.IBaseDialog;
import com.hdyl.baselib.base.inter.IComCallBacks;
import com.hdyl.mine.R;

/**
 * Date:2017/10/27 15:45
 * Author:liugd
 * Modification:
 **/


public class ToolDialog extends IBaseDialog {
    CheckBox checkBox;

    public ToolDialog(Context context) {
        super(context);

        checkBox = findViewByID(R.id.imageViewNummark);
    }

    @Override
    public int setLayoutID() {
        return R.layout.view_menu_tool_pintu;
    }

    @Override
    public int[] setClickIDs() {
        return new int[]{R.id.imageViewNummark, R.id.imageViewChoosePhoto, R.id.imageViewExchange};
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
