package com.espaciounido.unadcalendar.utils.job;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.dashboard.DashboardActivity;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEvent;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventDataSource;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventLocalRepo;
import com.espaciounido.unadcalendar.utils.DateUtils;
import com.espaciounido.unadcalendar.utils.TinyDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class JobSchedulingReceiver extends BroadcastReceiver {

    public static final String KEY_REMEMBER = "remember";
    private GCEventDataSource gcEventDataSource;
    private Context baseContext;


    public JobSchedulingReceiver() {
        gcEventDataSource = new GCEventLocalRepo();
    }

    @Override
    public void onReceive(Context context, final Intent intent) {
        System.out.println("JobSchedulingReceiver - onReceive");
        if (!intent.getAction().equals("com.espaciounido.unadcalendar.utils.DAILY_NOTIFICATION")) {
            return;
        }

        baseContext = context;

        if (intent.hasExtra(KEY_REMEMBER)) {
            sendNotification(intent.getExtras(), intent.getExtras().getString(KEY_REMEMBER));
            return;
        }

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        final ArrayList<Integer> daysAlarms = getDiaysAlarm();
        if (daysAlarms.size() == 0) return;

        gcEventDataSource.getEventsByDayOfYear(day, new GCEventDataSource.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<GCEvent> events) {
                StringBuilder message = new StringBuilder();
                boolean hasNotify = false;
                message.append("Te recordamos revisar tu agendar.\n");
                for (GCEvent e : events) {
                    int restDay = DateUtils.restHours(e.getEnd());
                    if (allowNotify(daysAlarms, restDay)) {
                        hasNotify = true;
                        message.append(e.getSummary()).append("\n");
                    }
                }

                if (hasNotify) {
                    sendNotification(intent.getExtras(), message.toString());
                }
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    private boolean allowNotify(ArrayList<Integer> integers, int day) {
        for (Integer d : integers) {
            if (d == day) {
                return true;
            }
        }
        return false;
    }


    private void sendNotification(Bundle extras, String content) {

        NotificationManager mNM;
        mNM = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent mInte = new Intent(getBaseContext(), DashboardActivity.class);
        mInte.replaceExtras(extras);
        PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0, mInte,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(getBaseContext(), JobRememberReceiver.class);
        intent.replaceExtras(extras);

        PendingIntent remember = PendingIntent.getBroadcast(getBaseContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                .setSmallIcon(android.R.drawable.ic_menu_day)
                .setTicker(getBaseContext().getText(R.string.alarm_service_label))
                .setContentTitle(getBaseContext().getText(R.string.ntf_title))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setContentText(content)
                .setContentIntent(pIntent)
                .addAction(android.R.drawable.ic_menu_recent_history,
                        getBaseContext().getString(R.string.text_remember_later), remember)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);


        mNM.notify(0, builder.build());
    }


    private ArrayList<Integer> getDiaysAlarm() {
        TinyDB tinyDB = new TinyDB(getBaseContext());
        ArrayList<Integer> nDays = new ArrayList<Integer>();
        int[] ele = new int[]{15, 12, 8, 6, 3, 1, 0};
        for (int anEle : ele) {
            if (tinyDB.getBoolean("notifications_day_" + anEle)) {
                nDays.add(anEle);
            }
        }
        return nDays;
    }


    public Context getBaseContext() {
        return baseContext;
    }
}
