package com.zfj.android.newcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NewCalendar.NewCalenderListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewCalendar calendar = (NewCalendar) findViewById(R.id.newCalendar);
        calendar.listener = this;
    }

    @Override
    public void onLongClickItem(Date day) {
        DateFormat df = new SimpleDateFormat().getInstance();
        Toast.makeText(this, df.format(day.getTime()),Toast.LENGTH_SHORT).show();
    }
}
