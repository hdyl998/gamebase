package com.hdyl.xiaoxiaole;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hdyl.mine.R;

public class MainXxlActivity extends Activity {

	GameView gameView;
	TextView textView,textView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_xiaoxiaole);
		gameView=(GameView) findViewById(R.id.gameView1);
		textView=(TextView) findViewById(R.id.textScore);
		textView2=(TextView) findViewById(R.id.textHighScore);

		SharedPreferences preferences = getSharedPreferences("myxml", Activity.MODE_PRIVATE);
		int high=preferences.getInt("high", 0);
		textView2.setText(String.valueOf(high));
	}
	public void onclick(View v){
		switch(v.getId()){
			case R.id.button2:finish();break;
			case R.id.button1:newGame();break;
		}
	}
	private void newGame() {
		gameView.newGame();
	}
	public void setScore(int score){
		textView.setText(String.valueOf(score));
	}
	public void setHighScore(int score){
		textView2.setText(String.valueOf(score));
	}
	public void saveScore(int score){
		SharedPreferences preferences = getSharedPreferences("myxml", Activity.MODE_PRIVATE);

		SharedPreferences.Editor editor = preferences.edit();
		// 保存组件中的值
		editor.putInt("high", score);
		// 提交保存的结果
		editor.commit();
	}
}
