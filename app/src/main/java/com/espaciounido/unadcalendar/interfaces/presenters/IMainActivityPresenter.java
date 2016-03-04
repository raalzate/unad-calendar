package com.espaciounido.unadcalendar.interfaces.presenters;

/**
 * Created by MyMac on 20/02/16.
 */
public interface IMainActivityPresenter {
    void onLoadFragment(Class fragmentClass);
    String[] getDaysByWeek();
}
