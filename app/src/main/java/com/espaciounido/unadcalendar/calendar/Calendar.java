package com.espaciounido.unadcalendar.calendar;

import android.support.v4.app.FragmentManager;

/**
 * Created by MyMac on 17/09/16.
 */
public interface Calendar {
    interface View {
        void setAdapter(EventsPagerAdapter adapter);

        FragmentManager getSupportFragmentManager();

        String getName();

        String getCode();

        void setTitle(String title);

        void setCurrentItem(int position);

        void showConfirmDelete();

        void showSnarbar(String message);

        void finish();

        String getString(int id);
    }

    interface Presenter {
        void loadEvents();

        void deleteCalendar();
    }
}
