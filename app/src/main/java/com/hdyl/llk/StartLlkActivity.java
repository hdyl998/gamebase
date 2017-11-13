package com.hdyl.llk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyl.baselib.utils.SpUtils;
import com.hdyl.llk.base.BaseActivity;
import com.hdyl.llk.utils.Tools;
import com.hdyl.mine.R;
import com.hdyl.mine.tuijian.TuijianAcitivity;


public class StartLlkActivity extends BaseActivity {

    View view;

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.iv_stat:
                Intent intent = new Intent(this, LevelActivity.class);
                startActivity(intent);
                break;

            case R.id.textViewGood2:

                Intent intent3 = new Intent(this, TuijianAcitivity.class);
                startActivity(intent3);

                break;
            case R.id.textViewScore:
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Couldn't launch the market !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textViewType:

                if (Constants.PIC_TYPE == 0) {
                    Constants.PIC_TYPE = 1;
                } else {
                    Constants.PIC_TYPE = 0;
                }

                SpUtils.putInt("aa", "config", Constants.PIC_TYPE);

                if (Constants.PIC_TYPE == 0) {
                    Tools.setTextViewDrawable(tvTypeTextView, R.drawable.ic_luncher_2, 2);
                } else {
                    Tools.setTextViewDrawable(tvTypeTextView, R.drawable.fruit_5, 2);
                }
                Constants.initBitmap();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            super.onBackPressed();
        } else {
            isExit = true;
            Toast.makeText(this, "再按一次将就要离开了 ~T_T~", 0).show();
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }
    }

    boolean isExit = false;

    TextView tvTypeTextView;

    @Override
    protected void initData() {
        view = findViewById(R.id.ll);

        findViewById(R.id.textViewScore).setOnClickListener(this);

        findViewById(R.id.textViewGood2).setOnClickListener(this);

        findViewById(R.id.iv_stat).setOnClickListener(this);
        tvTypeTextView = (TextView) findViewById(R.id.textViewType);
        tvTypeTextView.setOnClickListener(this);
        Constants.PIC_TYPE = SpUtils.getInt("aa", "config", 0);// 获得配置
        if (Constants.PIC_TYPE == 0) {
            Tools.setTextViewDrawable(tvTypeTextView, R.drawable.ic_luncher_2, 2);
        } else {
            Tools.setTextViewDrawable(tvTypeTextView, R.drawable.fruit_5, 2);
        }
        Constants.initBitmap();
    }

    @Override
    protected int setView() {
        return R.layout.activity_start_llk;
    }

    @Override
    protected String getPageName() {
        return null;
    }
}
