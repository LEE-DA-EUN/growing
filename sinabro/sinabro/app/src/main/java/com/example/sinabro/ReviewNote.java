package com.example.sinabro;

import android.app.Dialog;
import android.os.Bundle;
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

import com.example.sinabro.provider.ReviewNoteDTO;
import com.example.sinabro.provider.review_note_DBHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewNote extends Fragment {

    public ReviewNote() {
        // Required empty public constructor
    }

    ArrayAdapter<String> rlistViewAdapter;
    ArrayList<ReviewNoteDTO> reviewNoteList = new ArrayList<>();
    ArrayList<String> simpleList = new ArrayList<>();

    review_note_DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dbHelper = new review_note_DBHelper(getActivity().getApplicationContext());

        View view = inflater.inflate(R.layout.fragment_review_note, container, false);
        ListView review_list = (ListView) view.findViewById(R.id.review_list);
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
                                "\n날짜 : " + review_day.getText().toString() +
                                "\n내용 : " + review_content.getText().toString();

                        ReviewNoteDTO reviewNoteDTO = new ReviewNoteDTO();
                        reviewNoteDTO.setTitle(review_subject.getText().toString());
                        reviewNoteDTO.setDate(review_day.getText().toString());
                        reviewNoteDTO.setContents(review_content.getText().toString());

                        dbHelper.insertReviewNoteData(reviewNoteDTO);
                        getReviewNoteListData();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
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
                simpleList
        );
        review_list.setAdapter(rlistViewAdapter);

        getReviewNoteListData();

        return view;
    }

    public void getReviewNoteListData() {
        reviewNoteList.clear();
        simpleList.clear();

        reviewNoteList = dbHelper.selectReviewNoteTable();

        for (int i = 0; i < reviewNoteList.size(); i++) {
            String review = "제목 : " + reviewNoteList.get(i).getTitle() +
                    "\n날짜 : " + reviewNoteList.get(i).getDate() +
                    "\n내용 : " + reviewNoteList.get(i).getContents();
            simpleList.add(review);
        }

        rlistViewAdapter.notifyDataSetChanged();
    }
}