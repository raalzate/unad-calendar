package com.espaciounido.unadcalendar.listeners;

import com.espaciounido.unadcalendar.listeners.events.OnLoadedFragmentEvent;
import com.espaciounido.unadcalendar.interfaces.views.IMainActivity;

import roboguice.event.Observes;

/**
 * Created by MyMac on 20/02/16.
 */
public class MainActivityListener {
    // handle the buy event
    protected void handleLoadedFragment( @Observes OnLoadedFragmentEvent event ) {
        event.mainActivity.onLoadedFragment(event.getStatus());
        if(event.getStatus().equals(IMainActivity.Status.COMPLETED)) {
            event.mainActivity.navigationToUp(event.fragmentClass, event.bundle, event.title);
        }
    }
}
