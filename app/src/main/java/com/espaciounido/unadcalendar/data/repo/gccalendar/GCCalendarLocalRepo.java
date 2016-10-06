package com.espaciounido.unadcalendar.data.repo.gccalendar;


import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventLocalRepo;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by MyMac on 10/09/16.
 */
public class GCCalendarLocalRepo implements GCCalendarDataSource {

    GCEventLocalRepo eventLocalRepo = new GCEventLocalRepo();


    @Override
    public void getCalendar(String calendarId, GetCalenderCallback callback) {
        GCCalendar calendar = Realm.getDefaultInstance().where(GCCalendar.class)
                .equalTo("calendarId", calendarId)
                .findFirst();

        callback.onCalendar(calendar);
    }

    @Override
    public void getCalendars(final LoadCalendarCallback callback) {
        RealmResults<GCCalendar> calendars = Realm.getDefaultInstance()
                .where(GCCalendar.class)
                .findAll();

        callback.onCalendarsLoaded(calendars);
    }

    @Override
    public void saveCalendar(final GCCalendar calendar, final SaveCalendarCallback calendarCallback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(calendar);
        realm.commitTransaction();
        calendarCallback.onComplete();
    }

    @Override
    public void deleteById(final String calendarId, final DeleteCalendarCallback deleteCalendarCallback) {
        Realm realm = Realm.getDefaultInstance();

        GCCalendar result =
                Realm.getDefaultInstance().where(GCCalendar.class)
                        .equalTo("calendarId", calendarId).findFirst();

        realm.beginTransaction();
        result.deleteFromRealm();
        realm.commitTransaction();

        eventLocalRepo.deleteAllByCalendarId(calendarId);
        deleteCalendarCallback.onComplete();
    }

    @Override
    public void close() {
        Realm.getDefaultInstance().close();
    }
}
