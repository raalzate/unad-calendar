package com.espaciounido.unadcalendar.calendar.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.calendar.domain.EventCalendar;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEvent;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventDataSource;

/**
 * Created by MyMac on 25/09/16.
 */
public class GetEventUseCase extends UseCase<GetEventUseCase.Request, GetEventUseCase.Response> {

    GCEventDataSource repo;

    public GetEventUseCase(GCEventDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        repo.getEventById(requestValues.id, new GCEventDataSource.LoadEventCallback() {
            @Override
            public void onEventLoaded(GCEvent e) {
                EventCalendar eventCalendar = new EventCalendar();

                eventCalendar.setSummary(e.getSummary());
                eventCalendar.setDescription(e.getDescription());
                eventCalendar.setEnd(e.getEnd());
                eventCalendar.setStart(e.getStart());
                eventCalendar.setHtmlLink(e.getHtmlLink());
                eventCalendar.setCalendarId(e.getCalendarId());

                getUseCaseCallback().onSuccess(new Response(eventCalendar));
            }

            @Override
            public void onEmpty() {
                getUseCaseCallback().onError();
            }
        });
    }

    @Override
    public void cancelUseCase() {
        repo.close();
    }

    public static final class Request implements UseCase.RequestValues {
        public final String id;

        public Request(String id) {
            this.id = id;
        }
    }

    public static final class Response implements UseCase.ResponseValue {
        public final EventCalendar eventCalendar;

        public Response(EventCalendar eventCalendar) {
            this.eventCalendar = eventCalendar;
        }
    }
}
