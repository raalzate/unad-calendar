package com.espaciounido.unadcalendar.course.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.course.domain.model.Course;
import com.espaciounido.unadcalendar.data.repo.course.CourseDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyMac on 10/09/16.
 */
public class GetCourseUseCase extends UseCase<GetCourseUseCase.Request, GetCourseUseCase.Response> {

    private CourseDataSource repo;

    public GetCourseUseCase(CourseDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        repo.getCourses(new CourseDataSource.LoadCoursesCallback() {
            @Override
            public void onCalendarsLoaded(List<com.espaciounido.unadcalendar.data.repo.course.Course.Curso> cursos) {
                Response res = new Response(cursos);
                getUseCaseCallback().onSuccess(res);
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
        private final ArrayList<Course> courses;

        public Response(List<com.espaciounido.unadcalendar.data.repo.course.Course.Curso> cursos) {
            courses = new ArrayList<>();
            for (com.espaciounido.unadcalendar.data.repo.course.Course.Curso c : cursos) {
                courses.add(new Course(c.name, c.code, c.credits, c.period));
            }
        }

        public List<Course> getCourses() {
            return courses;
        }
    }
}
