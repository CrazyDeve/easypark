package com.benben.carmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benben.carmanager.model.User;

public class UserDao {
    private static final String TAG = "UserDao";
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private Context context;
    //构造方法
    public UserDao(Context context){
        this.context=context;
    }

    //添加方法,
    public long add(User users) {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        if(TextUtils.isEmpty(users.getUsername()) || TextUtils.isEmpty(users.getPassword())){
            return -1;
        }

        ContentValues values=new ContentValues();
        values.put("username",users.getUsername());
        values.put("password",users.getPassword());
        values.put("nickName",users.getNickName());
        values.put("idNumber",users.getIdNumber());
        values.put("name",users.getName());

        long rowId =  db.insert("tb_user",null,values);

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
        values.put("nickName",users.getNickName());
        values.put("password",users.getPassword());
        int rows = db.update("tb_user", values, "_id=?", new String[]{String.valueOf(users.get_id())});
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
            String nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            String idNumber=cursor.getString(cursor.getColumnIndex("idNumber"));

            User users=new User(_id,username,password,nickName,idNumber);
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
            String nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            String idNumber=cursor.getString(cursor.getColumnIndex("idNumber"));

            User users=new User(_id,username,password,nickName,idNumber);
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


    public User login(User user){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        String sql="select * from tb_user where username=? and password=?";
        Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(user.getUsername()), String.valueOf(user.getPassword())});
        if (cursor.moveToNext()){
            int _id=cursor.getInt(cursor.getColumnIndex("_id"));

            String username=cursor.getString(cursor.getColumnIndex("username"));
            String password=cursor.getString(cursor.getColumnIndex("password"));
            String nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            String idNumber=cursor.getString(cursor.getColumnIndex("idNumber"));
            User users=new User(_id,username,password,nickName,idNumber);
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


}
