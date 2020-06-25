package com.example.sinabro.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class bucket_list_DBHelper extends SQLiteOpenHelper {
    private static bucket_list_DBHelper sInstance;
    private static final int DB_VERSION=1;
    private static final String  DB_NAME="Memo.db";

    // 테이블을 생성
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + BucketListContract.ListEntry.TABLE_NAME + " (" +
            BucketListContract.ListEntry._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            BucketListContract.ListEntry.COLUMN_NAME_CONTENT + " TEXT NOT NULL, " +
            BucketListContract.ListEntry.COLUMN_NAME_CHECKED + " TEXT NOT NULL)";

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

    public bucket_list_DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public ArrayList<BucketListDTO> selectBucketListTable() {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM "+ BucketListContract.ListEntry.TABLE_NAME;

        ArrayList<BucketListDTO> bucketList = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            BucketListDTO bucket = new BucketListDTO();
            bucket.setId(cursor.getInt(0));
            bucket.setContents(cursor.getString(1));
            bucket.setChecked(cursor.getString(2));

            bucketList.add(bucket);
        }
        return bucketList;
    }

    public void insertBucketListData(BucketListDTO bucketList) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(BucketListContract.ListEntry.COLUMN_NAME_CONTENT, bucketList.getContents());
        values.put(BucketListContract.ListEntry.COLUMN_NAME_CHECKED, bucketList.getChecked());
        db.insert(BucketListContract.ListEntry.TABLE_NAME, null, values);
    }

    public void updateBucketListData(BucketListDTO bucketList) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(BucketListContract.ListEntry.COLUMN_NAME_CONTENT, bucketList.getContents());
        values.put(BucketListContract.ListEntry.COLUMN_NAME_CHECKED, bucketList.getChecked());
        db.update(BucketListContract.ListEntry.TABLE_NAME, values, BucketListContract.ListEntry._ID +"=" + bucketList.getId() , null);
    }

    public void deleteBucketListData(int _id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(BucketListContract.ListEntry.TABLE_NAME, "_id=" + _id, null);
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