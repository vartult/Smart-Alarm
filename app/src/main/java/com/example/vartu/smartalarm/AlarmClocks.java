package com.example.vartu.smartalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AlarmClocks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clocks);
    }
    public void setAlarm(View view){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

}
