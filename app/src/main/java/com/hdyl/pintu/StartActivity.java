//package com.hdyl.pintu;
//
//import android.content.Intent;
//import android.os.Handler;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.TextView;
//
//
//public class StartLlkActivity extends BaseActivity implements Runnable {
//
//	TextView textView, textViewTitle, textViewBotton;
//
//	@Override
//	public void onBackPressed() {
//		isCancel = true;
//		super.onBackPressed();
//	}
//
//	boolean isCancel = false;
//
//	@Override
//	public void run() {
//		if (isCancel == true) {
//			return;
//		}
//		Intent intent = new Intent(this, MainPintuActivity.class);
//		startActivity(intent);
//		finish();
//	}
//
//	@Override
//	public void onClick(int id) {
//	}
//
//	@Override
//	protected void initData() {
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȥ����Ϣ��
//		setContentView(R.layout.activity_start);
//		textView = (TextView) findViewById(R.id.textView3);
//
//		textViewTitle = (TextView) findViewById(R.id.textView2);
//
//		textViewBotton = (TextView) findViewById(R.id.textView1);
//		new Handler().postDelayed(this, 4000);
//		textView.setVisibility(View.GONE);
//		textViewBotton.setVisibility(View.INVISIBLE);
//		textViewTitle.setVisibility(View.GONE);
//		textView.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				textViewTitle.setVisibility(View.VISIBLE);
//				Animation animation = AnimationUtils.loadAnimation(getApplication(), R.anim.umeng_socialize_fade_in);
//				textViewTitle.startAnimation(animation);
//			}
//		}, 100);
//
//		textView.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//
//				textView.setVisibility(View.VISIBLE);
//
//
//				Animation animation2 = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_in_from_bottom);
//				textView.startAnimation(animation2);
//			}
//		}, 1100);
//
//		textView.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				Animation animation = AnimationUtils.loadAnimation(getApplication(), R.anim.umeng_socialize_fade_in);
//				textViewBotton.startAnimation(animation);
//				textViewBotton.setVisibility(View.VISIBLE);
//			}
//		}, 2500);
//
//	}
//}
