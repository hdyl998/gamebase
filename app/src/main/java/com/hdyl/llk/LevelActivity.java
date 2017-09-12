package com.hdyl.llk;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.hdyl.llk.base.BaseActivity;
import com.hdyl.mine.R;


public class LevelActivity extends BaseActivity {

	GridView listView;
	LevelAdapter levelAdapter;
	public static boolean isChange = false;

	View view;
	TextView tvFinishTextView;

	@Override
	protected void onResume() {
		// Tools.changBG(view);
		if (isChange) {
			levelAdapter.notifyDataSetChanged();
			isChange = false;
			count();
		}
		super.onResume();
	}

	private void count() {
		SaveData saveData = SaveData.getInstance();
		tvFinishTextView.setText((saveData.currentLevel) + "/" + saveData.listLevels.size());
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.textViewInfo:
				InfoDialog dialog = new InfoDialog(LevelActivity.this, getResources().getString(R.string.app_detail));
				dialog.show();
				break;

			default:
				break;
		}
	}

	@Override
	protected void initData() {
		view = findViewById(R.id.ll);
		listView = (GridView) findViewById(R.id.gv_select_level);
		levelAdapter = new LevelAdapter(this);
		listView.setAdapter(levelAdapter);

		tvFinishTextView = (TextView) findViewById(R.id.ll_finish);
		count();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				if (arg2 > SaveData.getInstance().currentLevel) {

					InfoDialog dialog = new InfoDialog(LevelActivity.this, "还没有解锁呢！别着急啊~~");
					dialog.show();
				} else {
					Intent intent = new Intent(LevelActivity.this, MainLlkActivity.class);
					intent.putExtra("position", arg2);
					startActivity(intent);
				}
				// Intent intent = new Intent(LevelActivity.this,
				// MainLlkActivity.class);
				// intent.putExtra("position", arg2);
				// startActivity(intent);

			}
		});
		findViewById(R.id.textViewInfo).setOnClickListener(this);
	}

	@Override
	protected int setView() {
		return R.layout.activity_level;
	}

	@Override
	protected String getPageName() {
		return "关卡";
	}

}
