package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.drag.SelfStockSettingActivity;
import com.ez08.tools.EzParseJson2Map;
import com.ez08.tools.MapItem;
import com.ez08.view.EzCustomView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    GridLayout gridLayout;
    TextView textView;
    int margin = 0;
    int item_width = 0;
    int item_height = 0;
    public List<Model> data;
    public static List<Model> initData;

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PATH = getExternalCacheDir() + File.separator + "metroScrolldata.txt";

        data = new ArrayList<>();
        initData = new ArrayList<>();

        margin = dip2px(this, 6);
        item_width = (int) ((getScreenWidth(this) - margin * 7) / 6f);
        item_height = item_width;
        gridLayout = (GridLayout) findViewById(R.id.grid);
        textView = (TextView) findViewById(R.id.txt_right);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                Intent intent = new Intent(MainActivity.this, SelfStockSettingActivity.class);
//                startActivityForResult(intent, 100);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_logo);
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant =
                                palette.getVibrantSwatch();
                        if (vibrant != null) {
                            // If we have a vibrant color
                            // update the title TextView
                            gridLayout.setBackgroundColor(vibrant.getRgb());
                        }
                    }
                });
            }
        });
        gridLayout.setColumnCount(6);
        gridLayout.setOrientation(GridLayout.VERTICAL);
        inflateData();

//        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        gridLayout.removeAllViews();
        Iterator<Model> iterator = initData.iterator();
        while (iterator.hasNext()) {
            Model model = iterator.next();
            data.add(model);
        }

        //data整理
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getType() == 1) {
                List<Model> listModel = new ArrayList<>();
                listModel.add(data.get(i));

                if (i + 1 < data.size() && data.get(i + 1).getType() == 1) {

                    if (i + 2 < data.size() && data.get(i + 2).getType() == 1) {

                        if (i + 3 < data.size() && data.get(i + 3).getType() == 1) {
                            listModel.add(data.get(i + 1));
                            listModel.add(data.get(i + 2));
                            listModel.add(data.get(i + 3));
                            Model model = new Model();
                            model.setType(6);
                            model.setSubList(listModel);

                            data.remove(i + 3);
                            data.remove(i + 2);
                            data.remove(i + 1);
                            data.remove(i);
                            data.add(i, model);
                        } else {
                            listModel.add(data.get(i + 1));
                            listModel.add(data.get(i + 2));
                            Model model = new Model();
                            model.setType(6);
                            model.setSubList(listModel);

                            data.remove(i + 2);
                            data.remove(i + 1);
                            data.remove(i);
                            data.add(i, model);
                        }

                    } else if (i + 2 < data.size() && data.get(i + 2).getType() == 2) {
                        listModel.add(data.get(i + 1));
                        listModel.add(data.get(i + 2));
                        Model model = new Model();
                        model.setType(6);
                        model.setSubList(listModel);

                        data.remove(i + 2);
                        data.remove(i + 1);
                        data.remove(i);
                        data.add(i, model);
                    } else {
                        listModel.add(data.get(i + 1));
                        Model model = new Model();
                        model.setType(6);
                        model.setSubList(listModel);

                        data.remove(i + 1);
                        data.remove(i);
                        data.add(i, model);
                    }

                } else if (i + 1 < data.size() && data.get(i + 1).getType() == 2) {
                    listModel.add(data.get(i + 1));
                    Model model = new Model();
                    model.setType(6);
                    model.setSubList(listModel);

                    data.remove(i + 1);
                    data.remove(i);
                    data.add(i, model);

                } else {
                    Model model = new Model();
                    model.setType(6);
                    model.setSubList(listModel);

                    data.remove(i);
                    data.add(i, model);
                }
            } else if (data.get(i).getType() == 2) {
                List<Model> listModel = new ArrayList<>();
                listModel.add(data.get(i));

                if (i + 1 < data.size() && data.get(i + 1).getType() == 1) {

                    if (i + 2 < data.size() && data.get(i + 2).getType() == 1) {
                        listModel.add(data.get(i + 1));
                        listModel.add(data.get(i + 2));
                        Model model = new Model();
                        model.setType(6);
                        model.setSubList(listModel);

                        data.remove(i + 2);
                        data.remove(i + 1);
                        data.remove(i);
                        data.add(i, model);
                    } else {
                        listModel.add(data.get(i + 1));
                        Model model = new Model();
                        model.setType(6);
                        model.setSubList(listModel);

                        data.remove(i + 1);
                        data.remove(i);
                        data.add(i, model);
                    }

                } else if (i + 1 < data.size() && data.get(i + 1).getType() == 2) {
                    listModel.add(data.get(i + 1));
                    Model model = new Model();
                    model.setType(6);
                    model.setSubList(listModel);

                    data.remove(i + 1);
                    data.remove(i);
                    data.add(i, model);

                } else {
                    Model model = new Model();
                    model.setType(6);
                    model.setSubList(listModel);

                    data.remove(i);
                    data.add(i, model);
                }
            }
        }


        int column = 0;
        int row = 0;
        int columnTemp = 0;
        int rowTemp = 0;

        List<Integer> heightList = new ArrayList<>();
        ;
        boolean enter = false;
        for (Model model : data) {

            if (column + model.getWidth() > 6) {

                if (rowTemp == 4 && model.getWidth() == 2) {
                    if (heightList.get(0) == 4) {
                        column = 4;
                        row = row + 2;
                        rowTemp = 2;
                    } else {
                        column = 0;
                        row = row + 2;
                        rowTemp = 2;
                        enter = true;
                    }
                } else {
                    column = 0;
                    row = rowTemp + row;
                    rowTemp = 0;
                    columnTemp = 0;
                    heightList.clear();
                }
            } else {
                if (enter) {
                    column = 0;
                    row = rowTemp + row;
                    rowTemp = 0;
                    columnTemp = 0;
                    heightList.clear();
                    enter = false;
                }
            }


            View convertView;
            if (model.getType() == 6) {
                convertView = LayoutInflater.from(this).inflate(R.layout.min_cell_layout, gridLayout, false);
                ((DirView) convertView).setData(model.getSubList());
            } else {
                convertView = LayoutInflater.from(this).inflate(getResources().getIdentifier(model.getMap().get("ezLayout").toString(), "layout", getPackageName()), gridLayout, false);
                if(convertView instanceof EzCustomView){
                    ((EzCustomView)convertView).setContentData(model);
                }
            }

            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) convertView.getLayoutParams();
            lp.width = item_width * model.getWidth() + margin * (model.getWidth() - 1);
            lp.height = item_height * model.getHeight() + margin * (model.getHeight() - 1);

            lp.columnSpec = GridLayout.spec(column, model.getWidth());
            lp.rowSpec = GridLayout.spec(row, model.getHeight());

            lp.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);
            gridLayout.addView(convertView);

            column = model.getWidth() + column;
            if (rowTemp < model.getHeight()) {
                heightList.add(model.getHeight());
                rowTemp = model.getHeight();
            }

            if (columnTemp < model.getWidth()) {
                columnTemp = model.getWidth();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Intent intent = new Intent(MainActivity.this, SelfStockSettingActivity.class);
//                intent.putExtra("content",getData());
                startActivityForResult(intent, 100);
                break;
            default:
                break;
        }
        return true;
    }

    private String getData() {
//        File file = new File(PATH);
//        StringBuffer sb = new StringBuffer();
//        if (file.exists()) {
//            try {
//                FileInputStream fis = new FileInputStream(file);
//                byte[] bbuf = new byte[1024];
//                int hasRead = 0;
//                while ((hasRead = fis.read(bbuf)) > 0) {
//                    System.out.println(new String(bbuf, 0, hasRead));
//                    sb.append(new String(bbuf));
//                }
//                fis.close();
//                return sb.toString();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
            AssetManager am = getAssets();
            try {
                InputStream is = am.open("metroScrolldata.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuffer sb2 = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb2.append(line);
                }
                String json = sb2.toString();
                Log.e("content", json);

                saveData(sb2.toString());
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
        return null;
    }

    private String PATH = "";

    private void saveData(String str) {
        File file = new File(PATH);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            os.write(str.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void inflateData() {
        json = getData();
        List<Model> item = EzParseJson2Map1.paresJsonFromArray(json);
        initData.addAll(item);
    }

    void initData(){
        Model model1 = new Model();
        model1.setText("1");
        model1.setType(1);

        Model model2 = new Model();
        model2.setText("2");
        model2.setType(1);

        Model model3 = new Model();
        model3.setText("3");
        model3.setType(1);

        Model model4 = new Model();
        model4.setText("4");
        model4.setType(1);

        Model model5 = new Model();
        model5.setText("5");
        model5.setType(4);

        Model model6 = new Model();
        model6.setText("6");
        model6.setType(5);

        Model model7 = new Model();
        model7.setText("7");
        model7.setType(3);

        Model model8 = new Model();
        model8.setText("8");
        model8.setType(3);
//
        Model model9 = new Model();
        model9.setText("9");
        model9.setType(4);

        Model model10 = new Model();
        model10.setText("10");
        model10.setType(3);
//
        Model model11 = new Model();
        model11.setText("11");
        model11.setType(2);

        Model model12 = new Model();
        model12.setText("12");
        model12.setType(1);

        Model model13 = new Model();
        model13.setText("13");
        model13.setType(1);
//
        Model model14 = new Model();
        model14.setText("14");
        model14.setType(5);
//
        Model model15 = new Model();
        model15.setText("15");
        model15.setType(3);


        initData.add(model1);
        initData.add(model2);
        initData.add(model3);
        initData.add(model4);
        initData.add(model5);
//
        initData.add(model6);
        initData.add(model7);
        initData.add(model8);
//
        initData.add(model9);
        initData.add(model10);
//
        initData.add(model11);
        initData.add(model12);
        initData.add(model13);
        initData.add(model14);
        initData.add(model15);
    }
}
