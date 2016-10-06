package com.espaciounido.unadcalendar.settings;

/**
 * Created by MyMac on 14/09/16.
 */
public class UserData {
    public final String name;
    public final String period;
    public final String email;
    public final String carrera;
    public final String cead;
    public final String token;

    public UserData(String name, String period, String email, String carrera, String cead, String token) {
        this.name = name;
        this.period = period;
        this.email = email;
        this.carrera = carrera;
        this.cead = cead;
        this.token = token;
    }
}
