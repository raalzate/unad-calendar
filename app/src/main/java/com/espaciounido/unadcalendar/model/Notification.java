package com.espaciounido.unadcalendar.model;

/**
 * Created by rauloko on 22/02/15.
 */
public class Notification {

    public static final String TYPE_NONE = "none";
    public static final String TYPE_QUIZ = "quiz";
    public static final String TYPE_LINK = "link";
    public static final String TYPE_EVENTROOM = "event_room_";

    public static final String STATUS_UNREAD = "unread";
    public static final String STATUS_READ = "read";

    private String id;
    private String title;
    private String body;
    private String type;
    private String contentType;
    private String date;
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        if(status == null)
            return Notification.STATUS_UNREAD;
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
