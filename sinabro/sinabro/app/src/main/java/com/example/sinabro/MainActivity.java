package com.example.sinabro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sinabro.provider.single_line_DBHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {

    ImageButton single_line_btn, bucketlist_btn, emotion_trash_btn, rivew_btn;


    private ListView single_line_ListView;
    single_line_DBHelper s_db;
    ArrayAdapter sAdapter;

    public String getDate(){ // 날짜 구하기

        String strResult = "";

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        strResult += Integer.toString(year) + ".";

        int month = calendar.get(Calendar.MONTH);
        strResult += Integer.toString(month+1) + ".";

        int date = calendar.get(Calendar.DAY_OF_MONTH);
        strResult += Integer.toString(date);

        return strResult;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        single_line_btn = (ImageButton)findViewById(R.id.single_line_btn);
        bucketlist_btn = (ImageButton)findViewById(R.id.bucketlist_btn);
        emotion_trash_btn = (ImageButton)findViewById(R.id.emotion_trsh_bth);
        rivew_btn = (ImageButton)findViewById(R.id.rivew_btn);


        single_line_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                SingleLineRecord singleLineRecord = new SingleLineRecord();
                transaction.replace(R.id.frame, singleLineRecord);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        bucketlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Bucketlist bucketlist = new Bucketlist();
                transaction.replace(R.id.frame, bucketlist);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        emotion_trash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                EmotionalTrashCan emotionalTrashCan = new EmotionalTrashCan();
                transaction.replace(R.id.frame, emotionalTrashCan);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        rivew_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                ReviewNote reviewNote = new ReviewNote();
                transaction.replace(R.id.frame, reviewNote);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        String resultdate = getDate();

        TextView today = (TextView)findViewById(R.id.today_date);
        today.setText(resultdate);
    }




}
