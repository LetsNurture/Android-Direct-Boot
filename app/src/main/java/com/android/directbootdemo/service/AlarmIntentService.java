package com.android.directbootdemo.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.android.directbootdemo.AlarmManager.Alarm;
import com.android.directbootdemo.R;
import com.android.directbootdemo.db.DbAlarmStorage;

/**
 * IntentService to notify about an alarm.
 */
public class AlarmIntentService extends IntentService {

    public static final String ID = "id";
    public static final String MINUTE = "minute";
    public static final String HOUR = "hour";
    public static final String DATE = "date";
    public static final String MONTH = "month";

    public AlarmIntentService() {
        super(AlarmIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context = getApplicationContext();

        Alarm alarm = new Alarm();
        Bundle bundle = intent.getExtras();
        alarm.setId(bundle.getInt(ID));
        alarm.setMinute(bundle.getInt(MINUTE));
        alarm.setHour(bundle.getInt(HOUR));
        alarm.setDate(bundle.getInt(DATE));
        alarm.setMonth(bundle.getInt(MONTH));

        NotificationManager notificationManager;
        notificationManager = getSystemService(NotificationManager.class);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentTitle(context.getString(R.string.alarm));

        notificationManager.notify(alarm.getId(), builder.build());

        // delete the alarm once it goes off
        DbAlarmStorage dbAlarmStorage = new DbAlarmStorage(context);
        dbAlarmStorage.deleteAlarm(alarm);
    }
}
