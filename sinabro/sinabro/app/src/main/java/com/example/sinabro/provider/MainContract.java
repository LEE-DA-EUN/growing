package com.example.sinabro.provider;

import android.provider.BaseColumns;

public class MainContract {

    public static class MainEntry implements BaseColumns{
        public static final String MAIN_TABLE_NAME = "main_table";  // TABLE 이름
        public static final String  _ID = "_id";
        public static final String  SINGLE_LINE_COLUMN_DDAY = "Dday";
        public static final String  SINGLE_LINE_COLUMN_GOAL = "goal";
    }
}
