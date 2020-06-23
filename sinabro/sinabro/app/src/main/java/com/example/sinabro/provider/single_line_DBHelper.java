package com.example.sinabro.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class single_line_DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "single_line.db";
    public static final String  SINGLE_LINE_COLUMN_ID = "id";
    public static final String  SINGLE_LINE_COLUMN_DATE = "date";
    public static final String  SINGLE_LINE_COLUMN_CONTENT = "content";

    public single_line_DBHelper(Context context){  // 생성자
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table single_line" + "(id integer primary key, date text, content text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS content");  // 기존 테이블 삭제
        onCreate(db); // 다시 생성
    }

    public boolean insetSingleLine(String date, String content){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date", date);
        contentValues.put("content", content);

        db.insert("single_line", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from single_line where id=" + id +"", null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SINGLE_LINE_COLUMN_DATE);
        return numRows;
    }

    public boolean updateSingleLine(Integer id, String date, String content){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date", date);
        contentValues.put("content", content);
        db.update("single_line",contentValues,"id=?", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer delectSingleLine(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("single_line","id=?", new String[]{Integer.toString(id)});
    }

    public ArrayList getAllSinlgeLine(){
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from single_line", null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(SINGLE_LINE_COLUMN_ID)));
            res.getString(res.getColumnIndex(SINGLE_LINE_COLUMN_NAME));
            res.moveToNext();
        }
        return arrayList;
    }
}
