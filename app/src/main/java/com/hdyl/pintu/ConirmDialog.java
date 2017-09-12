package com.hdyl.pintu;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hdyl.mine.R;

public class ConirmDialog extends Dialog {

	TextView tvTitle, tvContent, tvOK, tvCancel;

	public ConirmDialog(Context context1, String title, String content, String okBtn, String cancelBtn, final android.view.View.OnClickListener okClick, final android.view.View.OnClickListener calcelClick) {
		super(context1, R.style.my_dialog);
		setContentView(R.layout.dialog_info);

		tvTitle = (TextView) findViewById(R.id.textView1);
		tvContent = (TextView) findViewById(R.id.textView2);

		tvOK = (TextView) findViewById(R.id.button1);
		tvCancel = (TextView) findViewById(R.id.button2);

		tvTitle.setText(title);
		tvContent.setText(content);

		tvOK.setText(okBtn);
		tvCancel.setText(cancelBtn);

		tvOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
				if (okClick != null) {
					okClick.onClick(arg0);
				}
			}
		});
		if (calcelClick == null) {
			tvCancel.setVisibility(View.GONE);
		} else {
			tvCancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dismiss();
					if (calcelClick != null) {
						calcelClick.onClick(arg0);
					}
				}
			});
		}

		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
		// | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	public ConirmDialog(Context context1, String title, String content, android.view.View.OnClickListener okClick, android.view.View.OnClickListener calcelClick) {
		this(context1, title, content, "确认", "取消", okClick, calcelClick);
	}

	public ConirmDialog(Context context1, String content, android.view.View.OnClickListener okClick, android.view.View.OnClickListener calcelClick) {
		this(context1, "提示", content, "确认", "取消", okClick, calcelClick);
	}

	public ConirmDialog(Context context1, String content) {
		this(context1, "提示", content, "确认", "取消", null, null);
	}
}
