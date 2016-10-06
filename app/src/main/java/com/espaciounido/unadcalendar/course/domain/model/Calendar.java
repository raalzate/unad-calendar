package com.espaciounido.unadcalendar.course.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyMac on 6/09/16.
 */
public class Calendar {
    public final String id;
    public final String calendar;
    public final List<Event> events;

    public Calendar(String id, String calendar) {
        this.id = id;
        this.calendar = calendar;
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public static class Event {
        public final String id;
        public final String description;
        public final String htmlLink;
        public final String summary;
        public final String start;
        public final String end;
        public final String calendarId;

        public Event(String id, String description, String htmlLink, String summary, String start, String end, String calendarId) {
            this.id = id;
            this.description = description;
            this.htmlLink = htmlLink;
            this.summary = summary;
            this.start = start;
            this.end = end;
            this.calendarId = calendarId;
        }
    }
}
