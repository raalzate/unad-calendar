package com.espaciounido.unadcalendar.utils;

/**
 * Created by rauloko on 27/01/15.
 */
public interface CallBack {


    public static int ACTION_LIST_DIARY = 1;

    public void action(int action);
    public void error(String msn);
}
