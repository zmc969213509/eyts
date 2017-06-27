package com.guojianyiliao.eryitianshi.MyUtils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_DocChat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmc on 2017/5/26.
 * 保存用户与医生聊过天的医生列表
 */

public class DoctorDao {

    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public DoctorDao(Context context) {

        myDbHelper = new MyDbHelper(context);
//        db = myDbHelper.getWritableDatabase();
//        db.execSQL(sql);
    }

    /**
     * 保存聊天医生信息
     * @param chat
     */
    public void add(zmc_DocChat chat){
        db = myDbHelper.getWritableDatabase();
        // 当表不存在就创建
        String sql = "CREATE TABLE IF NOT EXISTS TABLE_DOC(userName text PRIMARY KEY,docName text,docId text,docIcon text)";
        db.execSQL(sql);
        ContentValues values = new ContentValues();
        values.put("userName",chat.getUserName());
        values.put("docName",chat.getDocName());
        values.put("docId",chat.getDocId());
        values.put("docIcon",chat.getDocIcon());
        try{
            db.insert("TABLE_DOC",null,values);
        }catch (Exception e){

        }
    }

    /**
     * 获取所有医生信息
     * @return
     */
    public List<zmc_DocChat> getAllDoc(){
        List<zmc_DocChat> list = new ArrayList<>();

        db = myDbHelper.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("select * from TABLE_DOC", null);
            while(cursor.moveToNext()){
                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                String docName = cursor.getString(cursor.getColumnIndex("docName"));
                String docId = cursor.getString(cursor.getColumnIndex("docId"));
                String docIcon = cursor.getString(cursor.getColumnIndex("docIcon"));
                list.add(new zmc_DocChat(docName,docId,docIcon,userName));
            }
        }catch (SQLiteException e){

        }
        return list;
    }

    /**
     * 获取聊天医生信息
     * @param userName
     * @return
     */
    public zmc_DocChat getDocInfo(String userName){
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from TABLE_DOC where userName=?", new String[]{userName});
        if (cursor.moveToNext()) {
            String docName = cursor.getString(cursor.getColumnIndex("docName"));
            String docId = cursor.getString(cursor.getColumnIndex("docId"));
            String docIcon = cursor.getString(cursor.getColumnIndex("docIcon"));
            return new zmc_DocChat(docName,docId,docIcon,userName);
        }
        return null;
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }
}
