package com.example.oss_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    ImageButton imageButton,imageButton2,imageButton3,imageButton4;
    ImageButton btn1;

    public static final String TAG = "insert_text";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = (ImageButton)findViewById(R.id.imageButton_1);
        imageButton2 = (ImageButton)findViewById(R.id.imageButton_2);
        imageButton3 = (ImageButton)findViewById(R.id.imageButton_3);
        imageButton4 = (ImageButton)findViewById(R.id.imageButton_4);
        btn1 = (ImageButton)findViewById(R.id.btn1);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();

            }
        });





        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.frame, fragment1);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.frame, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment3 fragment3 = new Fragment3();
                transaction.replace(R.id.frame, fragment3);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment4 fragment4 = new Fragment4();
                transaction.replace(R.id.frame, fragment4);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
