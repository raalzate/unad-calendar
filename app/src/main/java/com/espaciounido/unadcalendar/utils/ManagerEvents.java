package com.espaciounido.unadcalendar.utils;

/**
 * Created by MyMac on 20/09/16.
 */
public class ManagerEvents {
    private static ManagerEvents ourInstance = new ManagerEvents();

    private ManagerEvents() {
    }

    public static ManagerEvents getInstance() {
        return ourInstance;
    }

    public void postDelay(Object object) {

    }
}
