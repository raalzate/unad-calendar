package com.espaciounido.unadcalendar.dashboard.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.dashboard.domain.model.Course;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendar;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyMac on 11/09/16.
 */
public class GetCourseRegisterUseCase extends UseCase<GetCourseRegisterUseCase.Request, GetCourseRegisterUseCase.Response> {

    GCCalendarDataSource repo;

    public GetCourseRegisterUseCase(GCCalendarDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {

        repo.getCalendars(new GCCalendarDataSource.LoadCalendarCallback() {
            @Override
            public void onCalendarsLoaded(List<GCCalendar> calendars) {
                List<Course> courses = new ArrayList<>();
                for (GCCalendar c : calendars) {
                    courses.add(new Course(c.getName(), c.getCalendarId()));
                }
                getUseCaseCallback().onSuccess(new Response(courses));
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    @Override
    public void cancelUseCase() {
        repo.close();
    }

    public static final class Request implements UseCase.RequestValues {
    }

    public static final class Response implements UseCase.ResponseValue {
        private final List<Course> courses;

        public Response(List<Course> courses) {
            this.courses = courses;
        }

        public List<Course> getData() {
            return courses;
        }
    }
}
