package com.hdyl.mine.tuijian;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.base.MineBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TuijianAcitivity extends MineBaseActivity implements OnClickListener {

	List<AppItem> listMatchItems = new ArrayList<AppItem>();

	private OnItemClickListener onItemLongClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Intent intent = new Intent(TuijianAcitivity.this, WebActivity.class);
			intent.putExtra("title", listMatchItems.get(arg2).name);
			intent.putExtra("url", listMatchItems.get(arg2).url);
			startActivity(intent);
		}
	};

	@Override
	public void onClick(View arg0) {
		finish();
	}

	@Override
	public   void initViews() {
		findViewById(R.id.back).setOnClickListener(this);

		ListView listView = (ListView) findViewById(R.id.listview);

		TextView textView = new TextView(this);
		textView.setText(getResources().getString(R.string.tips));
		textView.setTextColor(getResources().getColor(R.color.green));
		listView.addFooterView(textView);
		AppAdapter adapter = new AppAdapter(this);

		listView.setAdapter(adapter);

		AppItem appItem = new AppItem();
		appItem.name = "经典扫雷";
		appItem.icon = R.drawable.app1;
		appItem.url = "http://openbox.mobilem.360.cn/qcms/view/t/detail?sid=3281879";
		listMatchItems.add(appItem);

		appItem = new AppItem();
		appItem.name = "鲜花连连看";
		appItem.icon = R.drawable.app2;
		appItem.url = "http://openbox.mobilem.360.cn/qcms/view/t/detail?sid=3203033";
		listMatchItems.add(appItem);

		appItem = new AppItem();
		appItem.name = "推箱子";
		appItem.icon = R.drawable.app3;
		appItem.url = "http://openbox.mobilem.360.cn/qcms/view/t/detail?sid=3387918";
		listMatchItems.add(appItem);


		adapter.setData(listMatchItems);

		listView.setOnItemClickListener(onItemLongClickListener);

	}

	@Override
	public int setLayoutID() {
		return R.layout.activity_tuijian;
	}

	@Override
	protected String getPageName() {
		return "推荐页";
	}

}
