package com.benben.carmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benben.carmanager.bean.OrderBean;
import com.benben.carmanager.bean.ShareParkBean;
import com.benben.carmanager.model.User;

import java.util.ArrayList;
import java.util.List;

public class ShareParkDao {
    private static final String TAG = "OrderDao";
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private Context context;
    //构造方法
    public ShareParkDao(Context context){
        this.context=context;
    }

    //添加方法,
    public long add(ShareParkBean parkBean) {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        if(TextUtils.isEmpty(parkBean.getParkAddr()) || TextUtils.isEmpty(parkBean.getParkName())){
            return -1;
        }

        ContentValues values=new ContentValues();
        values.put("parkName",parkBean.getParkName());
        values.put("parkAddr",parkBean.getParkAddr());
        values.put("price",parkBean.getPrice());
        values.put("startTime",parkBean.getStartTime());
        values.put("endTime",parkBean.getEndTime());

        long rowId =  db.insert("tb_sharepark",null,values);

        //关闭数据库
        db.close();
        helper.close();

        return rowId;
    }

    //更新方法
    public int update(User users) {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",users.getUsername());
        values.put("password",users.getPassword());
        int rows = db.update("tb_user", values, "_id=?", new String[]{String.valueOf(users.get_id())});
        //关闭数据库
        db.close();
        helper.close();
        return rows;
    }
    //更新方法
    public boolean delete(int id) {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        db.delete("tb_sharepark","_id=?",new String[]{String.valueOf(id)});
        db.close();
        helper.close();
        return true;
    }
    //查找方法
    public User find(int userId){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        String sql="select * from tb_user where _id=?";
        Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(userId)});
        if (cursor.moveToNext()){
            int _id=cursor.getInt(cursor.getColumnIndex("_id"));

            String username=cursor.getString(cursor.getColumnIndex("username"));
            String password=cursor.getString(cursor.getColumnIndex("password"));

            User users=new User(_id,username,password);
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return users;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return null;
    }

    //查找方法
    public User findByName(User user){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        String sql="select * from tb_user where username=?";
        Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(user.getUsername())});
        if (cursor.moveToNext()){
            int _id=cursor.getInt(cursor.getColumnIndex("_id"));

            String username=cursor.getString(cursor.getColumnIndex("username"));
            String password=cursor.getString(cursor.getColumnIndex("password"));
            User users=new User(_id,username,password);
            cursor.close();
            db.close();
            helper.close();
            return users;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return null;
    }


    //查找方法
    public List<ShareParkBean> findAll() {
        helper = new DBOpenHelper(context);
        db = helper.getReadableDatabase();


        List<ShareParkBean> parkBeans = new ArrayList<>();

        String sql = "select * from tb_sharepark";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String parkName = cursor.getString(cursor.getColumnIndex("parkName"));
            String parkAddr = cursor.getString(cursor.getColumnIndex("parkAddr"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String startTime = cursor.getString(cursor.getColumnIndex("startTime"));
            String endTime = cursor.getString(cursor.getColumnIndex("endTime"));

            ShareParkBean parkBean = new ShareParkBean(_id,parkName ,parkAddr,price,startTime,endTime);
            parkBeans.add(parkBean);
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return parkBeans;
    }


}
