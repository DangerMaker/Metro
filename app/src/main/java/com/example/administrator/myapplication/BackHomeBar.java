package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class BackHomeBar extends RelativeLayout implements View.OnClickListener {

    ImageView back1;
    TextView back2;
    TextView backToHome;

    Activity mActivity;
    Class<?> mClass;
    int mType = 0;
    public BackHomeBar(Context context) {
        super(context);
    }

    public BackHomeBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.back_home_bar, this);
        back1 = (ImageView) view.findViewById(R.id.nav);
        back2 = (TextView) view.findViewById(R.id.last_page);
        backToHome = (TextView) view.findViewById(R.id.to_home);

        back1.setOnClickListener(this);
        back2.setOnClickListener(this);
        backToHome.setOnClickListener(this);

       change();
    }

    /**
     * bar必设置的参数
     * @param activity 当前activity
     * @param mClass 主界面class
     * @param type  0、nothing 1、backtolast 2、both
     */
    public void setParams(Activity activity,Class<?> mClass,int type) {
        mActivity = activity;
        this.mClass = mClass;
        this.mType = type;

        change();
    }

    private void change(){
        if(mType == 0){
            back1.setVisibility(VISIBLE);
            back2.setVisibility(GONE);
            backToHome.setVisibility(GONE);
        }else if(mType == 1){
            back1.setVisibility(VISIBLE);
            back2.setVisibility(VISIBLE);
            backToHome.setVisibility(GONE);
        }
        else if(mType == 2){
            back1.setVisibility(VISIBLE);
            back2.setVisibility(VISIBLE);
            backToHome.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.nav) {
            if (mActivity != null) {
                mActivity.finish();
            } else {
                throw new NullPointerException("activity == null in BackHomeBar");
            }
        } else if (id == R.id.last_page) {
            if (mActivity != null) {
                mActivity.finish();
            } else {
                throw new NullPointerException("activity == null in BackHomeBar");
            }
        } else if (id == R.id.to_home) {
            if (mClass != null) {
                mActivity.startActivity(new Intent(mActivity,mClass));
            } else {
                throw new NullPointerException("mClass == null in BackHomeBar");
            }
        }
    }
}
