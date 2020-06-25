package com.example.sinabro;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewNote extends Fragment {

    public ReviewNote() {
        // Required empty public constructor
    }

    ArrayAdapter <String> rlistViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_note, container, false);

        ListView review_list = (ListView) view.findViewById(R.id.review_list);
        final ArrayList<String> review_record = new ArrayList<String>();

        Button r_btn = (Button) view.findViewById(R.id.review_add_btn);
        r_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                final Dialog review_input = new Dialog(getActivity());
                review_input.setContentView(R.layout.review_input);

                Button store = (Button) review_input.findViewById(R.id.review_store);
                Button cancel = (Button) review_input.findViewById(R.id.review_cancel);

                final EditText review_subject = (EditText) review_input.findViewById(R.id.review_Dsubject);
                final EditText review_day = (EditText) review_input.findViewById(R.id.review_Dday);
                final EditText review_content = (EditText) review_input.findViewById(R.id.review_Dreview);

                store.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String review = "제목 : " + review_subject.getText().toString() +
                                "\n날짜 : "  + review_day.getText().toString() +
                                "\n내용 : " + review_content.getText().toString();

                        review_record.add(review);
                        rlistViewAdapter.notifyDataSetChanged();
                        review_input.dismiss();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        review_input.dismiss();

                    }
                });

                review_input.show();

            }
        });

        rlistViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                review_record
        );
        review_list.setAdapter(rlistViewAdapter);


        return view;
    }
}