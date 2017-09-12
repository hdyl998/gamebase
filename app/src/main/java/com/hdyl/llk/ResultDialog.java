package com.hdyl.llk;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdyl.mine.R;

public class ResultDialog extends Dialog implements android.view.View.OnClickListener {

	MainLlkActivity gameActi;
	View btnQuit, btnReplay, btnNext;
	ImageView imageRank;
	TextView textInfo;

	public ResultDialog(Context context, boolean isWin, int  index) {
		super(context, R.style.dialog);
		this.setContentView(R.layout.dialog_result);
		this.setCancelable(false);
		getWindow().setWindowAnimations(R.style.dialogAnimation2);

		gameActi = (MainLlkActivity) context;
		initIdEvent();
		int id[] = { R.drawable.point0, R.drawable.point1, R.drawable.point2, R.drawable.point3 };
		imageRank.setImageResource(id[index]);
		if (isWin) {
			textInfo.setText("胜利啦！再来一局");
		} else {
			textInfo.setText("别气馁！好成绩属于你");
			btnNext.setVisibility(View.GONE);
		}
	}

	private void initIdEvent() {
		btnQuit = this.findViewById(R.id.btn_dia_quit);
		btnReplay = this.findViewById(R.id.btn_dia_replay);
		btnNext = this.findViewById(R.id.btn_dia_next);

		imageRank = (ImageView) findViewById(R.id.image_dia_result);
		textInfo = (TextView) findViewById(R.id.text_dia_info);

		btnQuit.setOnClickListener(this);
		btnReplay.setOnClickListener(this);
		btnNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		v.setEnabled(false);
		this.dismiss();
		switch (v.getId()) {
			case R.id.btn_dia_quit:
				gameActi.finish();
				break;
			case R.id.btn_dia_replay:
				gameActi.newGame();
				break;
			case R.id.btn_dia_next:
				gameActi.nextLevel();
				break;
		}
	}
}
