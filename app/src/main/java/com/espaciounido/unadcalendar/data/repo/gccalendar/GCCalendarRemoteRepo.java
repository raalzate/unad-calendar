package com.espaciounido.unadcalendar.data.repo.gccalendar;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by MyMac on 14/09/16.
 */
public class GCCalendarRemoteRepo implements GCCalendarDataSource {

    private final DatabaseReference reference;
    private final String uid;

    public GCCalendarRemoteRepo(DatabaseReference reference, String uid) {
        this.reference = reference;
        this.uid = uid;
    }

    @Override
    public void getCalendar(String calendarId, GetCalenderCallback callback) {

    }

    @Override
    public void getCalendars(LoadCalendarCallback callback) {

    }

    @Override
    public void saveCalendar(GCCalendar calendar, SaveCalendarCallback saveCalendarCallback) {
        reference.child(uid)
                .child(extId(calendar.getCalendarId()))
                .setValue(calendar);
        saveCalendarCallback.onComplete();
    }

    @Override
    public void deleteById(String id, DeleteCalendarCallback deleteCalendarCallback) {
        reference.child(uid)
                .child(extId(id)).removeValue();
    }

    private String extId(String id) {
        return id.substring(0, id.indexOf('@'));
    }

    @Override
    public void close() {

    }
}
