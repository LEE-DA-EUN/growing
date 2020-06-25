package com.example.sinabro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Detail_singleline extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_line_detail);

        Intent intent = getIntent();
        TextView today = (TextView) findViewById(R.id.detailed);
        TextView mtext = (TextView) findViewById(R.id.detail_main_text);
        today.setText(intent.getStringExtra("record").substring(15));
        String dayed = intent.getStringExtra("record").substring(0,10);
        mtext.setText("당신의 " + dayed+ "일의 기록입니다.");
    }
}
