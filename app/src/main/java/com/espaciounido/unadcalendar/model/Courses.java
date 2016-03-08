package com.espaciounido.unadcalendar.model;

/**
 * Created by rauloko on 7/02/15.
 */
public class Courses {
    private String name;
    private String code;
    private String credits;
    private String period;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return getCode() +" - " +getName();
    }
}
