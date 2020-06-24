package com.example.sinabro.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class single_line_DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "single_line.db";  //DB 이름

    public single_line_DBHelper(Context context){  // 생성자
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SingleLineContract.SLEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SingleLineContract.SLEntry.SQL_DELETE_TABLE);  // 기존 테이블 삭제
        onCreate(db); // 다시 생성
    }

    public void insetRecord(String date, String content){  // 날짜 + 내용 추가
        SQLiteDatabase s_db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE, date);
        values.put(SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_CONTENT, content);

        s_db.insert(SingleLineContract.SLEntry.TABLE_NAME, null, values);
    }

    public Cursor readRecord(){

        SQLiteDatabase s_db = getReadableDatabase();
        String[] projrction = {
                BaseColumns._ID,
                SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE,
                SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_CONTENT
        };

        String sortOrder = SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE + "DESC";  // 내림차순

        Cursor cursor = s_db.query(
                SingleLineContract.SLEntry.TABLE_NAME,
                projrction, //값을 가져올 projrction 이름의 배열
                null, // where 문에 필요한 column
                null, // where 문에 필요한 value
                null,
                null,
                sortOrder
        );

        return cursor;
    }

}
