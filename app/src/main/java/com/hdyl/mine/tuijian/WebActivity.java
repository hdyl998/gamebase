package com.hdyl.mine.tuijian;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.hdyl.mine.R;
import com.hdyl.mine.about.AboutActivity;
import com.hdyl.mine.base.BaseActivity;

public class WebActivity extends BaseActivity implements OnClickListener {

    WebView webView;

    @Override
    public void onClick(View arg0) {
        finish();
    }


    @Override
    protected void initData() {
        TextView textView = (TextView) findViewById(R.id.textViewa);
        webView = (WebView) findViewById(R.id.progressBarWebView1);
        String string = getIntent().getStringExtra("url");
        webView.loadUrl(string);
        String titldString = getIntent().getStringExtra("title");
        textView.setText(titldString);
        findViewById(R.id.textView3).setOnClickListener(this);
    }

    @Override
    protected int setView() {
        return R.layout.activity_web;
    }

    @Override
    protected String getPageName() {
        return this.getClass().getSimpleName();
    }

    public static void launch(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}