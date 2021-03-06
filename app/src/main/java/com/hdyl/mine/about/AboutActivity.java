package com.hdyl.mine.about;

import android.view.View;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.base.MineBaseActivity;
import com.hdyl.mine.tools.Tools;
import com.hdyl.mine.tuijian.WebActivity;

/**
 * 关于
 *
 * @author liugd
 */
public class AboutActivity extends MineBaseActivity {

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.textViewUpdate:
                String urlString = "http://openbox.mobilem.360.cn/qcms/view/t/detail?sid=3281879";
                WebActivity.launch(mContext, getString(R.string.app_name_mine), urlString);
                break;
            default:
                finish();
                break;
        }

    }

    @Override
    public void initViews() {
        TextView textView = (TextView) findViewById(R.id.TextView01);
        String str = Tools.getVerName(this);
        textView.setText("V " + str);
        findViewById(R.id.ll).setOnClickListener(this);
        findViewById(R.id.textViewUpdate).setOnClickListener(this);
    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected String getPageName() {
        return "关于";
    }
}
