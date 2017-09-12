package com.hdyl.pintu.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	protected abstract void initData();

	public abstract void onClick(int id);

	public void onClick(View view) {
		try {
			onClick(view.getId());
		} catch (Exception e) {
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
