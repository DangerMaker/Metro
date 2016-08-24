package com.example.administrator.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.tools.MapItem;
import com.ez08.view.EzCustomView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Created by Administrator on 2016/8/24.
 */
public class EzTypeView3 extends RelativeLayout implements EzCustomView {
    MapItem model;

    TextView name;
    CustomTextView price;
    TextView percent;
    TextView tip;
    MaterialIconView arrow;

    boolean inited = false;

    public EzTypeView3(Context context) {
        super(context);
    }

    public EzTypeView3(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private void initData() {
        if (model.getMap().get("title") != null) {
            name.setText(model.getMap().get("title").toString());
        }

        if (model.getMap().get("price") != null) {
            price.setText(model.getMap().get("price").toString());
        }

        if (model.getMap().get("percent") != null) {
            percent.setText(model.getMap().get("percent").toString());
        }

        if (model.getMap().get("tip") != null) {
            tip.setText(model.getMap().get("tip").toString());
        }

        if (model.getMap().get("arrow") != null) {
            String flag = model.getMap().get("arrow").toString();
            if (flag.equals("0")) {
                arrow.setIcon(MaterialDrawableBuilder.IconValue.ARROW_UP_BOLD);
                setBackgroundColor(getResources().getColor(R.color.colorRed));
            } else if (flag.equals("1")) {
                arrow.setIcon(MaterialDrawableBuilder.IconValue.ARROW_DOWN_BOLD);
                setBackgroundColor(getResources().getColor(R.color.colorGreen));
            } else if (flag.equals("2")) {
                arrow.setVisibility(View.GONE);
                price.setText("- -");
                percent.setVisibility(GONE);
                tip.setText("停牌");
                setBackgroundColor(getResources().getColor(R.color.colorGray));
            } else {
                arrow.setVisibility(View.GONE);
                tip.setText("持平");
                setBackgroundColor(getResources().getColor(R.color.colorYellow));
            }
        }
    }

    private boolean initView() {
        name = (TextView) findViewWithTag("title");
        price = (CustomTextView) findViewWithTag("price");
        percent = (TextView) findViewWithTag("percent");
        tip = (TextView) findViewWithTag("tip");
        arrow = (MaterialIconView) findViewWithTag("arrow");
        return true;
    }

    @Override
    public void setContentData(Object data) {
        model = (MapItem) data;
        if (!inited) {
            inited = initView();
        }
        initData();
    }
}
