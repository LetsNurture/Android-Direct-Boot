package com.android.directbootdemo.db;

import android.content.Context;
import android.support.v4.os.BuildCompat;
import android.util.Log;

import com.android.directbootdemo.AlarmManager.Alarm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Save alarms and specify the storage
 */

public class DbAlarmStorage {

    private static final String TAG = DbAlarmStorage.class.getSimpleName();
    private DbAlarmImpl dbAlarmImpl;

    public DbAlarmStorage(Context context) {

        // context which will hold the appropriate storage context
        Context storageContext = null;

        // if device is running Android N or newer version, move shared preferences to Device Encrypted Storage,
        // else keep them in Credential Encrypted Storage

        if (BuildCompat.isAtLeastN()) {
            final Context deviceContext;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                deviceContext = context.createDeviceProtectedStorageContext();
                if (!deviceContext.moveDatabaseFrom(context,
                        DatabaseHelper.DATABASE_NAME)) {
                    Log.e(TAG, "Failed to migrate database");
                }
                storageContext = deviceContext;
            }
        } else {
            storageContext = context;
        }
        if (storageContext != null) {
            dbAlarmImpl = new DbAlarmImpl(storageContext);
        }
    }

    // save alarm
    public Alarm saveAlarm(int month, int date, int hour, int minute) {
        Alarm alarm = new Alarm();
        alarm.setId(new Random().nextInt());
        alarm.setMinute(minute);
        alarm.setHour(hour);
        alarm.setMonth(month);
        alarm.setDate(date);

        dbAlarmImpl.saveAlarm(alarm);
        return alarm;
    }

    // get the list of alarms
    public Set<Alarm> getAlarms() {
        Set<Alarm> alarms = new HashSet<>();
        for (Alarm alarm : dbAlarmImpl.getAllAlarms()) {
            alarms.add(alarm);
        }
        return alarms;
    }

    public void deleteAlarm(Alarm alarm) {
        dbAlarmImpl.deleteAlarm(alarm);
    }
}
