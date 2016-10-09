package com.espaciounido.unadcalendar.utils.job;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.espaciounido.unadcalendar.utils.Utils;

import java.util.Calendar;


public class JobAlarmDaily {

    private Context context;
    private PendingIntent mAlarmSender;
    private Intent mIntent;

    public JobAlarmDaily(){

    }

    public JobAlarmDaily(Context context) {
        this.context = context;
        mIntent = new Intent("com.espaciounido.unadcalendar.utils.DAILY_NOTIFICATION");
        mAlarmSender = PendingIntent.getBroadcast(context, 22341,
                mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    }


    public void startAlarm(int hourOfDay, int minute) {

        int start = Utils.getTinyDB(context).getInt("alarm_service_start");

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (start == hourOfDay) {
            return;
        } else {
            am.cancel(mAlarmSender);
        }
        Utils.getTinyDB(context).putInt("alarm_service_start", hourOfDay);


        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) >= hourOfDay) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(String.format("%s %s:%s", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000,
                mAlarmSender);


    }

    public void createAlarmRemember(int minute, String messege) {
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minute);
        c.set(Calendar.SECOND, 0);

        mIntent = new Intent(context, JobSchedulingReceiver.class);
        mIntent.setAction("com.espaciounido.unadcalendar.utils.DAILY_NOTIFICATION");
        mIntent.putExtra(JobSchedulingReceiver.KEY_REMEMBER, messege);
        mAlarmSender = PendingIntent.getBroadcast(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), mAlarmSender);
    }
}
