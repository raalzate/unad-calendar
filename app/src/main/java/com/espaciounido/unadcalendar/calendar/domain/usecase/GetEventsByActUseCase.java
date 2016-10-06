package com.espaciounido.unadcalendar.calendar.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.calendar.domain.ItemEvent;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEvent;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MyMac on 18/09/16.
 */
public class GetEventsByActUseCase extends UseCase<GetEventsByActUseCase.Request, GetEventsByActUseCase.Response> {

    private final GCEventDataSource repo;

    public GetEventsByActUseCase(GCEventDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        repo.getEventsByStartAndEnd(requestValues.start, requestValues.end, requestValues.code, new GCEventDataSource.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<GCEvent> events) {
                List<ItemEvent> list = new ArrayList<>();
                for (GCEvent e : events) {
                    list.add(new ItemEvent(e.getId(), e.getSummary(), e.getDescription(), e.getStart(), e.getEnd()));
                }

                getUseCaseCallback().onSuccess(new Response(list));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void cancelUseCase() {
        repo.close();
    }

    public static final class Request implements UseCase.RequestValues {
        private final Date start;
        private final Date end;
        private final String code;

        public Request(Date start, Date end, String code) {
            this.start = start;
            this.end = end;
            this.code = code;
        }
    }

    public static final class Response implements UseCase.ResponseValue {
        public final List<ItemEvent> list;

        public Response(List<ItemEvent> list) {
            this.list = list;
        }
    }
}
