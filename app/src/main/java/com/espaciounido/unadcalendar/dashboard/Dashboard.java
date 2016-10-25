package com.espaciounido.unadcalendar.dashboard;

import android.os.Bundle;
import android.support.design.widget.NavigationView;

/**
 * Created by MyMac on 4/09/16.
 */
public class Dashboard {

    public interface View extends EventAdapter.OnClickEventListener{
        void addSubMenuCourse(String code, String name);

        void clearSubMenuCourse();

        void closeDrawer();

        void gotoActivity(Class activity);

        void gotoActivity(Class activity, Bundle extras);

        void hideCalendar();

        void showCalendar();

        void openDrawer();

        void setNickname(String name);

        void setEmail(String email);

        void setPivotDaySubtractCalendar(int daySubtractCalendar);
    }

    public interface Presenter extends
            NavigationView.OnNavigationItemSelectedListener,
            CalendarEventView.OnDateChangeListener {

        void loadListItemNewEvent(String code, String name);

        void onLoadSubMenuCourse();

        void onLoadView();

        void onStop();

        void onOpenDrawer();

        boolean setOptionMenu(int id);
    }


}
