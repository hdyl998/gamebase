package com.hdyl.gamebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hdyl.baselib.utils.ToastUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textView1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ToastUtils.makeTextAndShow("aa", R.drawable.ic_launcher);
    }
}
