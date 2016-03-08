package com.espaciounido.unadcalendar.model.entitys;

/**
 * Created by raalzate on 25/02/2016.
 */
public class SetupActivity {

    public enum Priority {
        HIGH(10, "Prioridad Alta"),
        HALF(5, "Prioridad Media"),
        LOW(1, "Prioridad Baja"),
        NORMAL(0, "Normal");

        private  int i;
        private String name;

        Priority(int i, String name) {
            this.i = i;
            this.name = name;
        }

        public int value(){
            return i;
        }

        public String description(){
            return name;
        }
    }

    public final String title;
    public final Priority priority;
    public final int percentage;
    public final int maxTimeDay;
    public final int thumbnail;

    public SetupActivity(String title, Priority priority, int maxTimeDay, int percentage, int thumbnail) {
        this.title = title;
        this.priority = priority;
        this.percentage = percentage;
        this.maxTimeDay = maxTimeDay;
        this.thumbnail = thumbnail;
    }

    public SetupActivity(String title, Priority priority, int maxTimeDay, int percentage) {
       this(title, priority, percentage, maxTimeDay, 0);
    }

}
