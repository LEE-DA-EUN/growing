package com.example.sinabro;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    ListView bucketListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dbHelper = new bucket_list_DBHelper(getActivity().getApplicationContext());

        View bucket_view = inflater.inflate(R.layout.fragment_bucketlist, container, false);
        Button add_btn = (Button) bucket_view.findViewById(R.id.add);
        Button delete_btn = (Button) bucket_view.findViewById(R.id.delete);
        bucketListView = (ListView) bucket_view.findViewById(R.id.bucketlist);
        final EditText add_dream = (EditText) bucket_view.findViewById(R.id.new_record);

        add_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                BucketListDTO bucketList = new BucketListDTO();
                bucketList.setContents(add_dream.getText().toString());
                bucketList.setChecked("false");
                dbHelper.insertBucketListData(bucketList);

                getBucketListData();

                Intent intent= new Intent();
                if (intent != null){
                    mListid = intent.getLongExtra("id", -1);
                }
            }
        });

        delete_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                SparseBooleanArray del = bucketListView.getCheckedItemPositions();
                if (del.size() != 0){
                    for(int i = bucketListView.getCount()-1; i>=0; i--){
                        if(del.get(i)){
                            dbHelper.deleteBucketListData(bucketList.get(i).getId());
                        }
                    }
                    getBucketListData();
                }
            }
        });

        bucketViewAdapter = new ArrayAdapter<String>(getActivity(), simple_list_item_multiple_choice, simpleList);
        bucketListView.setAdapter(bucketViewAdapter);

        bucketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                if(listView.isItemChecked(position)) {
                    bucketList.get(position).setChecked("true");
                } else {
                    bucketList.get(position).setChecked("false");
                }
                dbHelper.updateBucketListData(bucketList.get(position));
                getBucketListData();
            }
        });

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

        for(int i=0; i < bucketList.size(); i++) {
            if(bucketList.get(i).getChecked().equals("true"))
                bucketListView.setItemChecked(i, true);
        }
    }
}