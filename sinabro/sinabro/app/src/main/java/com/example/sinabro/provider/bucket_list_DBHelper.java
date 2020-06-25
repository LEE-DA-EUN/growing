package com.example.sinabro.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class bucket_list_DBHelper extends SQLiteOpenHelper {
    private static bucket_list_DBHelper sInstance;
    private static final int DB_VERSION=1;
    private static final String  DB_NAME="Memo.db";
    // 테이블을 생성
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT ,%s CHAR[10], %s TEXT, %s TEXT)",
                    BucketListContract.ListEntry.TABLE_NAME,
                    BucketListContract.ListEntry._ID,
                    BucketListContract.ListEntry.COLUMN_NAME_CONTENT);
    // AUTOINCREMENT를 이용하여 1씩 증가

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BucketListContract.ListEntry.TABLE_NAME;

    public static bucket_list_DBHelper getInstance(Context context)
    {
        if(sInstance==null) // 인스턴스 없을 시 생성
        {
            sInstance=new bucket_list_DBHelper(context);
        }
        return sInstance;
    }

    private bucket_list_DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    } // db 생성

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
