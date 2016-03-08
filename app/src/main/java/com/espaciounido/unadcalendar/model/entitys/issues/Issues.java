package com.espaciounido.unadcalendar.model.entitys.issues;

/**
 * Created by MyMac on 26/02/16.
 */
public abstract class Issues {

    public Type typeIssues;
    public  String title;
    public String subtitle;

    protected String label;

    public enum Type {
        NOTE("Notas"),
        REMINDER("Recordatorios");

        private String name;
        Type(String name) {
            this.name = name;
        }
        public String value(){
            return name;
        }
    }
    abstract void setTitle(String title);
    abstract void setSubTitle(String subtitle);
    abstract void setLabel(String label);
    public abstract Type getType();
}
