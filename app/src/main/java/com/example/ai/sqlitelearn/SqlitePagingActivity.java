package com.example.ai.sqlitelearn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库分页
 *
 * select * from person limit 0,15;
 * 当前页码第一条数据下标0,每页展示15条
 *
 * 总条目
 * 每页条数
 * 总页码
 * 页码
 *
 * 1、页码为1时在listView中展示对应的数据
 * 2、当listView加载完成本页数据分页加载下一页数据
 */
public class SqlitePagingActivity extends AppCompatActivity {

    private ListView listView;
    private MyFavoriteDatabaseHelper databaseHelper;
    private int totalNum;//表示当前控件加载数据的总条目
    private SQLiteDatabase db;
    private int pageSize = 20;//每页展示条目
    private int pageNum;// 表示总页码

    private int currentPage = 1;// 当前页码

    private List<Plant> totalList = new ArrayList<>();

    private MyBaseAdapter adapter;

    private boolean isDivPage;//是否分页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        listView = findViewById(R.id.listView);
        databaseHelper = MyFavoriteDatabaseHelper.getInstance(getApplicationContext());
        db = databaseHelper.getWritableDatabase();
        totalNum = DbManager.getDataCount(db,MyFavoriteDatabaseHelper.TABLE_NAME);

        /**
         * ceil：大于或等于指定表达式的值的最小整数
         *
         * 总页数
         */
        pageNum = (int)Math.ceil(totalNum/pageSize);

        if (currentPage == 1){
            totalList = DbManager.getListByCurrentPage(db,MyFavoriteDatabaseHelper.TABLE_NAME,currentPage,pageSize);
        }

        adapter = new MyBaseAdapter(this,totalList);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isDivPage && AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState){

                    if (currentPage<pageNum){
                        currentPage++;
                        totalList.addAll(DbManager.getListByCurrentPage(db,MyFavoriteDatabaseHelper.TABLE_NAME,currentPage,pageSize));
                        adapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage = firstVisibleItem+visibleItemCount == totalItemCount;
            }
        });



    }


    public class MyBaseAdapter extends BaseAdapter{
        private Context context;
        private List<Plant> list;

        public MyBaseAdapter(Context context,List<Plant> list){
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
                holder = new ViewHolder();
                holder.tv_id = convertView.findViewById(R.id.id_tv);
                holder.tv_image = convertView.findViewById(R.id.imageId_tv);
                holder.tv_name = convertView.findViewById(R.id.name_tv);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tv_id.setText(list.get(position).get_id()+"");
            holder.tv_image.setText(list.get(position).getImageId()+"");
            holder.tv_name.setText(list.get(position).getName());

            return convertView;
        }

        class ViewHolder{
            TextView tv_id,tv_name,tv_image;
        }
    }

}
