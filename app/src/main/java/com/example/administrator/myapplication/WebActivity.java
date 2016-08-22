package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ez08.tools.MapItem;
import com.umeng.socialize.UMShareAPI;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-26
 */
public class WebActivity extends AppCompatActivity implements View.OnClickListener{

    WebView webView;
    public MapItem mapItem;
    String url = "";

    BackHomeBar homeBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview1);
        mapItem = (MapItem) getIntent().getSerializableExtra("cell");
        webView = (WebView) findViewById(R.id.webview);

        homeBar = (BackHomeBar) findViewById(R.id.homebar);
        homeBar.setParams(this,MainActivity.class,2);

        Log.e("title",mapItem.getMap().get("title").toString());

        MapItem action = (MapItem) mapItem.getMap().get("ezAction");
        if(action.getMap().get("ezTargetData") != null){
            url = ((MapItem)action.getMap().get("ezTargetData")).getMap().get("ezDataUrl").toString();
        }

        if(action.getMap().get("targetData") != null){
            url = ((MapItem)action.getMap().get("targetData")).getMap().get("ezDataUrl").toString();
        }


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        System.out.println(url);
        webView.loadUrl(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

    }
}
