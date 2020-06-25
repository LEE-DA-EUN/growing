package com.example.sinabro;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.sinabro.provider.bucket_list_DBHelper;
import com.example.sinabro.provider.single_line_DBHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    ImageButton single_line_btn, bucketlist_btn, emotion_trash_btn, rivew_btn;

    SharedPreferences pref;
    single_line_DBHelper single_line_dbHelper;
    bucket_list_DBHelper bucket_list_dbHelper;

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
            strResult = strResult + "0" + Integer.toString(date) + ".";
        }
        else{
            strResult = strResult + Integer.toString(date) + ".";
        }

        return strResult;
    }

    private final int ONE_DAY = 24 * 60 * 60 * 1000;
    private Calendar mCalendar;
    private TextView tvDday;
    EditText edtGoal;

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker a_view, int a_year, int a_monthOfYear, int a_dayOfMonth) {
            tvDday.setText(getDday(a_year, a_monthOfYear, a_dayOfMonth));

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("year", a_year);
            editor.putInt("month", a_monthOfYear);
            editor.putInt("day", a_dayOfMonth);
            editor.commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        single_line_dbHelper = new single_line_DBHelper(MainActivity.this);
        bucket_list_dbHelper = new bucket_list_DBHelper(MainActivity.this);

        pref = getSharedPreferences("sinabro_preference", MODE_PRIVATE);

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

        Locale.setDefault(Locale.KOREAN);
        mCalendar = new GregorianCalendar();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View a_view) {
                final int year = mCalendar.get(Calendar.YEAR);
                final int month = mCalendar.get(Calendar.MONTH);
                final int day = mCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, mDateSetListener, year, month, day);
                dialog.show();
            }
        };

        tvDday = findViewById(R.id.day);
        tvDday.setOnClickListener(clickListener);

        int year = pref.getInt("year", 0);
        int month = pref.getInt("month", 0);
        int day = pref.getInt("day", 0);

        if(year != 0) {
            tvDday.setText(getDday(year, month, day));
        }

        edtGoal = findViewById(R.id.edt_goal);
        edtGoal.setText(pref.getString("edtGoal", ""));
        edtGoal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("edtGoal", edtGoal.getText().toString());
                    editor.commit();

                    Toast.makeText(MainActivity.this, "목표가 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private String getDday(int a_year, int a_monthOfYear, int a_dayOfMonth) {
        final Calendar ddayCalendar = Calendar.getInstance();
        ddayCalendar.set(a_year, a_monthOfYear, a_dayOfMonth);
        final long dday = ddayCalendar.getTimeInMillis() / ONE_DAY;
        final long today = Calendar.getInstance().getTimeInMillis() / ONE_DAY;
        long result = dday - today;
        final String strFormat;

        if (result > 0) {
            strFormat = "- %d";
        } else if (result == 0) {
            strFormat = "+ 0";
        } else {
            result *= -1;
            strFormat = "+ %d";
        }

        final String strCount = "다직해야 " + (String.format(strFormat, result)) + " 일";
        return strCount;
    }
}

