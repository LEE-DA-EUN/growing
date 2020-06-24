package com.example.sinabro.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class single_line_DBHelper extends SQLiteOpenHelper {

    private static single_line_DBHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "single_line.db";  //DB 이름
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %sTEXT",
                    SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME,
                    SingleLineContract.SLEntry._ID,
                    SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE,
                    SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_CONTENT); //table 생성
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME; //table 삭제

    public static single_line_DBHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new single_line_DBHelper(context);
        }
        return sInstance;
    }

    private single_line_DBHelper(Context context){  // 생성자
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    //
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SingleLineContract.SLEntry.SQL_CREATE_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(SingleLineContract.SLEntry.SQL_DELETE_TABLE);  // 기존 테이블 삭제
//        onCreate(db); // 다시 생성
//    }
//
//    public void insertRecord(String date, String content){  // 날짜 + 내용 추가
//        SQLiteDatabase s_db = getReadableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE, date);
//        values.put(SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_CONTENT, content);
//
//        s_db.insert(SingleLineContract.SLEntry.TABLE_NAME, null, values);
//    }
//
//    public Cursor readRecord(){
//
//        SQLiteDatabase s_db = getReadableDatabase();
//        String[] projrction = {
//                BaseColumns._ID,
//                SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE,
//                SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_CONTENT
//        };
//
//        String sortOrder = SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE + "DESC";  // 내림차순
//
//        Cursor cursor = s_db.query(
//                SingleLineContract.SLEntry.TABLE_NAME,
//                projrction, //값을 가져올 projrction 이름의 배열
//                null, // where 문에 필요한 column
//                null, // where 문에 필요한 value
//                null,
//                null,
//                sortOrder
//        );
//
//        return cursor;
//    }
//
}