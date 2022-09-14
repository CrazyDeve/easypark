package com.benben.carmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benben.carmanager.bean.OrderBean;
import com.benben.carmanager.model.User;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final String TAG = "OrderDao";
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private Context context;
    //构造方法
    public OrderDao(Context context){
        this.context=context;
    }

    //添加方法,
    public long add(OrderBean orderBean) {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        if(TextUtils.isEmpty(orderBean.getCarNumber()) || TextUtils.isEmpty(orderBean.getParkName())){
            return -1;
        }

        ContentValues values=new ContentValues();
        values.put("parkName",orderBean.getParkName());
        values.put("parkAddr",orderBean.getParkAddr());
        values.put("lat",orderBean.getLat());
        values.put("lng",orderBean.getLng());
        values.put("carNumber",orderBean.getCarNumber());
        values.put("startTime",orderBean.getStartTime());
        values.put("endTime",orderBean.getEndTime());

        long rowId =  db.insert("tb_order",null,values);

        //关闭数据库
        db.close();
        helper.close();

        return rowId;
    }

    //更新方法

    /**
     * 1 已支付 0 未支付
     * @param id
     * @param status
     * @return
     */
    public int update(String  id, String status) {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("status",status);
        int rows = db.update("tb_order", values, "_id=?", new String[]{String.valueOf(id)});
        //关闭数据库
        db.close();
        helper.close();
        return rows;
    }
    //更新方法
    public void delete(User users) {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        db.delete("tb_word","_id=?",new String[]{String.valueOf(users.get_id())});
        db.close();
        helper.close();
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
    public List<OrderBean> findAll() {
        helper = new DBOpenHelper(context);
        db = helper.getReadableDatabase();


        List<OrderBean> orders = new ArrayList<>();

        String sql = "select * from tb_order";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String parkName = cursor.getString(cursor.getColumnIndex("parkName"));
            String parkAddr = cursor.getString(cursor.getColumnIndex("parkAddr"));
            String lat = cursor.getString(cursor.getColumnIndex("lat"));
            String lng = cursor.getString(cursor.getColumnIndex("lng"));
            String carNumber = cursor.getString(cursor.getColumnIndex("carNumber"));
            String startTime = cursor.getString(cursor.getColumnIndex("startTime"));
            String endTime = cursor.getString(cursor.getColumnIndex("endTime"));
            String status = cursor.getString(cursor.getColumnIndex("status"));


            OrderBean orderBean = new OrderBean(_id,parkName ,parkAddr, lat,lng,carNumber,startTime,endTime,status);
            orders.add(orderBean);
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return orders;
    }


}
