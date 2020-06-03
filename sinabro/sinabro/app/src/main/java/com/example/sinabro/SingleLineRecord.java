package com.example.sinabro;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View single_line_view = inflater.inflate(R.layout.fragment_single_line_record, container, false);

        ListView single_line_list = (ListView) single_line_view.findViewById(R.id.stored_list);
        Button storage_btn = (Button) single_line_view.findViewById(R.id.store);
        final EditText single_line_record = (EditText) single_line_view.findViewById(R.id.sigle_line_record);

        final ArrayList<String> user_record = new ArrayList<String>();

        storage_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String user_input_data = getDate() + "  :  " + single_line_record.getText().toString();
                user_record.add(user_input_data);
                slistViewAdapter.notifyDataSetChanged();
            }

        }); //btn눌러서 한줄 기록 저장

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
                android.R.layout.simple_expandable_list_item_1,
                user_record
        );
        single_line_list.setAdapter(slistViewAdapter);

        return single_line_view;
    }


}

