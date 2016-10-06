package com.espaciounido.unadcalendar.data.repo.course;

import com.espaciounido.unadcalendar.data.rest.UnadCalendarApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MyMac on 10/09/16.
 */
public class CourseRemoteRepo implements CourseDataSource {
    private UnadCalendarApi api;
    private List<Call> calls;

    public CourseRemoteRepo(UnadCalendarApi api) {
        this.api = api;
        calls = new ArrayList<>();
    }

    @Override
    public void getCourses(final LoadCoursesCallback callback) {
        Call<Course> call = api.getCourses();
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                callback.onCalendarsLoaded(response.body().cursos);
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
        calls.add(call);
    }

    @Override
    public void createNewCalendar(String courseCode, String email, String period,
                                  final SaveCourseCallback saveCourseCallback) {
        Call<NewCalendar> call = api.createNewCalendar(courseCode, email, period);
        call.enqueue(new Callback<NewCalendar>() {
            @Override
            public void onResponse(Call<NewCalendar> call, Response<NewCalendar> response) {
                saveCourseCallback.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<NewCalendar> call, Throwable t) {
                saveCourseCallback.onError(t);
            }
        });
        calls.add(call);
    }

    @Override
    public void close() {
        for (Call call : calls) {
            call.cancel();
        }
    }
}
