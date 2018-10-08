package com.example.ai.sqlitelearn;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
/**
 * 演示Sqlite适配器的使用
 */
public class SimpleCursorAdapterActivity extends AppCompatActivity {

    private ListView listView;
    private MyFavoriteDatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.listView);
        databaseHelper = MyFavoriteDatabaseHelper.getInstance(getApplicationContext());

        //1、创建数据库并往里面添加数据
        /**
         * 往数据库里添加数据
         */
        for (int i = 0; i < 30; i++) {
            databaseHelper.insert(i+1,"hello"+i);
        }

        //2、获取数据库查询的数据源
        Cursor cursor = databaseHelper.getAllData();
        //3、将数据源数据加载到适配器中
        /**
         * public SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
         *     }
         *     Context context 上下文
         *     int layout item的布局id
         *     Cursor cursor 数据源
         *     String[] from 表示cursor中数据字段的数组
         *     int[] to 表示展示字段对应值的控件资源id
         *     int flags 设置适配器的标记（观察者模式)
         *
         *   注意：SimpleCursorAdapter的数据源主键必须为 _id
         */
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item,cursor,
                new String[]{MyFavoriteDatabaseHelper.ID,
                        MyFavoriteDatabaseHelper.IMAGE_ID,
                        MyFavoriteDatabaseHelper.PLANT_NAME},
                new int[]{R.id.id_tv,R.id.imageId_tv,R.id.name_tv},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        //4、将适配器的数据加载到控件
        listView.setAdapter(adapter);
    }

    /**
     * 往已有的数据库里查询数据
     */
    /*private void query(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+
                File.separator+"info.db";
        *//**
         * openDatabase(String path,CursorFactory factory,int flag);
         * String path 表示当前打开数据库存放的路径
         * CursorFactory factory
         * int flag 表示打开数据库的操作模式
         *//*
        db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from "+MyFavoriteDatabaseHelper.TABLE_NAME,null);

    }*/



}
