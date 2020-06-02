package com.example.sinabro;

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
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_multiple_choice;


/**
 * A simple {@link Fragment} subclass.
 */
public class Bucketlist extends Fragment {

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

            }
        });

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
                }
            }
        });



        return bucket_view;
    }
}
