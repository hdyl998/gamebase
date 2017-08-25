package com.hdyl.mine.base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.hdyl.mine.R;
import com.hdyl.mine.top.TopObject;

public class InputNameDialog extends Dialog implements View.OnClickListener {

	EditText textView;

	public InputNameDialog(Context context, String name) {
		super(context, R.style.my_dialog);
		this.setCancelable(true);
		setContentView(R.layout.dialog_input_name);
		textView = (EditText) findViewById(R.id.editText1);
		textView.setText(name);
		textView.selectAll();
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button1:
			String str = textView.getText().toString().trim();
			if (str.length() == 0) {
				str = "default";
			}
			if (str.length() > 10) {
				str = str.substring(0, 10);
			}
			TopObject.putName(getContext(), str);
			dismiss();
			break;

		default:
			break;
		}
	}
}
