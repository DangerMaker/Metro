package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DragActivity extends AppCompatActivity {
    private static String PATH  ;
    private RecyclerView mRecy;
//    private List<String> mList=new ArrayList<>();
    private TextView txtTitle,txtComplete;
    private View mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        PATH = getExternalCacheDir() + File.separator + "metroScrolldata.txt";
        mRecy = (RecyclerView) findViewById(R.id.recy);
        init();
    }

    private void init() {

        txtComplete= (TextView) findViewById(R.id.txt_complete);
        txtTitle= (TextView) findViewById(R.id.toolbar_title);
        mBack=findViewById(R.id.btn_go_back);
        txtComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Iterator<String> iterator = mList.iterator();
//                StringBuffer sb = new StringBuffer();
//                sb.append("[");
//                for (int i = 0; i < mList.size(); i++) {
//                    sb.append(iterator.next());
//                    if (i != mList.size() - 1) {
//                        sb.append(",");
//                    }
//                }
//                sb.append("]");
//                saveData(sb.toString());
//                setResult(RESULT_OK,new Intent());
//                finish();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtTitle.setText("长按拖动排序");
//        Intent intent=getIntent();
//        String content=intent.getStringExtra("content");
//
//        try {
//            JSONArray array=new JSONArray(content);
//            for(int i=0;i<array.length();i++){
//                String obj=array.getString(i);
//                mList.add(obj);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecy.setLayoutManager(manager);
        mRecy.addItemDecoration(new ItemDecor(this, LinearLayoutManager.VERTICAL));
        ItemDragHelperCallback callback = new ItemDragHelperCallback(){
            @Override
            public boolean isLongPressDragEnabled() {
                // 长按拖拽打开
                return true;
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);

        DragAdapter adapter = new DragAdapter(this, MainActivity.initData);
        mRecy.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

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
}