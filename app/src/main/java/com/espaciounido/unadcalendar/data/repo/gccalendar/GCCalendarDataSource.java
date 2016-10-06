package com.espaciounido.unadcalendar.data.repo.gccalendar;

import java.util.List;

/**
 * Created by MyMac on 10/09/16.
 */
public interface GCCalendarDataSource {
    void getCalendar(String calendarId, GetCalenderCallback callback);

    void getCalendars(LoadCalendarCallback callback);

    void saveCalendar(GCCalendar calendar, SaveCalendarCallback saveCalendarCallback);

    void deleteById(String id, DeleteCalendarCallback deleteCalendarCallback);

    void close();

    interface LoadCalendarCallback {
        void onCalendarsLoaded(List<GCCalendar> calendars);

        void onDataNotAvailable();
    }

    interface SaveCalendarCallback {
        void onComplete();

        void onError(Throwable error);
    }

    interface DeleteCalendarCallback {
        void onComplete();

        void onError(Throwable error);
    }

    interface GetCalenderCallback {
        void onCalendar(GCCalendar calendar);

        void onEmpty();
    }
}
