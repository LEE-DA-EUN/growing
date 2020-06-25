package com.example.sinabro.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class review_note_DBHelper extends SQLiteOpenHelper {

    private static review_note_DBHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "review_note.db";  //DB 이름
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %sTEXT)",
                    reviewnotecontract.RNEntry.REVIEW_NOTE_TABLE_NAME,
                    reviewnotecontract.RNEntry._ID,
                    reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_TITLE,
                    reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_DATE,
                    reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_CONTENT); //table 생성

    private static final String SQL_DELECTE_ENTRIES =
            "DROP TABLE IF EXISTS " + reviewnotecontract.RNEntry.REVIEW_NOTE_TABLE_NAME; //table 삭제

    public static review_note_DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new review_note_DBHelper(context);
        }
        return sInstance;
    }

    private review_note_DBHelper(Context context){  // 생성자
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
}

 
