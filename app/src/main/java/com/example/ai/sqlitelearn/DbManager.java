package com.example.ai.sqlitelearn;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    /**
     * 根据数据库以及数据表名称获取数据总条目
     * @param db 数据库对象
     * @param tableName 数据表名称
     * @return 数据总条目
     */
    public static int getDataCount(SQLiteDatabase db,String tableName){

        int count = 0;
        if (db != null){
            Cursor cursor = db.rawQuery("select * from "+tableName,null);
            count = cursor.getCount();//数据总条目
        }

        return count;
    }

    /**
     * 根据当前页码查询获取该页码对应的集合数据
     * @param db 数据库对象
     * @param tableName 数据表名称
     * @param currentPage 当前页码
     * @return 当前页对应的集合
     *
     * select * from plant ？，？
     * 0,20    1
     * 20,40   2
     * 40,60   3
     */
    public static List<Plant> getListByCurrentPage(SQLiteDatabase db,String tableName,int currentPage,int pageSize){
        /**
         * 当前页码第一条数据的下标
         */
        int index = (currentPage - 1)*pageSize;

        Cursor cursor = null;
        if (db != null){
            String sql = "select * from "+tableName+" limit ?,?";
            cursor = db.rawQuery(sql,new String[]{index+"",pageSize+""});
        }

        return cursorToList(cursor);
    }

    private static List<Plant> cursorToList(Cursor cursor) {

        List<Plant> list = new ArrayList<>();
        while (cursor.moveToNext()){
            int _id = cursor.getInt(cursor.getColumnIndex(MyFavoriteDatabaseHelper.ID));
            int imageId = cursor.getInt(cursor.getColumnIndex(MyFavoriteDatabaseHelper.IMAGE_ID));
            String name = cursor.getString(cursor.getColumnIndex(MyFavoriteDatabaseHelper.PLANT_NAME));

            Plant plant = new Plant(_id,imageId,name);
            list.add(plant);
        }
        return list;
    }


}
