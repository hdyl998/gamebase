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
        super.onCreate(savedInstanceState);
        MobclickAgent.setScenarioType(mContext, EScenarioType.E_UM_NORMAL);
    }


    protected String getPageName() {
        return this.getClass().getSimpleName();
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
        opo.outHeight = ScreenSizeUtils.getScreenHeight();
        opo.outWidth = ScreenSizeUtils.getScreenWidth();
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
