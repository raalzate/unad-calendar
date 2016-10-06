package com.espaciounido.unadcalendar.settings;

/**
 * Created by MyMac on 6/09/16.
 */
public interface Preference {
    String getName();

    String getEmail();

    String getPeriod();

    String getCarrera();

    String getCEAD();

    void saveRemote();

    int getNotiActiveFrequency();

    int getReplyFrequency();

    String tokenNotification();
}
