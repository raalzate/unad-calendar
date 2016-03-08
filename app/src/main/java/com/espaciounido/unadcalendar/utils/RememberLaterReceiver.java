package com.espaciounido.unadcalendar.utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.espaciounido.unadcalendar.R;

import java.util.Calendar;

/**
 * Created by rauloko on 20/03/15.
 */
public class RememberLaterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nMgr = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        new AlarmService(context).createAlarmRemember(minute + 30,"mensaje");
    }
}
