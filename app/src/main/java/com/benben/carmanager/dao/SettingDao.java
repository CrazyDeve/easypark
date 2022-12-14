package com.benben.carmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.benben.carmanager.model.Setting;


public class SettingDao {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private Context context;

    //构造方法
    public SettingDao(Context context){
        this.context=context;
    }

    //更新方法
    public void update(Setting setting)  {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("_id",setting.get_id());
        values.put("sock",setting.getSock());
        values.put("difficulty",setting.getDifficulty());
        values.put("newnum",setting.getNewnum());
        values.put("socknum",setting.getSocknum());
        values.put("theme",setting.getTheme());
        values.put("userID",setting.getUserId());
        db.update("tb_setting",values,"_id=1",null);
        //关闭数据库
        db.close();
        helper.close();
    }
    //更新用户登录Id
    public  void updateUserID(String userID){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("userID",userID);
        db.update("tb_setting",values,"_id=1",null);
        //关闭数据库
        db.close();
        helper.close();
    }
    //更新是否开启单词锁屏
    public void updateSock(int sock)  {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("sock",sock);
        db.update("tb_setting",values,"_id=1",null);
        //关闭数据库
        db.close();
        helper.close();
    }

    //更新主题
    public void updateTheme(int theme){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("theme",theme);
        db.update("tb_setting",values,"_id=1",null);
        //关闭数据库
        db.close();
        helper.close();
    }

    //更新困难度
    public void updateDifficulty(String difficulty) {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("difficulty",difficulty);
        db.update("tb_setting",values,"_id=1",null);
        //关闭数据库
        db.close();
        helper.close();
    }
    //更新每天需要背的单词数
    public void updateNewNum(int newnum)  {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("newnum",newnum);
        db.update("tb_setting",values,"_id=1",null);
        //关闭数据库
        db.close();
        helper.close();
    }
    //更新解锁屏幕的单词书
    public void updateSockNum(int socknum)  {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("socknum",socknum);
        db.update("tb_setting",values,"_id=1",null);
        //关闭数据库
        db.close();
        helper.close();
    }
    //返回设置信息
    public Setting getSetting()  {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_setting where _id=1";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            int _id=cursor.getInt(cursor.getColumnIndex("_id"));
            int sock=cursor.getInt(cursor.getColumnIndex("sock"));
            String difficulty=cursor.getString(cursor.getColumnIndex("difficulty"));
            int newnum=cursor.getInt(cursor.getColumnIndex("newnum"));
            int socknum=cursor.getInt(cursor.getColumnIndex("socknum"));
            int theme=cursor.getInt(cursor.getColumnIndex("theme"));
            Setting setting=new Setting(_id,sock,difficulty,newnum,socknum,theme);
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return setting;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return null;
    }
    //获取目前的主题样式
    public int getTheme(){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_setting where _id=1";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            int theme=cursor.getInt(cursor.getColumnIndex("theme"));
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return theme;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return 0;
    }

    //获取难度
    public String getDifficulty() {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_setting where _id=1";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            String difficulty=cursor.getString(cursor.getColumnIndex("difficulty"));
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return difficulty;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return null;
    }

    //获取锁屏背单词是否开启
    public int getSock(){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_setting where _id=1";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            int sock=cursor.getInt(cursor.getColumnIndex("sock"));
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return sock;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return 0;
    }

    //获取每天需要背的词数量
    public int getNewNum(){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_setting where _id=1";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            int newnum=cursor.getInt(cursor.getColumnIndex("newnum"));
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return newnum;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return 0;
    }

    //获取锁屏需要解锁的单词数量
    public int getSockNum() {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_setting where _id=1";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            int socknum=cursor.getInt(cursor.getColumnIndex("socknum"));
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return socknum;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return 0;
    }

    //获取设置中挑战单词总数量
    public int getChallengeTotalNum() {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_setting where _id=1";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            int socknum=cursor.getInt(cursor.getColumnIndex("challengeWordTotalNum"));
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return socknum;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return 0;
    }

    //获取用户ID
    public String getUserID() {
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_setting where _id=1";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            String socknum=cursor.getString(cursor.getColumnIndex("userID"));
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return socknum;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return null;
    }
}
