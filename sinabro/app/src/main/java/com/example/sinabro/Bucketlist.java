package com.example.sinabro;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sinabro.provider.BucketListDTO;
import com.example.sinabro.provider.bucket_list_DBHelper;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_multiple_choice;


/**
 * A simple {@link Fragment} subclass.
 */


public class Bucketlist extends Fragment {

    private long mListid = -1;

    public Bucketlist() {
        // Required empty public constructor
    }

    ArrayAdapter <String> bucketViewAdapter;
    ArrayList<BucketListDTO> bucketList = new ArrayList<>();
    ArrayList<String> simpleList = new ArrayList<>();

    bucket_list_DBHelper dbHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dbHelper = new bucket_list_DBHelper(getActivity().getApplicationContext());

        View bucket_view = inflater.inflate(R.layout.fragment_bucketlist, container, false);
        Button add_btn = (Button) bucket_view.findViewById(R.id.add);
        Button delete_btn = (Button) bucket_view.findViewById(R.id.delete);
        final ListView bucket_list = (ListView) bucket_view.findViewById(R.id.bucketlist);
        final EditText add_dream = (EditText) bucket_view.findViewById(R.id.new_record);

        add_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String user_dream = add_dream.getText().toString();
                dbHelper.insertBucketListData(user_dream);

                getBucketListData();

                Intent intent= new Intent();
                if (intent != null){
                    mListid = intent.getLongExtra("id", -1);
                }
            }
        });

        delete_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                SparseBooleanArray del = bucket_list.getCheckedItemPositions();
                if (del.size() != 0){
                    for(int i = bucket_list.getCount()-1; i>=0; i--){
                        if(del.get(i)){
                            dbHelper.deleteBucketListData(bucketList.get(i).getId());
                        }
                    }
                    getBucketListData();
                }
            }
        });

        bucketViewAdapter = new ArrayAdapter<String>(getActivity(), simple_list_item_multiple_choice, simpleList);
        bucket_list.setAdapter(bucketViewAdapter);

        getBucketListData();

        return bucket_view;
    }

    public void getBucketListData() {
        bucketList.clear();
        simpleList.clear();

        bucketList = dbHelper.selectBucketListTable();

        for(int i=0; i < bucketList.size(); i++) {
            simpleList.add(bucketList.get(i).getContents());
        }

        bucketViewAdapter.notifyDataSetChanged();
    }
}