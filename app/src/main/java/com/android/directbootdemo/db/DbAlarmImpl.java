package com.android.directbootdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.directbootdemo.AlarmManager.Alarm;

import java.util.ArrayList;
import java.util.List;

/**
 * alarm implementation class for database
 */

public class DbAlarmImpl implements DBOperation {

    private SQLiteDatabase database;
    private String LOG_TAG = getClass().getSimpleName();

    public DbAlarmImpl(Context context) {
        DatabaseHelper databasehelper = DatabaseHelper.getInstance(context);
        database = databasehelper.getWritableDatabase();
    }

    @Override
    public void saveAlarm(Alarm alarm) {
        database.insert(DatabaseHelper.TABLE_ALARM, null, getContentValues(alarm));
    }

    @Override
    public void deleteAlarm(Alarm alarmToBeDeleted) {

        String rawQuery = "Delete from " + DatabaseHelper.TABLE_ALARM + " where " + DatabaseHelper.KEY_ALARM_ID +
                " = " + alarmToBeDeleted.getId();

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            Log.i("alarm", "deleted");
            cursor.close();
        }
    }

    @Override
    public List<Alarm> getAllAlarms() {

        List<Alarm> alarmList = new ArrayList<>();

        String rawQuery = "Select * from " + DatabaseHelper.TABLE_ALARM;

        Cursor cursor = database.rawQuery(rawQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Alarm alarm = new Alarm();
                alarm.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ALARM_ID)));
                alarm.setMinute(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_MINUTE)));
                alarm.setHour(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_HOUR)));
                alarm.setMonth(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_MONTH)));
                alarm.setDate(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_DATE)));
                alarmList.add(alarm);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.d(LOG_TAG, "No alarm found");
        }
        return alarmList;
    }

    @Override
    public ContentValues getContentValues(Object object) {
        Alarm alarm = (Alarm) object;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_ALARM_ID, alarm.getId());
        contentValues.put(DatabaseHelper.KEY_MINUTE, alarm.getMinute());
        contentValues.put(DatabaseHelper.KEY_HOUR, alarm.getHour());
        contentValues.put(DatabaseHelper.KEY_MONTH, alarm.getMonth());
        contentValues.put(DatabaseHelper.KEY_DATE, alarm.getDate());
        return contentValues;
    }
}
