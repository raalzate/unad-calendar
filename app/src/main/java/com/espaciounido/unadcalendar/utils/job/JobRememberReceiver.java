package com.espaciounido.unadcalendar.utils.job;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


/**
 * Created by rauloko on 20/03/15.
 */
public class JobRememberReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nMgr = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        new JobAlarmDaily(context).createAlarmRemember(minute + 30, "Recordar mas tarde");

    }
}
