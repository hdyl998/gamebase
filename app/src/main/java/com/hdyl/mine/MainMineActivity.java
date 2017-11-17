package com.hdyl.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.hdyl.MainInActivity;
import com.hdyl.baselib.base.interfaceImpl.AnimationListenerImpl;
import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.llk.utils.SpDataCache;
import com.hdyl.mine.about.AboutActivity;
import com.hdyl.mine.base.LoadingDialog;
import com.hdyl.mine.base.MineBaseActivity;
import com.hdyl.mine.base.ScoreUtils;
import com.hdyl.mine.game.GameActivity;
import com.hdyl.mine.learn.LearnActivity;
import com.hdyl.mine.set.SetActivity;
import com.hdyl.mine.stage.MineStageActivity;
import com.hdyl.mine.tools.PermissionUtils;
import com.hdyl.mine.tools.Tools;
import com.hdyl.mine.top.TopActivity;
import com.hdyl.mine.top.TopObject;
import com.hdyl.mine.tuijian.TuijianAcitivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMineActivity extends MineBaseActivity implements OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
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
                animation.setAnimationListener(new AnimationListenerImpl() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
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
                ScoreUtils.getToMarket(this);
                break;
            case R.id.textViewFight:
                startActivity(new Intent(mContext, MineStageActivity.class));
                break;
            case R.id.textViewHide:
                startActivity(new Intent(mContext, MainInActivity.class));
                break;
        }

    }

    private void setNameText() {
        name = TopObject.getName(this);
        textViewName.setText("你好！$!".replace("$", name));
    }

    View viewHide;

    @Override
    public void initViews() {

        Uri uri = Uri.parse("qqshidao://app:match://654875/1");
        LogUitls.print("test", uri.getScheme());

        String allCmd = uri.getSchemeSpecificPart().substring(2);
        LogUitls.print("test", allCmd);
        int indexYinhao = allCmd.indexOf(":");
        if (indexYinhao != -1) {
            String type = allCmd.substring(0, indexYinhao);
            String action = allCmd.substring(indexYinhao + 1);
            LogUitls.print("test", type);
            LogUitls.print("test", action);
        }


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

        viewHide = findViewById(R.id.textViewHide);
        viewHide.setOnClickListener(this);

        findViewById(R.id.textViewFight).setOnClickListener(this);
        textViewName = (TextView) findViewById(R.id.textViewChangeName);
        textViewName.setOnClickListener(this);
        setNameText();

        findViewById(R.id.textView1).setOnClickListener(this);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);

        boolean isHide = checkTimeHide();

        LogUitls.print(isHide);
        viewHide.setVisibility(isHide ? View.GONE : View.VISIBLE);

        SpDataCache cache = new SpDataCache();
        if (isHide == false && null == cache.get("hidefun")) {
            cache.put("hidefun", "1");
            LoadingDialog dialog = new LoadingDialog(mContext);
            dialog.setTvText("隐藏关卡已开启，欢迎使用！点击右上角的“惊喜”进入！");
            dialog.show();
        }
    }


    private String startTime = "2017-10-30 12:33:00";

    private boolean checkTimeHide() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time = format.parse(startTime).getTime();
            long hour = (new Date().getTime() - time) / 1000 / 3600;
            LogUitls.print(hour);
            return hour < 24;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public int setLayoutID() {
        return R.layout.activity_main_mine;
    }

    @Override
    protected String getPageName() {
        return "首页";
    }

    PermissionUtils.PermissionGrant permissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {

        }
    };

}
