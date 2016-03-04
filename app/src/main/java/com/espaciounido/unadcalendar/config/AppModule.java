package com.espaciounido.unadcalendar.config;

import com.espaciounido.unadcalendar.views.MainActivity;
import com.espaciounido.unadcalendar.interfaces.views.IMainActivity;
import com.espaciounido.unadcalendar.interfaces.presenters.IMainActivityPresenter;
import com.espaciounido.unadcalendar.presenters.MainActivityPresenter;
import com.google.inject.AbstractModule;

/**
 * Created by MyMac on 21/02/16.
 */
public class AppModule extends AbstractModule {

    @Override protected void configure()
    {
        bind(IMainActivityPresenter.class).to(MainActivityPresenter.class);
        bind(IMainActivity.class).to(MainActivity.class);
    }
}
