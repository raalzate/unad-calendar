package com.espaciounido.unadcalendar.dashboard.domain.model;

/**
 * Created by MyMac on 4/09/16.
 */
public class ItemEvent {
    public final String id;
    public final String title;
    public final int day;
    public final int progress;
    public final String detail;
    public final boolean isHeader;

    public ItemEvent(String id, String title, String detail, int day, int progress) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.detail = detail;
        this.progress = progress;
        this.isHeader = false;
    }

    public ItemEvent(String title) {
        this.title = title;
        this.day = 0;
        this.progress = 0;
        this.id = "";
        this.detail = "";
        this.isHeader = true;
    }
}
