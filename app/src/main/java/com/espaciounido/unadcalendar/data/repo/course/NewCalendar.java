package com.espaciounido.unadcalendar.data.repo.course;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MyMac on 6/09/16.
 */
public class NewCalendar {
    @SerializedName("success")
    public CalendarSuccess success;
    public int code;
    public boolean error;

    public static class CalendarSuccess {
        public String id;
        public String calendar;
        public List<Event> events;

        public static class Event {
            public String id;
            public String description;
            public String htmlLink;
            public String summary;
            public String start;
            public String end;
            public String calendarId;
        }
    }
}
