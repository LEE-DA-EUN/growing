package com.example.sinabro.provider;

import android.provider.BaseColumns;

public final class SingleLineContract {

    private SingleLineContract(){

    }

    public static class SLEntry implements BaseColumns {
        public static final String SINGLE_LINE_TABLE_NAME = "single_line_table";  // TABLE 이름
        public static final String  _ID = "_id";
        public static final String  SINGLE_LINE_COLUMN_DATE = "date";
        public static final String  SINGLE_LINE_COLUMN_CONTENT = "content";

//        public static final String SQL_CREATE_TABLE =  "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
//                _ID + " INTEGER PRIMARY KEY," + " INTEGER PRIMARY KEY," +
//                SINGLE_LINE_COLUMN_DATE + " TEXT," +
//                SINGLE_LINE_COLUMN_CONTENT + "TEXT)";
//
//        public static final String SQL_DELETE_TABLE =
//                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
