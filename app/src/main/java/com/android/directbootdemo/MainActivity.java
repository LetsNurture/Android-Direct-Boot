package com.android.directbootdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.directbootdemo.AlarmManager.Alarm;
import com.android.directbootdemo.AlarmManager.UtilAlarm;
import com.android.directbootdemo.db.DbAlarmStorage;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.timePicker)
    TimePicker mTimePicker;

    @OnClick(R.id.buttonAddAlarm)
    void setAlarm() {

        DbAlarmStorage dbAlarmStorage = new DbAlarmStorage(this);
        Alarm alarm;

        // save alarm in database
        alarm = dbAlarmStorage.saveAlarm(Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE), mTimePicker.getHour(), mTimePicker.getMinute());

        UtilAlarm utilAlarm = new UtilAlarm(this);

        // set alarm
        utilAlarm.setAlarm(alarm);

        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
