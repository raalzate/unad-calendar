package com.espaciounido.unadcalendar;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * Created by MyMac on 20/02/16.
 */
public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "N5ZqCtwcmQmnYsn4HmAICukVndZbq7n2buLizYu0",
                "tgvISH5GTsUk9Q3jBnXGG0yGqWxv6EC2TUG8X6vo");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("UNADCalendar", new SaveCallback() {

            @Override
            public void done(com.parse.ParseException arg0) {
                // TODO Auto-generated method stub
                if (arg0 == null) {
                    Log.d("UNADCalendarApplication", "successfully    subscribed to the broadcast channel.");
                } else {
                    Log.e("UNADCalendarApplication", "failed to subscribe for push", arg0);
                }
            }
        });

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
