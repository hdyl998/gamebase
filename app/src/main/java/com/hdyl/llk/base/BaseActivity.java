package com.hdyl.llk.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;

public abstract class BaseActivity extends Activity implements OnClickListener {

	protected Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		mContext = this;
		setContentView(setView());

		// AnalyticsConfig.setChannel(Tools.getAppMetaData(this,
		// "TD_CHANNEL_ID"));
		// MobclickAgent.openActivityDurationTrack(false);

		MobclickAgent.setScenarioType(mContext, EScenarioType.E_UM_NORMAL);
		initData();
	}

	protected abstract void initData();

	protected abstract int setView();

	protected abstract String getPageName();

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getPageName());
		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getPageName());
		MobclickAgent.onPause(mContext);
	}

}
