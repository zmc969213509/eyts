package com.guojianyiliao.eryitianshi.MyUtils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/24.
 */

public class MyDbHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "USER_DB";
    private static final int VERSION = 1;

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE TABLE_CHAT(docName text,disName text,Content text,filePath text,Time text,sendFlag text,msgFlag text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
