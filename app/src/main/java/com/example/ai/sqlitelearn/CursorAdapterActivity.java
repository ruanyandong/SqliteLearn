package com.example.ai.sqlitelearn;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CursorAdapterActivity extends AppCompatActivity {

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

        MyCursorAdapter adapter = new MyCursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);

    }

    /**
     * CursorAdapter是一个抽象类
     */
    public class MyCursorAdapter extends CursorAdapter{

        public MyCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        /**
         * 表示创建的适配器控件中每个item对应的view对象
         * @param context 上下文
         * @param cursor 数据源cursor
         * @param viewGroup 当前item的父布局
         * @return 每项item的view对象
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View view = LayoutInflater.from(CursorAdapterActivity.this).inflate(R.layout.item,viewGroup,false);
            return view;
        }

        /**
         * 绑定数据
         * @param view 由newView()返回的每项view对象
         * @param context 上下文
         * @param cursor 数据源cursor对象
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView tv_id = view.findViewById(R.id.id_tv);
            TextView tv_image = view.findViewById(R.id.imageId_tv);
            TextView tv_name = view.findViewById(R.id.name_tv);

            /**
             * 整形数据要转化为string
             */
            tv_id.setText(cursor.getInt(cursor.getColumnIndex(MyFavoriteDatabaseHelper.ID))+"");
            tv_image.setText(cursor.getInt(cursor.getColumnIndex(MyFavoriteDatabaseHelper.IMAGE_ID))+"");
            tv_name.setText(cursor.getString(cursor.getColumnIndex(MyFavoriteDatabaseHelper.PLANT_NAME)));
        }


    }
}
