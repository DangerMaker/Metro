package com.example.administrator.myapplication;

import com.ez08.tools.MapItem;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class Model extends MapItem{

    String text;
    String img;
    // 1 1X1
    // 2 2X1
    // 3 2X2
    // 4 4X2
    // 5 4X4
    int width = 0;
    int height = 0;
    List<Model> subList;
    int type;

    public List<Model> getSubList() {
        return subList;
    }

    public void setSubList(List<Model> subList) {
        this.subList = subList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getWidth() {
       wh();
        return width;
    }

    public int getHeight() {
        wh();
        return height;
    }

    public int getType() {
        return Integer.parseInt(getMap().get("type").toString());
    }

    public void setType(int type) {
       getMap().put("type",type);
    }

    private void wh(){
        if(getMap() != null){
            switch (Integer.parseInt(getMap().get("type").toString())){
                case 1:
                    width = 1;
                    height = 1;
                    break;
                case 2:
                    width = 2;
                    height = 1;
                    break;
                case 3:
                    width = 2;
                    height = 2;
                    break;
                case 4:
                    width = 4;
                    height = 2;
                    break;
                case 5:
                    width = 4;
                    height = 4;
                    break;
                case 6:
                    width = 2;
                    height = 2;
                    break;
            }
        }
    }
}
