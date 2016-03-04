package com.espaciounido.unadcalendar.interfaces.views;

import com.espaciounido.unadcalendar.interfaces.events.OnListenerEvent;

/**
 * Created by raalzate on 20/01/2016.
 */
public interface IHomeCourseFragment {
    void setOnListenerEvent(OnListenerEvent onListenerEvent);
    OnListenerEvent getOnListenerEvent();
}
