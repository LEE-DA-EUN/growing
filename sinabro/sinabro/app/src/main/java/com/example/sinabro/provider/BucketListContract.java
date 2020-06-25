package com.example.sinabro.provider;

import android.provider.BaseColumns;

public class BucketListContract {
    private BucketListContract(){}
    public static class ListEntry implements BaseColumns{
        public static final String TABLE_NAME = "list";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_CHECKED = "checked";
    }
}