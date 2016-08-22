package com.example.administrator.myapplication;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: lyjq(1752095474)
 * Date: 2016-06-17
 * json解析类
 */
public class EzParseJson2Map1 {

    /**
     * 解析数组
     * @param json
     * @return
     */
    public static List<Model> paresJsonFromArray(String json) {
        System.out.println(json);
        List<Model> modelList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                Model itemModel = parseJsonFromObject(object.toString());
                modelList.add(itemModel);
            }
            return modelList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析对象
     * @param json
     * @return
     */
    public static Model parseJsonFromObject(String json) {
        Model itemModel = new Model();
        try {
            JSONObject object = new JSONObject(json);
            Map<String, Object> map = new HashMap<>();
            Iterator keyIter = object.keys();
            Object value = null;
            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                value = object.get(key);
                if (value instanceof String) {
                    //@ judge sb
                    String temp = (String) value;
                    if (temp.startsWith("@")) {
                        String real = temp.substring(1, temp.length());
                        switch (getJsonType(real)) {
                            case JSON_TYPE_ARRAY:
                                char[] strChar = real.substring(1, 2).toCharArray();
                                char secondChar = strChar[0];
                                if (secondChar == '{') {
                                    map.put(key, paresJsonFromArray(real));
                                } else {
                                    map.put(key, real);
                                }
                                break;
                            case JSON_TYPE_OBJECT:
                                map.put(key, parseJsonFromObject(real));
                                break;
                            case JSON_TYPE_ERROR:
                                System.out.println("JSON_TYPE_ERROR");
                                break;
                        }
                    } else {
                        map.put(key, value);
                    }
                } else {
                    switch (getJsonType(value.toString())) {
                        case JSON_TYPE_ARRAY:
                            char[] strChar = value.toString().substring(1, 2).toCharArray();
                            char secondChar = strChar[0];
                            if (secondChar == '{') {
                                map.put(key, paresJsonFromArray(value.toString()));
                            } else {
                                map.put(key, value.toString());
                            }
                            break;
                        case JSON_TYPE_OBJECT:
                            map.put(key, parseJsonFromObject(value.toString()));
                            break;
                        case JSON_TYPE_ERROR:
                            System.out.println("JSON_TYPE_ERROR");
                            break;
                    }
                }
            }
            //set view
            itemModel.setMap(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemModel;
    }

    /**
     * 判断json种类
     * @param json
     * @return
     */
    private static JSON_TYPE getJsonType(String json) {
        if (TextUtils.isEmpty(json)) {
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
        char[] strChar = json.substring(0, 1).toCharArray();
        char firstChar = strChar[0];

        if (firstChar == '{') {
            return JSON_TYPE.JSON_TYPE_OBJECT;
        } else if (firstChar == '[') {
            return JSON_TYPE.JSON_TYPE_ARRAY;
        } else {
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
    }

    private enum JSON_TYPE {
        JSON_TYPE_OBJECT,
        JSON_TYPE_ARRAY,
        JSON_TYPE_ERROR
    }

}
