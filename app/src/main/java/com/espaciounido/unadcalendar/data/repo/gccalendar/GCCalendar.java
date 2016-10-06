package com.espaciounido.unadcalendar.data.repo.gccalendar;


import com.espaciounido.unadcalendar.data.repo.gcevent.GCEvent;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MyMac on 6/09/16.
 */
public class GCCalendar extends RealmObject {

    @PrimaryKey
    private String calendarId;
    private String name;
    private RealmList<GCEvent> events;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<GCEvent> getEvents() {
        return events;
    }

    public void setEvents(RealmList<GCEvent> events) {
        this.events = events;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

}
