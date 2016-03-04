package com.espaciounido.unadcalendar.interfaces.views;

import android.os.Bundle;

import roboguice.event.EventManager;

/**
 * Created by MyMac on 20/02/16.
 */
public interface IMainActivity {
    enum Status {
        ERROR,
        COMPLETED
    }
    void onLoadFragment(Class fragmentClass);
    void onLoadedFragment(Status status);
    boolean navigationToUp(Class fragmentClass, Bundle bundle, String title);
    Class getHomeFragment();
    EventManager getEventManager();
}
