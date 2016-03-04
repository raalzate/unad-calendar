package com.espaciounido.unadcalendar.model.parser.issues;

/**
 * Created by MyMac on 26/02/16.
 */
public class NoteIssues extends Issues {


    private NoteIssues(){ }

    public NoteIssues(String title, String subtitle, String label){
        typeIssues = Type.NOTE;
        setTitle(title);
        setSubTitle(subtitle);
        setLabel(label);
    }

    public NoteIssues(String title, String subtitle){
        this(title, subtitle, null);
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

    @Override
    public Type getType() {
        return typeIssues;
    }


}
