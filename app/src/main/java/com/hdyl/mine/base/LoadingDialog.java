package com.hdyl.mine.base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.tools.Tools;

public class LoadingDialog extends Dialog implements OnClickListener {

	TextView textView;
	private View.OnClickListener clickListener;

	public LoadingDialog(Context context, boolean cancelable) {
		super(context, R.style.my_dialog);
		this.setCanceledOnTouchOutside(cancelable);
		setContentView(R.layout.dialog_loading);
		textView = (TextView) findViewById(R.id.textView1);
		findViewById(R.id.button1).setOnClickListener(this);

	}

	public LoadingDialog(Context context) {
		this(context, false);
	}

	public LoadingDialog(Context context, String msg) {
		this(context, false);
		textView.setText(msg);
	}

	public LoadingDialog(Context context, String title, String left, String right, View.OnClickListener clickListener) {
		this(context, false);
		textView.setText(title);
		this.clickListener = clickListener;
		Button view001 = (Button) findViewById(R.id.button1);
		view001.setOnClickListener(this);
		view001.setText(left);
		Button view002 = (Button) findViewById(R.id.button2);
		view002.setOnClickListener(this);
		view002.setVisibility(View.VISIBLE);
		view002.setText(right);
	}

	public LoadingDialog(Context context, String title, String left, String right, String down, View.OnClickListener clickListener) {
		this(context, false);
		this.clickListener = clickListener;
		textView.setText(title);
		Button view001 = (Button) findViewById(R.id.button1);
		view001.setOnClickListener(this);
		view001.setText(left);
		Button view002 = (Button) findViewById(R.id.button2);
		view002.setOnClickListener(this);
		view002.setVisibility(View.VISIBLE);
		view002.setText(right);

		Button view003 = (Button) findViewById(R.id.button3);
		view003.setOnClickListener(this);
		view003.setVisibility(View.VISIBLE);
		view003.setText(down);

	}

	public void setIconResID(int res) {
		Tools.setTextViewDrawable(textView, res, 0);
	}

	public void setTvText(String msg) {
		textView.setText(msg);
	}

	@Override
	public void onClick(View arg0) {
		if (clickListener != null) {
			clickListener.onClick(arg0);
		}
		// switch (arg0.getId()) {
		// case R.id.button1:
		// break;
		// case R.id.button2:
		// break;
		// case R.id.button3:
		// break;
		// }
		dismiss();
	}
}
