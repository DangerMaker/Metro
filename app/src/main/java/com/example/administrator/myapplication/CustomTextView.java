package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.widget.TextView;

import com.ez08.view.EzCustomView;

/**
 * Created by Administrator on 2016/8/23.
 */
public class CustomTextView extends TextView implements EzCustomView{
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface typeFace =Typeface.createFromAsset(context.getAssets(),"fonts/digital.ttf");
        this.setTypeface(typeFace);
    }

    @Override
    public void setContentData(Object data) {
        this.setText((String)data);
    }
}
