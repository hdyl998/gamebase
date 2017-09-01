package com.hdyl.mine.learn;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.base.BaseActivity;
import com.hdyl.mine.tuijian.WebActivity;

public class LearnActivity extends BaseActivity {

	@Override
	public void onClick(View arg0) {

		String string = ((TextView) arg0).getText().toString().substring(2);
		switch (arg0.getId()) {
			case R.id.textView1:
				ImageView imageView = new ImageView(mContext);
				imageView.setImageResource(R.drawable.test);
				new AlertDialog.Builder(this).setTitle(string).setView(imageView).setNegativeButton("确定", null).show();
				break;
			case R.id.textView2:
				new AlertDialog.Builder(this).setTitle(string).setMessage(R.string.introduce).setNegativeButton("确定", null).show();
				break;
			case R.id.textView3:
				startWebView("http://jingyan.baidu.com/article/3065b3b6c9eef9becff8a405.html", string);
				break;
			case R.id.textView4:
				startWebView("http://jingyan.baidu.com/article/b0b63dbfd6178e4a4930706b.html", string);
				break;
			case R.id.textViewBack:
				finish();
				break;
			case R.id.textView5:
				ImageView imageView2 = new ImageView(mContext);
				imageView2.setImageResource(R.drawable.ic_ai);
				new AlertDialog.Builder(this).setTitle(string).setView(imageView2).setNegativeButton("确定", null).show();

				break;
		}
	}

	private void startWebView(String url, String aaa) {
		Intent intent = new Intent(this, WebActivity.class);
		intent.putExtra("title", aaa);
		intent.putExtra("url", url);
		startActivity(intent);
	}

	@Override
	protected void initData() {
		findViewById(R.id.textView1).setOnClickListener(this);
		findViewById(R.id.textView2).setOnClickListener(this);
		findViewById(R.id.textView3).setOnClickListener(this);
		findViewById(R.id.textView4).setOnClickListener(this);
		findViewById(R.id.textView5).setOnClickListener(this);
		findViewById(R.id.textViewBack).setOnClickListener(this);
	}

	@Override
	protected int setView() {
		return R.layout.activity_learn;
	}

	@Override
	protected String getPageName() {
		return "学习中心";
	}

}
