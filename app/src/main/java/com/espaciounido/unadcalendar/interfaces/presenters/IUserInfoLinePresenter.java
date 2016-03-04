package com.espaciounido.unadcalendar.interfaces.presenters;

import android.widget.ArrayAdapter;


/**
 * Created by raalzate on 20/01/2016.
 */
public interface IUserInfoLinePresenter {
    ArrayAdapter<?> getAdapter();
    void loadDataService();
}
