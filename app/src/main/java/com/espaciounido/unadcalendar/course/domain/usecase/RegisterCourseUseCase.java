package com.espaciounido.unadcalendar.course.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.course.domain.model.CreateCalendar;
import com.espaciounido.unadcalendar.data.repo.course.CourseDataSource;
import com.espaciounido.unadcalendar.data.repo.course.NewCalendar;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendar;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarDataSource;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEvent;
import com.espaciounido.unadcalendar.utils.Utils;
import com.google.firebase.messaging.FirebaseMessaging;

import io.realm.RealmList;

/**
 * Created by MyMac on 10/09/16.
 */
public class RegisterCourseUseCase extends UseCase<RegisterCourseUseCase.Request, RegisterCourseUseCase.Response> {

    private final CourseDataSource csRepo;
    private final GCCalendarDataSource gcRepo;

    public RegisterCourseUseCase(CourseDataSource csRepo, GCCalendarDataSource gcRepo) {
        this.csRepo = csRepo;
        this.gcRepo = gcRepo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {

        csRepo.createNewCalendar(
                requestValues.createCalendar.courseCode,
                requestValues.createCalendar.email,
                requestValues.createCalendar.period,
                new CourseDataSource.SaveCourseCallback() {
                    @Override
                    public void onComplete(NewCalendar newCalendar) {
                        final Response result = new Response(newCalendar);

                        gcRepo.saveCalendar(result.getData(),
                                new GCCalendarDataSource.SaveCalendarCallback() {
                                    @Override
                                    public void onComplete() {
                                        FirebaseMessaging.getInstance()
                                                .subscribeToTopic(extId(result.getData()
                                                        .getCalendarId()));
                                    }

                                    @Override
                                    public void onError(Throwable error) {
                                    }
                                });

                        getUseCaseCallback().onSuccess(result);


                    }

                    @Override
                    public void onError(Throwable error) {
                        getUseCaseCallback().onError();
                    }
                }
        );


    }

    private String extId(String id) {
        return id.substring(0, id.indexOf('@'));
    }


    @Override
    public void cancelUseCase() {
        csRepo.close();
    }

    public static final class Request implements UseCase.RequestValues {
        private final CreateCalendar createCalendar;

        public Request(CreateCalendar createCalendar) {
            this.createCalendar = createCalendar;
        }

        public CreateCalendar getData() {
            return createCalendar;
        }
    }

    public static final class Response implements UseCase.ResponseValue {
        private final GCCalendar calendar;

        public Response(NewCalendar newCalendar) {
            NewCalendar.CalendarSuccess cal = newCalendar.success;
            calendar = new GCCalendar();
            RealmList<GCEvent> events = new RealmList<>();

            calendar.setCalendarId(cal.id);
            calendar.setName(cal.calendar);

            for (NewCalendar.CalendarSuccess.Event e : cal.events) {
                GCEvent event = new GCEvent();
                event.setId(e.id);
                event.setCalendarId(cal.id);
                event.setSummary(e.summary);
                event.setDescription(e.description);
                event.setHtmlLink(e.htmlLink);
                event.setStart(Utils.strToCalendar(e.start).getTime());
                event.setEnd(Utils.strToCalendar(e.end).getTime());
                events.add(event);
            }

            calendar.setEvents(events);
        }

        public GCCalendar getData() {
            return calendar;
        }
    }
}
