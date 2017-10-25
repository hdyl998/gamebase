package com.hdyl.mine.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.hdyl.baselib.base.BaseActivity;
import com.hdyl.mine.set.AppSet;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;

public abstract class MineBaseActivity extends BaseActivity implements OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(setView());
        MobclickAgent.setScenarioType(mContext, EScenarioType.E_UM_NORMAL);
        initData();
    }

    protected abstract void initData();

    protected int setView() {
        return setLayoutID();
    }

    @Override
    public void initViews() {

    }


    @Override
    public int setLayoutID() {
        return 0;
    }

    protected String getPageName() {
        return this.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getPageName());
        MobclickAgent.onResume(mContext);
        if (lastBgRes != AppSet.getInstence().getBgId()) {
            initBg();
        }
    }


    private void initBg() {
        getWindow().setBackgroundDrawable(getBgDrawable());
        lastBgRes = AppSet.getInstence().getBgId();
    }

    int lastBgRes;


    public Drawable getBgDrawable() {
        Options opo = new Options();
        opo.outHeight = ScreenSize.getScreenHeight();
        opo.outWidth = ScreenSize.getScreenWidth();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), AppSet.getInstence().getBgId(), opo);
        return new BitmapDrawable(bitmap);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getPageName());
        MobclickAgent.onPause(mContext);
    }

}
