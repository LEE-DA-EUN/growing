package com.example.sinabro.provider;

import android.provider.BaseColumns;

public class reviewnotecontract {
    private reviewnotecontract(){

    }

    public static class RNEntry implements BaseColumns{
        public static final String REVIEW_NOTE_TABLE_NAME = "review_note_table";
        public static final String  _ID = "_id";
        public static final String  SINGLE_LINE_COLUMN_TITLE = "title";
        public static final String  SINGLE_LINE_COLUMN_DATE = "date";
        public static final String  SINGLE_LINE_COLUMN_CONTENT = "content";
    }
}

 
