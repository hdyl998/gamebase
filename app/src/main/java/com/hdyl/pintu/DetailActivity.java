package com.hdyl.pintu;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyl.mine.R;
import com.hdyl.pintu.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends BaseActivity {

	ListView listView;
	MyListAdapter adapter;
	DatabaseHelper databaseHelper;
	TextView textViews[] = new TextView[3];

	@Override
	protected void initData() {
		setContentView(R.layout.activity_top_pintu);
		findViewById(R.id.textView3).setOnClickListener(this);
		findViewById(R.id.tv_reset).setOnClickListener(this);
		listView = (ListView) findViewById(R.id.listView1);

		listView.addHeaderView(View.inflate(this, R.layout.item_list, null));
		List<UserInfo> list = new ArrayList<UserInfo>();
		adapter = new MyListAdapter(this, list);
		listView.setAdapter(adapter);

		TextView emptyView = new TextView(this);
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		emptyView.setText("暂无排行数据，大侠请先通过试炼吧！");
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		emptyView.setVisibility(View.GONE);
		((ViewGroup) listView.getParent()).addView(emptyView);

		listView.setEmptyView(emptyView);

		databaseHelper = new DatabaseHelper(this);
		textViews[0] = (TextView) findViewById(R.id.tvAll);
		textViews[1] = (TextView) findViewById(R.id.tvRandom);
		textViews[2] = (TextView) findViewById(R.id.tvClass);
		for (int i = 0; i < 3; i++)
			textViews[i].setOnClickListener(this);
		setData(0);
	}

	@Override
	public void onClick(int arg0) {

		switch (arg0) {
			case R.id.textView3:
				finish();
				break;
			case R.id.tv_reset:

				ConirmDialog conirmDialog = new ConirmDialog(this, "确认重置？", new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (adapter.getCount() != 0) {
							int len = databaseHelper.deleteAll(curIndex - 1);
							Toast.makeText(getApplicationContext(), "已重置,共清除" + len + "条数据", 1).show();
							adapter.setData(null);
						} else {
							Toast.makeText(getApplicationContext(), "数据为空", 0).show();
						}
					}
				}, this);

				conirmDialog.show();
				break;
			case R.id.tvAll:
				setData(0);
				break;
			case R.id.tvRandom:
				setData(1);
				break;
			case R.id.tvClass:
				setData(2);
				break;
		}
	}

	public int curIndex = 0;

	public void setData(int index) {
		curIndex = index;
		for (int i = 0; i < 3; i++) {
			if (index == i)
				textViews[i].setEnabled(false);
			else {
				textViews[i].setEnabled(true);
			}
		}
		adapter.setData(databaseHelper.selectInfos(index - 1, 1000));
	}

}
