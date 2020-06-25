package com.example.sinabro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sinabro.provider.SingleLineContract;
import com.example.sinabro.provider.SingleLineDTO;
import com.example.sinabro.provider.single_line_DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class SingleLineRecord extends Fragment {

    public SingleLineRecord() {
        // Required empty public constructor
    }

    ArrayAdapter<String> slistViewAdapter;
    ArrayList<SingleLineDTO> user_record = new ArrayList<>();
    ArrayList<String> simpleList = new ArrayList<>();

    single_line_DBHelper dbHelper;

    public String getDate() { // 날짜 구하기

        String strResult = "";

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        strResult += Integer.toString(year) + ".";

        int month = calendar.get(Calendar.MONTH);
        if (1 <= month && month <= 9) {
            strResult = strResult + "0" + Integer.toString(month + 1) + ".";
        } else {
            strResult += Integer.toString(month + 1) + ".";
        }

        int date = calendar.get(Calendar.DAY_OF_MONTH);
        if (1 <= date && date <= 9) {
            strResult = strResult + "0" + Integer.toString(date) + ".";
        } else {
            strResult = strResult + Integer.toString(date) + ".";
        }

        return strResult;
    }

    private static class SLAdapter extends CursorAdapter { // listview로 정보를 가져오기 위해

        public SLAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView record = view.findViewById(android.R.id.text1);    // ?
            record.setText(cursor.getString(cursor.getColumnIndexOrThrow(SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME)));
            //SINGLE_LINE_TABLE_NAME에 있는 데이터값을 가져와서 설정
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        dbHelper = new single_line_DBHelper(getActivity().getApplicationContext());

        final View single_line_view = inflater.inflate(R.layout.fragment_single_line_record, container, false);
        final ListView single_line_list = (ListView) single_line_view.findViewById(R.id.stored_list);
        Button storage_btn = (Button) single_line_view.findViewById(R.id.store);
        Button del_btn = (Button) single_line_view.findViewById((R.id.del));
        final EditText single_line_record = (EditText) single_line_view.findViewById(R.id.single_line_record);

        storage_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                SingleLineDTO singleLine = new SingleLineDTO();
                singleLine.setDate(getDate());
                singleLine.setContents(single_line_record.getText().toString());

                dbHelper.insertSingleLineData(singleLine);

                getSingleLineData();
            }
        }); //btn눌러서 한줄 기록 저장

        del_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SparseBooleanArray del = single_line_list.getCheckedItemPositions();
                if (del.size() != 0) {
                    for (int i = single_line_list.getCount() - 1; i >= 0; i--) {
                        if (del.get(i)) {
                            dbHelper.deleteSingleLineData(user_record.get(i).getId());
                        }
                    }
                    single_line_list.clearChoices();

                    getSingleLineData();
                }
            }
        });

        single_line_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String detail_record = slistViewAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), Detail_singleline.class);
                intent.putExtra("record", detail_record);
                startActivity(intent);
            }
        }); //detail activity로 이동

        slistViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_multiple_choice,
                simpleList
        );

        single_line_list.setAdapter(slistViewAdapter);

        getSingleLineData();

        return single_line_view;
    }

    public void getSingleLineData() {
        user_record.clear();
        simpleList.clear();

        user_record = dbHelper.selectSingleLineTable();

        for(int i=0; i < user_record.size(); i++) {
            simpleList.add(user_record.get(i).getDate() + "  :  " + user_record.get(i).getContents());
        }
        slistViewAdapter.notifyDataSetChanged();
    }
}