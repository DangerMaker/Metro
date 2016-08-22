package com.example.administrator.myapplication;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ez08.view.EzCustomView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class DirView extends GridLayout {

    int margin = 0;
    int item_width = 0;
    int item_height = 0;
    Context mContext;

    int column = 0;
    int row = 0;
    int rowTemp = 0;
    public DirView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DirView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        margin = dip2px(mContext, 6);
        item_width = (int) ((getScreenWidth(mContext) - margin * 7) / 6f);
        item_height = item_width;
        setColumnCount(2);
        setOrientation(GridLayout.VERTICAL);
    }

    public void setData(List<Model> listModel) {
        for (int i = 0; i < listModel.size(); i++) {
            Model model = listModel.get(i);

            if (column + model.getWidth() > 2) {
                column = 0;
                row = rowTemp + row;
                rowTemp = 0;
            }

            View convertView = LayoutInflater.from(mContext).inflate(getResources().getIdentifier(model.getMap().get("ezLayout").toString(), "layout", mContext.getPackageName()), this, false);
            if(convertView instanceof EzCustomView){
                ((EzCustomView)convertView).setContentData(model);
            }

            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) convertView.getLayoutParams();
            lp.width = item_width * model.getWidth() + margin * (model.getWidth() - 1);
            lp.height = item_height * model.getHeight() + margin * (model.getHeight() - 1);

            lp.columnSpec = GridLayout.spec(column, model.getWidth());
            lp.rowSpec = GridLayout.spec(row, model.getHeight());

            lp.setMargins(0, 0, margin, margin);
            addView(convertView);

            column = model.getWidth() + column;
            if (rowTemp < model.getHeight()) {
                rowTemp = model.getHeight();
            }

        }
    }

    public int dip2px(Context context, float dipValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * density + 0.5f);
    }

    @SuppressWarnings("deprecation")
    public int getScreenWidth(Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();
    }

}
