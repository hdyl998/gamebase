package com.hdyl;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import com.hdyl.baselib.base.BaseFragment;
import com.hdyl.mine.R;

/**
 * Created by Administrator on 2017/11/15.
 */

public class TestFragment extends BaseFragment {

    EditText editText;
    @Override
    public void initViews() {
        editText=findViewByID(R.id.editText);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(editText.getText().toString()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public int setLayoutID() {
        return R.layout.fragment_test;
    }

    @Override
    public int[] setClickIDs() {
        return new int[]{R.id.button5};
    }
}
