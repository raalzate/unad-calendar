package com.espaciounido.unadcalendar.utils.job;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by rauloko on 20/03/15.
 */
public class JobRememberReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new JobAlarmReceiver().setAlarmRemember(context);
    }
}
