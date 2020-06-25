package com.example.sinabro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sinabro.provider.SingleLineContract;
import com.example.sinabro.provider.single_line_DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class SingleLineRecord extends Fragment {

    public SingleLineRecord() {
        // Required empty public constructor
    }

    ArrayAdapter <String> slistViewAdapter;

    public String getDate(){ // 날짜 구하기

        String strResult = "";

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        strResult += Integer.toString(year) + ".";

        int month = calendar.get(Calendar.MONTH);
        if(1<=month && month<=9){
            strResult = strResult + "0" + Integer.toString(month+1) + ".";
        }
        else{
            strResult += Integer.toString(month+1) + ".";
        }

        int date = calendar.get(Calendar.DAY_OF_MONTH);
        if(1<=date && date<=9){
            strResult = strResult + "0" + Integer.toString(date)+ ".";
        }
        else{
            strResult = strResult + Integer.toString(date)+ ".";
        }

        return strResult;
    }

    private static class SLAdapter extends CursorAdapter { // listview로 정보를 가져오기 위해

        public SLAdapter(Context context, Cursor c) {
            super(context, c, false );
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent,false);
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

        final View single_line_view = inflater.inflate(R.layout.fragment_single_line_record, container, false);

        final ListView single_line_list = (ListView) single_line_view.findViewById(R.id.stored_list);
        Button storage_btn = (Button) single_line_view.findViewById(R.id.store);
        Button del_btn = (Button) single_line_view.findViewById((R.id.del));
        final EditText single_line_record = (EditText) single_line_view.findViewById(R.id.single_line_record);

        final ArrayList<String> user_record = new ArrayList<String>();
//
//        single_line_DBHelper dbHelper = single_line_DBHelper.getInstance(getContext());  // ?
//        Cursor cursor = dbHelper.getReadableDatabase().query(SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME,
//                null, null, null, null, null, null);
//        SLAdapter slAdapter = new SLAdapter(getContext(), cursor); // ?
//        single_line_list.setAdapter(slAdapter);  // ?

        storage_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String user_input_data = getDate() + "  :  " + single_line_record.getText().toString();
                user_record.add(user_input_data);
                ContentValues contentValues = new ContentValues(); // 넣을 내용을 담을 곳

                contentValues.put(SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_DATE,  getDate()); // 날짜 넣기
                contentValues.put(SingleLineContract.SLEntry.SINGLE_LINE_COLUMN_CONTENT, single_line_record.getText().toString()); //내용 넣기

                SQLiteDatabase db = single_line_DBHelper.getInstance(getActivity()).getWritableDatabase();
                long newRowID = db.insert(SingleLineContract.SLEntry.SINGLE_LINE_TABLE_NAME,
                        null,
                        contentValues);

                if(newRowID == -1){ //error 발생 (저장 제대로 X)
                    Toast.makeText(getActivity(), "저장에 문제가 발생하였습니다." , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "당신의 하루가 저장되었습니다", Toast.LENGTH_SHORT).show();
                }
                slistViewAdapter.notifyDataSetChanged();
            }

        }); //btn눌러서 한줄 기록 저장

        del_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                SparseBooleanArray del = single_line_list.getCheckedItemPositions();
                if (del.size() != 0){
                    for(int i = single_line_list.getCount()-1; i>=0; i--){
                        if(del.get(i)){
                            user_record.remove(i);
                        }
                    }
                    single_line_list.clearChoices();
                    slistViewAdapter.notifyDataSetChanged();
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
                user_record
        );
        single_line_list.setAdapter(slistViewAdapter);

        return single_line_view;
    }

}