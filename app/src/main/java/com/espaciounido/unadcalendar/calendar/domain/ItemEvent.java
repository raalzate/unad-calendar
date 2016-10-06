package com.espaciounido.unadcalendar.calendar.domain;

import java.util.Date;

/**
 * Created by MyMac on 4/09/16.
 */
public class ItemEvent {
    public final String id;
    public final String title;
    public final Date start;
    public final Date end;
    public final String detail;
    public final boolean isHeader;

    public ItemEvent(String id, String title, String detail, Date start, Date end) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.end = end;
        this.start = start;
        this.isHeader = false;
    }
}
