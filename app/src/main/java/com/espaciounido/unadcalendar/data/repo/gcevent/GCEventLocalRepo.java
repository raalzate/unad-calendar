package com.espaciounido.unadcalendar.data.repo.gcevent;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by MyMac on 10/09/16.
 */
public class GCEventLocalRepo implements GCEventDataSource {

    @Override
    public void close() {
        Realm.getDefaultInstance().close();
    }

    @Override
    public void getEvents(LoadEventsCallback callback) {
        RealmResults<GCEvent> elements = Realm.getDefaultInstance()
                .where(GCEvent.class)
                .findAll();

        callback.onEventsLoaded(elements);
    }

    @Override
    public void getEventById(String id, LoadEventCallback callback) {
       GCEvent event = Realm.getDefaultInstance()
                .where(GCEvent.class)
                .equalTo("id", id)
                .findFirst();

        if(event != null) {
            callback.onEventLoaded(event);
        } else {
            callback.onEmpty();
        }

    }

    @Override
    public void getEventsByStartAndEnd(Date start, Date end, String code, LoadEventsCallback callback) {
        RealmQuery<GCEvent> realm = Realm.getDefaultInstance()
                .where(GCEvent.class)
                .equalTo("calendarId", code);

        if (start == null) {
            realm.lessThanOrEqualTo("end", end);
        }

        if (end == null) {
            realm.greaterThanOrEqualTo("start", start);
        }

        if (start != null && end != null) {
            realm.greaterThanOrEqualTo("end", end);
            realm.lessThanOrEqualTo("start", start);
        }

        List<GCEvent> events = realm.findAllSorted("end");


        if (events.size() > 0) {
            callback.onEventsLoaded(events);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void getEventsByDayOfYear(int dayOfYear, LoadEventsCallback callback) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        List<GCEvent> events = Realm.getDefaultInstance()
                .where(GCEvent.class)
                .lessThanOrEqualTo("start", calendar.getTime())
                .greaterThanOrEqualTo("end", calendar.getTime())
                .findAllSorted("end");
        if (events.size() > 0) {
            callback.onEventsLoaded(events);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveEvent(final GCEvent event, final SaveEventCallback saveEventCallback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(event);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                saveEventCallback.onComplete();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                saveEventCallback.onError(error);
            }
        });
    }

    @Override
    public void deleteAllByCalendarId(String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<GCEvent> elements = Realm.getDefaultInstance()
                .where(GCEvent.class)
                .equalTo("calendarId", id)
                .findAll();

        elements.deleteAllFromRealm();
        realm.commitTransaction();
    }


}
