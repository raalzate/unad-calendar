package com.espaciounido.unadcalendar.dashboard.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.dashboard.domain.model.ItemEvent;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendar;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarDataSource;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEvent;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventDataSource;
import com.espaciounido.unadcalendar.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by MyMac on 11/09/16.
 */
public class GetEventsUseCase extends UseCase<GetEventsUseCase.Request, GetEventsUseCase.Response> {
    private static final int LIMIT_ITEM_EVENTS = 2;
    private final GCEventDataSource eventRepo;
    private final GCCalendarDataSource calendarRepo;

    public GetEventsUseCase(GCEventDataSource eventRepo, GCCalendarDataSource calendarRepo) {
        this.eventRepo = eventRepo;
        this.calendarRepo = calendarRepo;
    }

    @Override
    protected void executeUseCase(final Request requestValues) {
        eventRepo.getEventsByDayOfYear(requestValues.getData(), new GCEventDataSource.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<GCEvent> events) {
                sortListResult(events, getRequestValues().dayOfYear, new ListResultCallback() {
                    @Override
                    public void onListResult(List<ItemEvent> list) {
                        getUseCaseCallback().onSuccess(new Response(list));
                    }

                    @Override
                    public void onEmpty() {
                        getUseCaseCallback().onError();
                    }
                });

            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    @Override
    public void cancelUseCase() {
        calendarRepo.close();
        eventRepo.close();
    }

    private void sortListResult(List<GCEvent> data, final int dayOfYear, final ListResultCallback callback) {
        final LinkedHashMap<String, List<GCEvent>> hash = new LinkedHashMap<>();
        final List<ItemEvent> list = new ArrayList<>();
        final Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        for (GCEvent event : data) {
            if (!hash.containsKey(event.getCalendarId())) {
                hash.put(event.getCalendarId(), new ArrayList<GCEvent>());
            }
            hash.get(event.getCalendarId()).add(event);
        }

        for (String calId : hash.keySet()) {
            calendarRepo.getCalendar(calId, new GCCalendarDataSource.GetCalenderCallback() {
                @Override
                public void onCalendar(GCCalendar calendar) {

                    list.add(new ItemEvent(calendar.getName()));

                    for (GCEvent e : hash.get(calendar.getCalendarId())
                            .subList(0, Math.min(LIMIT_ITEM_EVENTS,
                                    hash.get(calendar.getCalendarId()).size()))) {

                        int dayRest = DateUtils.restDay(dateCalendar.getTime(), e.getEnd());
                        int progress = getProgress(e, dayOfYear);
                        ItemEvent item = new ItemEvent(e.getId(), e.getSummary(), e.getDescription(), dayRest, progress);
                        item.setDateEnd(e.getEnd());
                        list.add(item);
                    }
                    callback.onListResult(list);
                }

                @Override
                public void onEmpty() {
                    callback.onEmpty();
                }
            });

        }

    }

    public int getProgress(GCEvent event, int dayOfYear) {
        final Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        int daysResta = DateUtils.restHours(dateCalendar.getTime(), event.getEnd());
        int daysTotal = DateUtils.restHours(event.getStart(), event.getEnd());
        return ((daysTotal - daysResta) * 100) / daysTotal;
    }

    public interface ListResultCallback {
        void onListResult(List<ItemEvent> list);

        void onEmpty();
    }

    public static final class Request implements UseCase.RequestValues {
        private final int dayOfYear;

        public Request(int dayOfYear) {
            this.dayOfYear = dayOfYear;
        }

        public int getData() {
            return dayOfYear;
        }

    }

    public static final class Response implements UseCase.ResponseValue {

        private final List<ItemEvent> events;

        public Response(List<ItemEvent> events) {
            this.events = events;
        }

        public List<ItemEvent> getData() {
            return events;
        }

    }
}
