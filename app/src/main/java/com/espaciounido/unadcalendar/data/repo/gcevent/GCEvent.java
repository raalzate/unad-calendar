package com.espaciounido.unadcalendar.data.repo.gcevent;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MyMac on 25/03/16.
 */
public class GCEvent extends RealmObject {

    @PrimaryKey
    private String id;
    private String description;
    private String htmlLink;
    private String summary;
    private Date start;
    private Date end;
    private String calendarId;


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getHtmlLink() {
        return htmlLink;
    }


    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }


    public String getSummary() {
        return summary;
    }


    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getStart() {
        return start;
    }


    public void setStart(Date start) {
        this.start = start;
    }


    public Date getEnd() {
        return end;
    }


    public void setEnd(Date end) {
        this.end = end;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }
}
