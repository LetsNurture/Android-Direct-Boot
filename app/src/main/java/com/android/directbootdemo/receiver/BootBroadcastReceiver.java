package com.android.directbootdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.os.BuildCompat;
import android.support.v4.os.UserManagerCompat;
import android.util.Log;

import com.android.directbootdemo.AlarmManager.Alarm;
import com.android.directbootdemo.AlarmManager.UtilAlarm;
import com.android.directbootdemo.db.DbAlarmStorage;

// Broadcast receiver to listen for the action ACTION_LOCKED_BOOT_COMPLETED and ACTION_BOOT_COMPLETED
public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BootBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.i(TAG, "Action Received: " + action + ", user unlocked: " + UserManagerCompat
                .isUserUnlocked(context));

        if (BuildCompat.isAtLeastN()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(action)) {
                    setAlarms(context);
                }
            }
        } else {
            if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                setAlarms(context);
            }
        }
    }

    private void setAlarms(Context context) {
        UtilAlarm util = new UtilAlarm(context);
        DbAlarmStorage dbAlarmStorage = new DbAlarmStorage(context);
        for (Alarm alarm : dbAlarmStorage.getAlarms()) {
            util.setAlarm(alarm);
        }
    }
}
