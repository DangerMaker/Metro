package com.example.administrator.myapplication;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.ez08.EZApplication;
import com.ez08.drupal.EzDrupalHelper;
import com.ez08.module.auth.EzAuthHelper;
import com.ez08.module.zone.EzZoneHelper;
import com.ez08.tools.EZGlobalProperties;
import com.umeng.socialize.PlatformConfig;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2016/8/17.
 */
public class MyApplication extends EZApplication {


    public static MyApplication sInstance;

    @Override
    public void onCreate() {
        sInstance = this;
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx595f22b2886de6db", "c85a4b10172f2f719c54a2341117b5b5");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("2066634295","d8b292ebb32de0f5613b6a11ed710966");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105455904", "n3kkKAdjwoJLBHFm");

        EZGlobalProperties.USER_URL = getResources().getString(R.string.http_url);
        EZGlobalProperties.IMAGE_URL = getResources().getString(R.string.http_img_url);
        EZGlobalProperties.mainuri = "com.example.administrator.myapplication.MainActivity";
        EzAuthHelper.init(sInstance,"http://123.57.226.248:81/dev/userapi/");
        EzZoneHelper.init(sInstance,"http://123.57.226.248:81/dev/userapi/");
        EzDrupalHelper.init("http://123.57.226.248:81/dev/userapi/","http://123.57.226.248:81/dev/userapi/");
        super.onCreate();

        EZGlobalProperties.activityMap.put("EZWebViewController", "com.example.administrator.myapplication.WebActivity");

    }

    public static synchronized MyApplication getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }


    @Override
    public boolean loginSuccess(Context context, String result, Response response) {
        return false;
    }

    @Override
    public void loginFailure(Context context, RetrofitError error) {

    }

    @Override
    public void logoutSuccess(Context context, String result, Response response) {

    }

    @Override
    public void logoutFailure(Context context, RetrofitError error) {

    }

    @Override
    public boolean registSuccess(Context context, String result, Response response) {
        return false;
    }

    @Override
    public void registFailure(Context context, RetrofitError error) {

    }
}
