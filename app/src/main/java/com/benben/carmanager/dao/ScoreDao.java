package com.benben.carmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.benben.carmanager.model.Score;
import com.benben.carmanager.utils.Api;


public class ScoreDao {
    private static final String TAG = "ScoreDao";
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private Context context;

    //构造方法
    public ScoreDao(Context context){
        this.context=context;
    }

    //更新方法
    public void update(int userId,int score)  {
        Log.d(TAG, "update: 更新了成绩...."+score);

        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        int oldMaxScore= Api.maxScore;
        ContentValues values=new ContentValues();
        if(oldMaxScore<score){
            values.put("maxScore",score);
        }
        values.put("preScore",score);
        db.update("tb_score",values,"userId=?",new String[]{userId+""});
        //关闭数据库
        db.close();
        helper.close();
    }


    //查找方法
    public Score find(int userId){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        String sql="select * from tb_score where userId=?";
        Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(userId)});
        if (cursor.moveToNext()){

            int _id=cursor.getInt(cursor.getColumnIndex("_id"));
            String maxScore=cursor.getString(cursor.getColumnIndex("maxScore"));
            String preScore=cursor.getString(cursor.getColumnIndex("preScore"));

            Score score=new Score(Integer.parseInt(maxScore),Integer.parseInt(preScore),userId);
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return score;
        }
        //关闭游标和数据库
        cursor.close();
        db.close();
        helper.close();
        return null;
    }



    //更新用户登录Id
    public  void updateUserID(String userID){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("maxScore",0);
        values.put("preScore",0);
        values.put("userId",userID);
        Cursor cursor=db.query("tb_score",new String[]{"userId"},"userId=?",new String[]{userID+""},null,null,null);
        if(cursor.moveToNext()){
            Log.d(TAG, "updateUserID: 已经有这个用户的成绩表了...");
        }else{
            long rows = db.insert("tb_score", null, values);
            if(rows>0){
                Log.d(TAG, "updateUserID: 添加一个新的用户信息到成绩表...");
            }
        }

        //关闭数据库
        db.close();
        helper.close();
    }





}
