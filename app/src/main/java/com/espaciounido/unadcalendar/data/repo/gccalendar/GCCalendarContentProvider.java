package com.espaciounido.unadcalendar.data.repo.gccalendar;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by MyMac on 13/10/16.
 */

public class GCCalendarContentProvider extends ContentProvider {

    public static final String PROVIDER_NAME = "com.espaciounido.unadcalendar.GCCalendarContentProvider";
    public static final String URL = "content://" + PROVIDER_NAME + "/calendars";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private static final int CALENDARS = 1;
    private static final int CALENDAR_ID = 2;

    private static final String[] sColumns = new String[] {"calendarId", "name", "events"};

    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "calendars", CALENDARS);
        uriMatcher.addURI(PROVIDER_NAME, "calendars/#", CALENDAR_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }



    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        MatrixCursor matrixCursor = new MatrixCursor(sColumns);
        Realm realm = Realm.getDefaultInstance();
        switch (uriMatcher.match(uri)) {
            case CALENDARS:
                RealmResults<GCCalendar> calendars = realm
                        .where(GCCalendar.class)
                        .findAll();
                for (GCCalendar calendar :calendars){
                    Object[] rowData = new Object[]{calendar.getCalendarId(), calendar.getName(),
                            new Gson().toJson(calendar.getEvents())};
                    matrixCursor.addRow(rowData);
                }

                realm.close();
                break;

            case CALENDAR_ID:
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case CALENDARS:
                return "vnd.android.cursor.dir/vnd.unadcalendar.calendars";
            case CALENDAR_ID:
                return "vnd.android.cursor.item/vnd.unadcalendar.calendars";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
