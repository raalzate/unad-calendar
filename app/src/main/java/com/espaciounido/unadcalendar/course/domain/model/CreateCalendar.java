package com.espaciounido.unadcalendar.course.domain.model;

/**
 * Created by MyMac on 6/09/16.
 */
public class CreateCalendar {
    public final String courseCode;
    public final String period;
    public final String email;

    public CreateCalendar(String courseCode, String email, String period) {
        this.courseCode = courseCode;
        this.period = period;
        this.email = email;
    }
}
