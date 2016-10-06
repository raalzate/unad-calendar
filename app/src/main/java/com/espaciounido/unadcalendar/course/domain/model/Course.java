package com.espaciounido.unadcalendar.course.domain.model;


/**
 * Created by rauloko on 7/02/15.
 */
public class Course {

    public final String name;
    public final String code;
    public final String credits;
    public final String period;

    public Course(String name, String code, String credits, String period) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.period = period;
    }

    public Course(String name, String code) {
        this(name, code, null, null);
    }


    @Override
    public String toString() {
        return name + " - " + code;
    }
}
