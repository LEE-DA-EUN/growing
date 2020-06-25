package com.example.sinabro.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class single_line_DBHelper extends SQLiteOpenHelper {

    private static single_line_DBHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "single_line.db";  //DB 이름

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME + " (" +
            SingleLineContract.SLEntry._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE + " TEXT NOT NULL, " +
            SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_CONTENT + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME; //table 삭제

    public static single_line_DBHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new single_line_DBHelper(context);
        }
        return sInstance;
    }

    public single_line_DBHelper(Context context){  // 생성자
        super(context, DB_NAME, null, DB_VERSION);
    }

    public ArrayList<SingleLineDTO> selectSingleLineTable() {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM "+ SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME;

        ArrayList<SingleLineDTO> singleLineList = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            SingleLineDTO singleLine = new SingleLineDTO();
            singleLine.setId(cursor.getInt(0));
            singleLine.setDate(cursor.getString(1));
            singleLine.setContents(cursor.getString(2));

            singleLineList.add(singleLine);
        }
        return singleLineList;
    }

    public void insertSingleLineData(SingleLineDTO singleLine) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE, singleLine.getDate());
        values.put(SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_CONTENT, singleLine.getContents());

        db.insert(SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME, null, values);
    }

    public void deleteSingleLineData(int _id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME, "_id=" + _id, null);
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

}