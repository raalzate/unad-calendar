package com.espaciounido.unadcalendar.model.parser.issues;

import org.joda.time.DateTime;

/**
 * Created by MyMac on 26/02/16.
 */
public class ReminderNoteIssues extends Issues {

    public DateTime dateTimeEnd;

    private ReminderNoteIssues(){}

    public ReminderNoteIssues(String title, String subtitle, DateTime dateTimeEnd, String label){
        typeIssues = Type.REMINDER;
        setTitle(title);
        setSubTitle(subtitle);
        setLabel(label);
        setDateTimeEnd(dateTimeEnd);
    }

    public ReminderNoteIssues(String title, String subtitle, DateTime dateTimeEnd){
        this(title,subtitle, dateTimeEnd, null);
    }


    @Override
    public Type getType() {
        return typeIssues;
    }

    @Override
    void setTitle(String title) {

        this.title = title;
    }

    @Override
    void setSubTitle(String subtitle) {

        this.subtitle = subtitle;
    }

    @Override
    void setLabel(String label) {

        this.label = label;
    }


    public void setDateTimeEnd(DateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }
}
