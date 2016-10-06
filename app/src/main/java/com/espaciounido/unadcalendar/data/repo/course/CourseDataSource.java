package com.espaciounido.unadcalendar.data.repo.course;


import java.util.List;

/**
 * Created by MyMac on 10/09/16.
 */
public interface CourseDataSource {
    void getCourses(LoadCoursesCallback callback);

    void createNewCalendar(String courseCode, String email, String period, SaveCourseCallback saveCourseCallback);

    void close();

    interface LoadCoursesCallback {
        void onCalendarsLoaded(List<Course.Curso> cursos);

        void onDataNotAvailable();
    }

    interface SaveCourseCallback {
        void onComplete(NewCalendar newCalendar);

        void onError(Throwable error);
    }
}
