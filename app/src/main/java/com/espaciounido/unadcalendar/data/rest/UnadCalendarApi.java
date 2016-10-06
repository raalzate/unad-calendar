package com.espaciounido.unadcalendar.data.rest;


import com.espaciounido.unadcalendar.data.repo.course.Course;
import com.espaciounido.unadcalendar.data.repo.course.NewCalendar;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MyMac on 5/09/16.
 */
public interface UnadCalendarApi {

    @GET("v2/static-data/cursos.json")
    Call<Course> getCourses();


    @GET("v2/")
    Call<NewCalendar> createNewCalendar(
            @Query("cs") String code,
            @Query("email") String email,
            @Query("pd") String period);
}
