package com.example.sinabro.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class review_note_DBHelper extends SQLiteOpenHelper {

    private static review_note_DBHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "review_note.db";  //DB 이름

    // 테이블을 생성
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + reviewnotecontract.RNEntry.REVIEW_NOTE_TABLE_NAME + " (" +
            reviewnotecontract.RNEntry._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_TITLE + " TEXT NOT NULL, " +
            reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_DATE + " TEXT NOT NULL, " +
            reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_CONTENT + " TEXT NOT NULL)";

    private static final String SQL_DELECTE_ENTRIES =
            "DROP TABLE IF EXISTS " + reviewnotecontract.RNEntry.REVIEW_NOTE_TABLE_NAME; //table 삭제

    public static review_note_DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new review_note_DBHelper(context);
        }
        return sInstance;
    }

    public review_note_DBHelper(Context context){  // 생성자
        super(context, DB_NAME, null, DB_VERSION);
    }

    public ArrayList<ReviewNoteDTO> selectReviewNoteTable() {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM "+ reviewnotecontract.RNEntry.REVIEW_NOTE_TABLE_NAME;

        ArrayList<ReviewNoteDTO> reviewNoteList = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            ReviewNoteDTO reviewNote = new ReviewNoteDTO();
            reviewNote.setId(cursor.getInt(0));
            reviewNote.setTitle(cursor.getString(1));
            reviewNote.setDate(cursor.getString(2));
            reviewNote.setContents(cursor.getString(3));

            reviewNoteList.add(reviewNote);
        }
        return reviewNoteList;
    }

    public void insertReviewNoteData(ReviewNoteDTO reviewNote) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_TITLE, reviewNote.getTitle());
        values.put(reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_DATE, reviewNote.getDate());
        values.put(reviewnotecontract.RNEntry.SINGLE_LINE_COLUMN_CONTENT, reviewNote.getContents());

        db.insert(reviewnotecontract.RNEntry.REVIEW_NOTE_TABLE_NAME, null, values);
    }

    public void deleteReviewNoteData(int _id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(reviewnotecontract.RNEntry.REVIEW_NOTE_TABLE_NAME, "_id=" + _id, null);
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
