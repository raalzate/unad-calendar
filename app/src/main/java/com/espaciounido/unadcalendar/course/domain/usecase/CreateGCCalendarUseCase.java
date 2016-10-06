package com.espaciounido.unadcalendar.course.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.course.domain.model.Course;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendar;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarDataSource;

/**
 * Created by MyMac on 11/09/16.
 */
public class CreateGCCalendarUseCase extends UseCase<CreateGCCalendarUseCase.Request, CreateGCCalendarUseCase.Response> {

    private GCCalendarDataSource repo;

    public CreateGCCalendarUseCase(GCCalendarDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        GCCalendar calendar = requestValues.getData();
        repo.saveCalendar(calendar, new GCCalendarDataSource.SaveCalendarCallback() {
            @Override
            public void onComplete() {
                getUseCaseCallback().onSuccess(new Response(getRequestValues().course));
            }

            @Override
            public void onError(Throwable error) {
                getUseCaseCallback().onError();
            }
        });
    }

    @Override
    public void cancelUseCase() {
        repo.close();
    }

    public static final class Request implements UseCase.RequestValues {
        private GCCalendar calendar;
        private Course course;

        public Request(GCCalendar calendar, Course course) {
            this.calendar = calendar;
            this.course = course;
        }

        public GCCalendar getData() {
            return calendar;
        }

        public Course getCouse() {
            return course;
        }

    }


    public static final class Response implements UseCase.ResponseValue {
        private Course course;

        public Response(Course course) {
            this.course = course;
        }

        public Course getData() {
            return course;
        }
    }
}
