package com.android.directbootdemo.AlarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.directbootdemo.service.AlarmIntentService;

import java.util.Calendar;


// Utils to perform operations on Alarm
public class UtilAlarm {

    private static final String TAG = "UtilAlarm";
    private final Context mContext;
    private final AlarmManager mAlarmManager;

    public UtilAlarm(Context context) {
        mContext = context;
        mAlarmManager = mContext.getSystemService(AlarmManager.class);
    }

    // schedule an alarm
    public void setAlarm(Alarm alarm) {
        Intent intent = new Intent(mContext, AlarmIntentService.class);

        intent.putExtra(AlarmIntentService.ID, alarm.getId());
        intent.putExtra(AlarmIntentService.MINUTE, alarm.getMinute());
        intent.putExtra(AlarmIntentService.HOUR, alarm.getHour());
        intent.putExtra(AlarmIntentService.DATE, alarm.getDate());
        intent.putExtra(AlarmIntentService.MONTH, alarm.getMonth());

        PendingIntent pendingIntent = PendingIntent
                .getService(mContext, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.MONTH, alarm.getMonth());
        alarmTime.set(Calendar.DATE, alarm.getDate());
        alarmTime.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        alarmTime.set(Calendar.MINUTE, alarm.getMinute());

        AlarmManager.AlarmClockInfo alarmClockInfo;
        alarmClockInfo = new AlarmManager.AlarmClockInfo(
                alarmTime.getTimeInMillis(),
                pendingIntent);
        mAlarmManager.setAlarmClock(alarmClockInfo, pendingIntent);

        Log.i(TAG, "Alarm set");
    }
}
