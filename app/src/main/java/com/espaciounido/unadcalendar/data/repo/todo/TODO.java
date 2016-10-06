package com.espaciounido.unadcalendar.data.repo.todo;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MyMac on 2/10/16.
 */
public class Todo extends RealmObject {

    @PrimaryKey
    private String id;
    private String message;
    private boolean status;
    private String eventId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
