package com.espaciounido.unadcalendar.model;

import com.espaciounido.unadcalendar.utils.Utils;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rauloko on 26/01/15.
 */
public class Diarys implements Comparable<Diarys>{
    private String name;
    private String id;
    private ArrayList<Event> events;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }


    @Override
    public int compareTo(Diarys diarys) {
        return 0;
    }
}
