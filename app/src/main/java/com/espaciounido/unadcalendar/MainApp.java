package com.espaciounido.unadcalendar;

import android.app.Application;

import com.espaciounido.unadcalendar.data.rest.RestClient;
import com.espaciounido.unadcalendar.settings.Preference;
import com.espaciounido.unadcalendar.settings.PreferenceModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by MyMac on 20/02/16.
 */
public class MainApp extends Application {
    private static RestClient restClient = new RestClient();
    private static Preference preferenceModel;

    public static Preference getPreferenceModel() {
        return preferenceModel;
    }

    public static RestClient getRestClient() {
        return restClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder(this)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        preferenceModel = new PreferenceModel(this);
    }
}
