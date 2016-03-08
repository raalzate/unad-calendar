package com.espaciounido.unadcalendar.utils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.espaciounido.unadcalendar.views.SplashActivity;

/**
 * @author Nilanchala
 *         <p/>
 *         Broadcast reciever, starts when the device gets starts.
 *         Start your repeating alarm here.
 */
public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Utils.getTinyDB(context).putInt("alarm_service_start", -1);
            Utils.startNotification(context, SplashActivity.class);
        }
    }
}
