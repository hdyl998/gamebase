package com.hdyl.mine.set;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.base.BaseActivity;
import com.hdyl.mine.tools.MySharepreferences;
import com.hdyl.mine.tools.ShareCacheUtil;
import com.hdyl.mine.tools.ToastUtils;

public class SetActivity extends BaseActivity implements OnClickListener {

	TextView textViewVar;

	EditText editText, editText2, editText3;

	RadioGroup radioGroup;

	int settype;

	View viewInputView;

	ImageView ivSwitch;

	TextView tvSwitch;

	ImageView ivSwitchVer;

	TextView tvSwitchVer;

	boolean isOnVer = true;

	boolean isOn = true;

	TextView tvTheme;

	int theamType = 0;

	TextView tvBg;

	AppSet appSet = AppSet.getInstence();

	@SuppressLint("NewApi")
	public void setFrameEnable() {
		if (Build.VERSION.SDK_INT >= 11) {
			viewInputView.setAlpha(settype == 4 ? 1f : 0.3f);
		} else {
			viewInputView.setVisibility(settype == 4 ? View.VISIBLE : View.GONE);
		}
		boolean isEnable = settype == 4 ? true : false;
		editText.setEnabled(isEnable);
		editText2.setEnabled(isEnable);
		editText3.setEnabled(isEnable);
	}

	private void updateUI() {
		if (isOn) {
			ivSwitch.setImageResource(R.drawable.on_push);
			tvSwitch.setText("开");
		} else {
			ivSwitch.setImageResource(R.drawable.ic_img_in_nowifi);
			tvSwitch.setText("关");
		}
	}

	private void updateUIVer() {
		if (isOnVer) {
			ivSwitchVer.setImageResource(R.drawable.on_push);
			tvSwitchVer.setText("开");
		} else {
			ivSwitchVer.setImageResource(R.drawable.ic_img_in_nowifi);
			tvSwitchVer.setText("关");
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.textViewBack:
				this.finish();
				break;
			case R.id.imagview:
				isOn = !isOn;
				updateUI();
				MySharepreferences.putInt(this, "aa", "sound", isOn ? 0 : 1);
				break;
			case R.id.imagviewVer:
				isOnVer = !isOnVer;
				updateUIVer();
				MySharepreferences.putInt(this, "aa", "vibrator", isOnVer ? 0 : 1);
				break;
			case R.id.linearLayout4:// 主题
				if (theamType == 0) {
					theamType = 1;
				} else {
					theamType = 0;
				}
				appSet.theme = theamType;
				appSet.saveTheme();
				initThemeUI();
				break;
			case R.id.linearLayout5://切换背景
				appSet.increaceBg();
				appSet.saveTheme();
				initThemeBg();
				break;

			default:// SAVE
				if (editText.length() == 0 || editText2.length() == 0 || editText3.length() == 0) {
					ToastUtils.makeTextAndShow(this, "数量不能为空!");
					return;
				}
				int width = Integer.parseInt(editText.getText().toString());
				int height = Integer.parseInt(editText2.getText().toString());
				int num = Integer.parseInt(editText3.getText().toString());

				int arr[] = changeNumState(width, height, num);
				width = arr[0];
				height = arr[1];
				num = arr[2];

				MySharepreferences.putInt(this, "aa", "width", width);
				MySharepreferences.putInt(this, "aa", "height", height);
				MySharepreferences.putInt(this, "aa", "num", num);
				MySharepreferences.putInt(this, "aa", "settype", settype);

				ToastUtils.makeTextAndShow(this, "保存成功");
				this.finish();
				break;
		}

	}

	public final static int[] changeNumState(int width, int height, int num) {
		if (width < 5) {
			width = 5;
		}
		if (width > 200) {
			width = 200;
		}
		if (height < 5) {
			height = 5;
		}
		if (height > 200) {
			height = 200;
		}

		if (num < width * height * 0.02) {
			num = (int) (width * height * 0.02);
			if (num < 3) {
				num = 3;
			}
		} else if (num > width * height * 0.5) {
			num = (int) (width * height * 0.5);
		}
		if (width * height - 9 <= num) {
			num = width * height - 9;
		}
		return new int[] { width, height, num };
	}

	@Override
	protected void initData() {
		editText = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);

		tvBg=(TextView) findViewById(R.id.tv_bg);

		ivSwitch = (ImageView) findViewById(R.id.imagview);
		ivSwitch.setOnClickListener(this);

		findViewById(R.id.linearLayout4).setOnClickListener(this);

		findViewById(R.id.linearLayout5).setOnClickListener(this);

		tvTheme = (TextView) findViewById(R.id.textViewTheme);

		tvSwitch = (TextView) findViewById(R.id.textViewSwitch);
		isOn = MySharepreferences.getInt(this, "aa", "sound") == 0;// 0表示打开的
		updateUI();

		ivSwitchVer = (ImageView) findViewById(R.id.imagviewVer);
		ivSwitchVer.setOnClickListener(this);
		tvSwitchVer = (TextView) findViewById(R.id.textViewVer);
		isOnVer = MySharepreferences.getInt(this, "aa", "vibrator") == 0;// 0表示打开的
		updateUIVer();

		findViewById(R.id.textViewBack).setOnClickListener(this);
		findViewById(R.id.textViewSave).setOnClickListener(this);
		viewInputView = findViewById(R.id.ll_input);

		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
		String str = ShareCacheUtil.getString(this, "size");
		if (str == null) {
			str = "4";
		}
		textViewVar = (TextView) findViewById(R.id.textViewVar);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				textViewVar.setText(arg1 + "");
				ShareCacheUtil.putString(SetActivity.this, "size", arg1 + "");
			}
		});
		seekBar.setProgress(Integer.parseInt(str));
		textViewVar.setText(str + "");

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
					case R.id.radio0:
						settype = 0;
						break;
					case R.id.radio1:
						settype = 1;
						break;
					case R.id.radio2:
						settype = 2;
						break;
					case R.id.radio3:
						settype = 3;
						break;
					case R.id.radio4:
						settype = 4;
						break;
				}
				setFrameEnable();
			}
		});

		settype = MySharepreferences.getInt(this, "aa", "settype");
		int ids[] = { R.id.radio0, R.id.radio1, R.id.radio2, R.id.radio3, R.id.radio4 };
		radioGroup.check(ids[settype]);

		editText.setText(MySharepreferences.getInt(this, "aa", "width") + "");
		editText2.setText(MySharepreferences.getInt(this, "aa", "height") + "");
		editText3.setText(MySharepreferences.getInt(this, "aa", "num") + "");

		setFrameEnable();

		theamType = appSet.theme;
		initThemeUI();
		initThemeBg();
	}

	/***
	 * 设置UI
	 */
	private void initThemeUI() {
		String sss[] = { "经典风格", "WIN7风格" };
		tvTheme.setText(sss[theamType]);
	}

	/***
	 * 设置UI
	 */
	private void initThemeBg() {
		tvBg.setText(appSet.getBgText());
		getWindow().setBackgroundDrawable(getBgDrawable());
	}

	@Override
	protected int setView() {
		return R.layout.activity_set;
	}

	@Override
	protected String getPageName() {
		return "设置";
	}

}
