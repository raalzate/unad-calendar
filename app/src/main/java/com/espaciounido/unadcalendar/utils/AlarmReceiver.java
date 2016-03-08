package com.espaciounido.unadcalendar.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.model.Diarys;
import com.espaciounido.unadcalendar.model.Event;
import com.espaciounido.unadcalendar.model.ModelSerializableStore;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by rauloko on 29/01/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private ModelSerializableStore modelSerializableStore;
    private ArrayList<Diarys> diarys;
    private HashMap<Integer, String> dateNotice;
    private TinyDB tinyDB;

    public static final String KEY_REMEMBER = "remember";
    private Bundle extras;


    @Override
    public void onReceive(Context context, Intent intent) {

        if (!intent.getAction().equals("com.espaciounido.unadcalendar.utils.DAILY_NOTIFICATION")){
            return;
        }


    }

    public String getMessegeAlarm(Context context, int day) {
        return "";
    }

    private ArrayList<Integer> getDiaysAlarm() {
        ArrayList<Integer> nDays = new ArrayList<Integer>();
        int[] ele = new int[]{15, 12, 8, 6, 3, 1, 0};
        for (int i = 0; i < ele.length; i++){
            if(tinyDB.getBoolean("notifications_day_"+ele[i])){
                nDays.add(ele[i]);
            }
        }
        return nDays;
    }

    private void notificar(Context context, String content) {


    }
}
