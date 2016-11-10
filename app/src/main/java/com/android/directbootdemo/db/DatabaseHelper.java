package com.android.directbootdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * create database and tables
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Names
    public static final String TABLE_ALARM = "alarm";
    // alarm Table - column names
    public static final String KEY_ALARM_ID = "alarm_id";
    public static final String KEY_MONTH = "alarm_month";
    public static final String KEY_DATE = "alarm_date";
    public static final String KEY_HOUR = "alarm_hour";
    public static final String KEY_MINUTE = "alarm_minute";
    // Database Name
    public static final String DATABASE_NAME = "DirectBoot.db";
    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getSimpleName();
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_ALARM = "CREATE TABLE "
            + TABLE_ALARM + "(" + KEY_ALARM_ID + " INTEGER PRIMARY KEY,"
            + KEY_MONTH + " TEXT,"
            + KEY_DATE + " TEXT,"
            + KEY_HOUR + " TEXT,"
            + KEY_MINUTE + " TEXT " + ")";
    private static DatabaseHelper sInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);

        // create new tables
        onCreate(db);
    }
}