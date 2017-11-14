package com.hdyl.baselib.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import java.io.Serializable;

/**
 * Created by liugd on 2017/7/25.
 */

public class FmtCtnActivity extends BaseActivity {
    @Override
    public void initViews() {
        Class<BaseFragment> clazz = (Class<BaseFragment>) getIntent().getSerializableExtra(EXTRA_FRAGMENT_NAME);
        FragmentManager manager = getSupportFragmentManager();
        try {
            manager.beginTransaction().add(android.R.id.content, clazz.newInstance()).commit();//加到根布局
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int setLayoutID() {
        return 0;
    }

    private final static String EXTRA_FRAGMENT_NAME = "fragment_name";

    public final static String EXTRA_1 = "1";
    public final static String EXTRA_2 = "2";
    public final static String EXTRA_3 = "3";
    public final static String EXTRA_4 = "4";
    public final static String EXTRA_5 = "5";
    public final static String EXTRA_6 = "6";

    public static void launch(Context context, Class<? extends BaseFragment> clazz, Object... objs) {
        Intent intent = new Intent(context, FmtCtnActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_NAME, clazz);
        if (objs != null && objs.length != 0) {
            int count = 1;
            for (Object o : objs) {
                if (o instanceof Serializable) {
                    intent.putExtra(count + "", (Serializable) o);
                    count++;
                } else {
                    new IllegalArgumentException("参数非法，必须实现Serializable接口");
                }
            }
        }
        context.startActivity(intent);
    }


}
