package com.espaciounido.unadcalendar.data.repo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by MyMac on 14/09/16.
 */
public abstract class FirebaseReferences {

    public static DatabaseReference getGCCalendar() {
        return FirebaseDatabase.getInstance()
                .getReference().child("GCCalendars");
    }

    public static DatabaseReference getUser() {
        return FirebaseDatabase.getInstance()
                .getReference().child("Users");
    }


}
