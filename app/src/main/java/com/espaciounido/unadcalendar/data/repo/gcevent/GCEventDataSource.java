package com.espaciounido.unadcalendar.data.repo.gcevent;


import java.util.Date;
import java.util.List;

/**
 * Created by MyMac on 10/09/16.
 */
public interface GCEventDataSource {
    void close();

    void getEvents(LoadEventsCallback callback);

    void getEventById(String id, LoadEventCallback callback);

    void getEventsByStartAndEnd(Date start, Date end, String code, LoadEventsCallback callback);

    void getEventsByDayOfYear(int dayOfYear, LoadEventsCallback callback);

    void saveEvent(GCEvent calendar, SaveEventCallback saveEventCallback);

    void deleteAllByCalendarId(String id);

    interface LoadEventsCallback {
        void onEventsLoaded(List<GCEvent> events);

        void onDataNotAvailable();
    }

    interface SaveEventCallback {
        void onComplete();

        void onError(Throwable error);
    }

    interface LoadEventCallback {
        void onEventLoaded(GCEvent event);

        void onEmpty();
    }
}
