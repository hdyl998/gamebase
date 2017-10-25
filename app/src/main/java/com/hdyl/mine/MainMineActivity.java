package com.hdyl.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.hdyl.mine.about.AboutActivity;
import com.hdyl.mine.base.MineBaseActivity;
import com.hdyl.mine.base.ScoreUtils;
import com.hdyl.mine.game.GameActivity;
import com.hdyl.mine.learn.LearnActivity;
import com.hdyl.mine.set.SetActivity;
import com.hdyl.mine.top.TopActivity;
import com.hdyl.mine.top.TopObject;
import com.hdyl.mine.tuijian.TuijianAcitivity;

public class MainMineActivity extends MineBaseActivity implements OnClickListener {
    View view;
    Context context;

    TextView textViewName;
    String name;

    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.imageView1:
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.big_fadein);
                view.startAnimation(animation);
                animation.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation arg0) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        Intent intent = new Intent(context, GameActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.imageViewSet:
                startActivity(new Intent(this, SetActivity.class));
                break;
            case R.id.textViewTopbang:

                startActivity(new Intent(this, TopActivity.class));
                break;
            case R.id.textView1:
            case R.id.textViewUpdate:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.textViewChangeName:
            case R.id.TextView01:
                final EditText editText = new EditText(mContext);
                editText.setText(TopObject.getName(this));
                editText.selectAll();

                new AlertDialog.Builder(mContext)
                        .setTitle("请输入尊姓大名")
                        .setView(editText)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String str = editText.getText().toString().trim();
                                if (str.length() == 0) {
                                    str = "default";
                                }
                                if (str.length() > 10) {
                                    str = str.substring(0, 10);
                                }
                                TopObject.putName(mContext, str);
                                setNameText();
                            }
                        }).show();


//                InputNameDialog dialog = new InputNameDialog(context, name);
//                dialog.setOnDismissListener(dismissListener);
//                dialog.show();
                break;
            case R.id.textViewTuijian:
                startActivity(new Intent(this, TuijianAcitivity.class));
                break;
            case R.id.textViewLearn:
                startActivity(new Intent(this, LearnActivity.class));

                break;
            case R.id.textViewScore:// 给我们评分
                ScoreUtils.getToScore(this);
                break;
        }

    }

    private void setNameText() {
        name = TopObject.getName(this);
        textViewName.setText("你好！$!".replace("$", name));
    }

    private OnDismissListener dismissListener = new OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface arg0) {
            setNameText();
        }
    };

    @Override
    public void initViews() {
        context = this;
        view = findViewById(R.id.imageView1);
        view.setOnClickListener(this);

        findViewById(R.id.TextView01).setOnClickListener(this);
        findViewById(R.id.textViewTopbang).setOnClickListener(this);
        findViewById(R.id.imageViewSet).setOnClickListener(this);
        findViewById(R.id.textViewScore).setOnClickListener(this);
        findViewById(R.id.textViewLearn).setOnClickListener(this);

        findViewById(R.id.textViewTuijian).setOnClickListener(this);
        findViewById(R.id.textViewUpdate).setOnClickListener(this);
        textViewName = (TextView) findViewById(R.id.textViewChangeName);
        textViewName.setOnClickListener(this);
        setNameText();

        findViewById(R.id.textView1).setOnClickListener(this);
    }

    @Override
    public int setLayoutID() {
          return R.layout.activity_main_mine;
    }

    @Override
    protected String getPageName() {
        return "首页";
    }

}
