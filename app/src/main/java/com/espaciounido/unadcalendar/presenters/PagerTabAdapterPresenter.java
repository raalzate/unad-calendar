package com.espaciounido.unadcalendar.presenters;

import com.espaciounido.unadcalendar.interfaces.presenters.IPagerTabPresenter;
import com.espaciounido.unadcalendar.interfaces.views.IPagerTabAdapter;
import com.espaciounido.unadcalendar.interfaces.events.OnListenerEvent;

/**
 * Created by raalzate on 20/01/2016.
 */
public class PagerTabAdapterPresenter implements IPagerTabPresenter {

    private final IPagerTabAdapter pagerTabAdapter;

    public PagerTabAdapterPresenter(IPagerTabAdapter pagerTabAdapter) {
        this.pagerTabAdapter = pagerTabAdapter;
    }

    @Override
    public void setOnListenerEvent(OnListenerEvent onListenerEvent) {

    }
}
