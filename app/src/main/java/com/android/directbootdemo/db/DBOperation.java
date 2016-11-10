package com.android.directbootdemo.db;

import android.content.ContentValues;

import com.android.directbootdemo.AlarmManager.Alarm;

import java.util.List;

/**
 * Database operations
 */
public interface DBOperation {
    void saveAlarm(Alarm alarm);

    void deleteAlarm(Alarm alarm);

    List getAllAlarms();

    ContentValues getContentValues(Object object);
}