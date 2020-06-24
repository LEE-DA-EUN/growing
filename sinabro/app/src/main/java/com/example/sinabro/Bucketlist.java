package com.example.sinabro;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.sinabro.provider.BucketListContract;
import com.example.sinabro.provider.bucket_list_DBHelper;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_multiple_choice;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */


public class Bucketlist extends Fragment {

    private long mListid = -1;

    public Bucketlist() {
        // Required empty public constructor
    }

    ArrayAdapter <String> bucketViewAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View bucket_view = inflater.inflate(R.layout.fragment_bucketlist, container, false);
        Button add_btn = (Button) bucket_view.findViewById(R.id.add);
        Button delete_btn = (Button) bucket_view.findViewById(R.id.delete);
        final ListView bucket_list = (ListView) bucket_view.findViewById(R.id.bucketlist);
        final EditText add_dream = (EditText) bucket_view.findViewById(R.id.new_record);

        final ArrayList<String> dream_record = new ArrayList<String>();
        bucketViewAdapter = new ArrayAdapter<String>(getActivity(), simple_list_item_multiple_choice, dream_record);
        bucket_list.setAdapter(bucketViewAdapter);

        add_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String user_dream = add_dream.getText().toString();
                dream_record.add(user_dream);
                bucketViewAdapter.notifyDataSetChanged();

                Intent intent= new Intent();
                if (intent != null){
                    mListid = intent.getLongExtra("id", -1);

                }

                ContentValues contentValues = new ContentValues(); //db에 저장
                contentValues.put(BucketListContract.ListEntry.COLUMN_NAME_CONTENT, user_dream);

                SQLiteDatabase db = bucket_list_DBHelper.getInstance(getActivity()).getWritableDatabase();

                if (mListid == -1){
                    long newRowID = db.insert(BucketListContract.ListEntry.TABLE_NAME, null, contentValues);
                    if (newRowID != -1){
                        setResult(RESULT_OK);
                    }
                }



            }
        });
        bucket_list_DBHelper dbHelper = bucket_list_DBHelper.getInstance(getActivity());
        Cursor cursor =

        delete_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                SparseBooleanArray del = bucket_list.getCheckedItemPositions();
                if (del.size() != 0){
                    for(int i = bucket_list.getCount()-1; i>=0; i--){
                        if(del.get(i)){
                            dream_record.remove(i);
                        }
                    }
                    bucket_list.clearChoices();
                    bucketViewAdapter.notifyDataSetChanged();

                    SQLiteDatabase db = bucket_list_DBHelper.getInstance(getActivity()).getWritableDatabase();
                    bucketViewAdapter.swapCursor(getListCursor());

                }


            }
        });
        //db 조회
        private Cursor getListCursor(){
            bucket_list_DBHelper dbHelper = bucket_list_DBHelper.getInstance(getActivity());
            return (View) dbHelper.getReadableDatabase().query(BucketListContract.ListEntry.TABLE_NAME, null, null, null, null, null, null);
        }

        return bucket_view;
    }





}