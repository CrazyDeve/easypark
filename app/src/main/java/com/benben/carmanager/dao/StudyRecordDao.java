package com.benben.carmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.benben.carmanager.model.StudyRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class StudyRecordDao {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private Context context;
    private DateFormat df;
    private static final String TAG = "StudyRecordDao";
    //构造方法
    public StudyRecordDao(Context context){
        this.context=context;
        df = new SimpleDateFormat("yyyy-MM-dd");
    }

    //添加方法
    public void add(StudyRecord studyRecord){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("date", studyRecord.getDate());
        values.put("newNum", studyRecord.getNewNum());
        values.put("repeatNum", studyRecord.getRepeatNum());
        values.put("needNewNum", studyRecord.getNeedNewNum());
        values.put("needRepeatNum", studyRecord.getNeedRepeatNum());
        values.put("difficulty", studyRecord.getDifficulty());
        db.insert("tb_studyrecord",null,values);
        //关闭数据库
        db.close();
        helper.close();
    }
    //更新方法
    public void update(StudyRecord studyRecord){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("date", studyRecord.getDate());
        values.put("newNum", studyRecord.getNewNum());
        values.put("repeatNum", studyRecord.getRepeatNum());
        values.put("needNewNum", studyRecord.getNeedNewNum());
        values.put("needRepeatNum", studyRecord.getNeedRepeatNum());
        db.update("tb_studyrecord",values,"_id=?",new String[]{String.valueOf(studyRecord.get_id())});
        //关闭数据库
        db.close();
        helper.close();
    }

    public String isHasYestodayData(){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        String sql="select * from tb_studyrecord";
        Cursor cursor=db.rawQuery(sql,null);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH,-1);
        String yestoday = df.format(instance.getTime());
        Log.d(TAG, "isHasYestodayData: 昨天的数据 == "+yestoday);
        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex("date"));
            if(date.equals(yestoday)){
                cursor.close();
                db.close();
                helper.close();
                return date;
            }
        }
        cursor.close();
        db.close();
        helper.close();
        return null;
    }

    //插入或者返回方法,若有今天数据就返回，没有就新建
    public StudyRecord addOrGet(){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        SettingDao settingDao=new SettingDao(context);
        final Calendar c= Calendar.getInstance();//获取系统当前数据

        String date0=df.format(c.getTime());
        Log.d(TAG, "addOrGet: 格式化完成的当日日期 == "+date0);
        String difficulty=settingDao.getDifficulty();
        String sql="select * from tb_studyrecord where date=?";
        Cursor cursor=db.rawQuery(sql,new String[]{date0});
        StudyRecord studyRecord;
        if (cursor.moveToNext()){
            int _id=cursor.getInt(cursor.getColumnIndex("_id"));
            String date=cursor.getString(cursor.getColumnIndex("date"));
            int firstNum=cursor.getInt(cursor.getColumnIndex("newNum"));
            int repeatNum=cursor.getInt(cursor.getColumnIndex("repeatNum"));
            //获取最新的每天需要背的单词数量
            int needNewNum=settingDao.getNewNum();
            int needRepeatNum=cursor.getInt(cursor.getColumnIndex("needRepeatNum"));
            studyRecord =new StudyRecord(_id,date,firstNum,repeatNum,needNewNum,needRepeatNum,difficulty);
            cursor.close();
            db.close();
            helper.close();
            update(studyRecord);
        }else {//没有就新建
            Log.i("今日首次进软件","新建了今日背单词的数据");
            int firstNum=0;
            int repeatNum=0;
            int needNewNum=settingDao.getNewNum();
            WordRecordDao wordRecordDao =new WordRecordDao(context);
            int needRepeatNum= wordRecordDao.getTypeReWordCount();
            studyRecord =new StudyRecord(date0,firstNum,repeatNum,needNewNum,needRepeatNum,difficulty);
            add(studyRecord);
        }
        return studyRecord;
    }

    //返回列表中的7条数据
    public ArrayList<StudyRecord> getWeekRecord(){
        ArrayList<StudyRecord> studyRecord_list =new ArrayList<StudyRecord>();
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        SettingDao settingDao=new SettingDao(context);
        String difficulty=settingDao.getDifficulty();
        String sql="select * from tb_studyrecord  where difficulty=? order by date DESC limit 7 ";
        Cursor cursor=db.rawQuery(sql,new String[]{difficulty});
        while (cursor.moveToNext()){
            int _id=cursor.getInt(cursor.getColumnIndex("_id"));
           String date=cursor.getString(cursor.getColumnIndex("date"));
            int newNum=cursor.getInt(cursor.getColumnIndex("newNum"));
            int repeatNum=cursor.getInt(cursor.getColumnIndex("repeatNum"));
            int needNewNum=cursor.getInt(cursor.getColumnIndex("needNewNum"));
            int needRepeatNum=cursor.getInt(cursor.getColumnIndex("needRepeatNum"));
            StudyRecord studyRecord =new StudyRecord(_id,date,newNum,repeatNum,needNewNum,needRepeatNum,difficulty);
            studyRecord_list.add(studyRecord);
        }
        cursor.close();
        db.close();
        helper.close();
        Collections.reverse(studyRecord_list);//对数据进行翻转，将今天放在最后
        //Log.i("hahaha","---------------------------->"+ studyRecord_list.size());
        return studyRecord_list;
    }

    /**
     * 获取所有学习的天数
     * @return
     */
    public ArrayList<String> getAllStudyDays(){
        ArrayList<String> allDays =new ArrayList<>();
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();
        SettingDao settingDao=new SettingDao(context);
        String difficulty=settingDao.getDifficulty();
        String sql="select date from tb_studyrecord order by date desc";
        Cursor cursor=db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String date=cursor.getString(cursor.getColumnIndex("date"));
            allDays.add(date);
        }
        cursor.close();
        db.close();
        helper.close();
        return allDays;
    }

    //返回总共学习的天数
    public int getAllStudyDayCount(){
        helper=new DBOpenHelper(context);
        db=helper.getReadableDatabase();

        String sql="select count(Distinct date) from tb_studyrecord";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            int count= cursor.getInt(0);
            //关闭游标和数据库
            cursor.close();
            db.close();
            helper.close();
            return count;
        }
        return 0;
    }

    //更新今天已经背的单词数
    public void updatefirstNum(){
        StudyRecord studyRecord =addOrGet();
        studyRecord.setNewNum(studyRecord.getNewNum()+1);
        update(studyRecord);
    }
    //更新今天已经复习的单词数
    public void updatereviewNum(){
        StudyRecord studyRecord =addOrGet();
        studyRecord.setRepeatNum(studyRecord.getRepeatNum()+1);
        update(studyRecord);
    }

}
