package com.guojianyiliao.eryitianshi.MyUtils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_ChatBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zmc on 2017/5/24.
 * 操作聊天数据表
 */

public class ChatDao {

    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public ChatDao(Context context) {
        myDbHelper = new MyDbHelper(context);
    }

    /**
     * 向数据库中添加一条聊天记录
     * @param chat
     */
    public void add(zmc_ChatBean chat){
        db = myDbHelper.getWritableDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS TABLE_CHAT(docName text,disName text,Content text,filePath text,Time text,sendFlag text,msgFlag text)";
        db.execSQL(sql);
        ContentValues values = new ContentValues();
        values.put("docName",chat.getDocName());
        values.put("disName",chat.getDisName());
        values.put("Content",chat.getContent());
        values.put("filePath",chat.getFilePath());
        values.put("Time",chat.getTime());
        values.put("sendFlag",chat.getSendFlag());
        values.put("msgFlag",chat.getMsgFlag());
        db.insert("TABLE_CHAT",null,values);
    }

    /**
     * 更改消息状态
     * @param chat
     */
    public void changeStatus(zmc_ChatBean chat){
        db = myDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("docName",chat.getDocName());
        values.put("disName",chat.getDisName());
        values.put("Content",chat.getContent());
        values.put("filePath",chat.getFilePath());
        values.put("Time",chat.getTime());
        values.put("sendFlag",chat.getSendFlag());
        values.put("msgFlag",chat.getMsgFlag());
        db.update("TABLE_CHAT",values,"Time=?",new String[]{chat.getTime()});
    }

    /**
     * 获取与医生的聊天记录
     * @param docName
     * @return
     */
    public List<zmc_ChatBean> read(String docName){
        Log.e("imChatActivity","docName : "+docName);
        db = myDbHelper.getReadableDatabase();
        List<zmc_ChatBean> list = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery("select * from TABLE_CHAT where docName=?", new String[]{docName});
            while (cursor.moveToNext()) {
                String disName = cursor.getString(cursor.getColumnIndex("disName"));
                String Content = cursor.getString(cursor.getColumnIndex("Content"));
                String filePath = cursor.getString(cursor.getColumnIndex("filePath"));
                String Time = cursor.getString(cursor.getColumnIndex("Time"));
                String sendFlag = cursor.getString(cursor.getColumnIndex("sendFlag"));
                String msgFlag  = cursor.getString(cursor.getColumnIndex("msgFlag"));
                list.add(new zmc_ChatBean(docName,disName,Content,filePath,Time,sendFlag,msgFlag));
            }
        }catch (SQLiteException e){

        }
        return list;
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }
}
