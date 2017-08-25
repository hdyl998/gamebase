package com.hdyl.mine.top;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.base.BaseActivity;
import com.hdyl.mine.tools.DatabaseHelper;
import com.hdyl.mine.tools.MyPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopActivity extends BaseActivity implements OnClickListener {

	ViewPager viewPager;
	PagerSlidingTabStrip pagerTabStrip;

	ListView listViews[] = new ListView[4];

	MyListAdapter adapters[] = new MyListAdapter[4];

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		finish();
	}

	// 设置listView暂无数据
	public void setAdapterViewNoDataShow(AdapterView adapterView, String msg) {
		TextView emptyView = new TextView(adapterView.getContext());
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		if (msg == null)
			msg = "暂无数据，敬请挑战！";
		emptyView.setTextSize(15.78f);// ——28px
		emptyView.setText(msg);
		emptyView.setTextColor(Color.parseColor("#464646"));
		emptyView.setVisibility(View.GONE);
		emptyView.setGravity(Gravity.CENTER);
		((ViewGroup) adapterView.getParent()).addView(emptyView);
		adapterView.setEmptyView(emptyView);
	}

	@Override
	protected void initData() {

		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText(TopObject.getName(this) + " 的排行榜");

		findViewById(R.id.textViewBack).setOnClickListener(this);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabStrip1);

		DatabaseHelper helper = new DatabaseHelper(this);
		List<View> list = new ArrayList<View>();
		for (int i = 0; i < 4; i++) {
			View view = View.inflate(this, R.layout.listview_item, null);
			listViews[i] = (ListView) view.findViewById(R.id.listview);
			adapters[i] = new MyListAdapter(this, helper.selectAllInfos(i));
			listViews[i].setAdapter(adapters[i]);
			setAdapterViewNoDataShow(listViews[i], null);
			list.add(view);
		}
		viewPager.setAdapter(new MyPagerAdapter(list, Arrays.asList(TopObject.getLevelName())));
		pagerTabStrip.setViewPager(viewPager);

		int aa = getIntent().getIntExtra("aa", 0);
		if (aa == 4)
			aa = 0;
		viewPager.setCurrentItem(aa);
	}

	@Override
	protected int setView() {
		return R.layout.activyti_top;
	}

	@Override
	protected String getPageName() {
		// TODO Auto-generated method stub
		return "排行榜";
	}
}
