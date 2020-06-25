package com.example.sinabro.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class main_DBHelper extends SQLiteOpenHelper {

    private static main_DBHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "main.db";  //DB 이름
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %sTEXT)",
                    MainContract.MainEntry.MAIN_TABLE_NAME,
                    MainContract.MainEntry._ID,
                    MainContract.MainEntry.SINGLE_LINE_COLUMN_GOAL,
                    MainContract.MainEntry.SINGLE_LINE_COLUMN_DDAY); //table 생성
    private static final String SQL_DELECTE_ENTRIES =
            "DROP TABLE IF EXISTS " + MainContract.MainEntry.MAIN_TABLE_NAME; //table 삭제

    public static main_DBHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new main_DBHelper(context);
        }
        return sInstance;
    }

    private main_DBHelper(Context context){  // 생성자
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELECTE_ENTRIES);
        onCreate(db);
    }
