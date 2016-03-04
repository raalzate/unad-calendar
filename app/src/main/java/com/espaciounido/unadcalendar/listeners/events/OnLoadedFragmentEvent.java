package com.espaciounido.unadcalendar.listeners.events;

import android.os.Bundle;

import com.espaciounido.unadcalendar.interfaces.views.IMainActivity;

/**
 * Created by MyMac on 21/02/16.
 */
public class OnLoadedFragmentEvent {

    public final IMainActivity mainActivity;

    public final String title;
    public final Bundle bundle;
    public final Class fragmentClass;

    private IMainActivity.Status status;

    public OnLoadedFragmentEvent(IMainActivity mainActivity, Class fragmentClass, Bundle bundle, String title) {
        this.mainActivity = mainActivity;
        this.title = title;
        this.bundle = bundle;
        this.fragmentClass = fragmentClass;
        withStatus(IMainActivity.Status.COMPLETED);
    }

    public OnLoadedFragmentEvent(IMainActivity mainActivity, Class fragmentClass, Bundle bundle) {
        this(mainActivity, fragmentClass, bundle, "");
    }

    public OnLoadedFragmentEvent(IMainActivity mainActivity, Class fragmentClass, String title) {
        this(mainActivity, fragmentClass, new Bundle(), title);
    }

    public OnLoadedFragmentEvent(IMainActivity mainActivity, Class fragmentClass) {
        this(mainActivity, fragmentClass, new Bundle(), "");
    }

    public IMainActivity.Status getStatus() {
        return status;
    }

    public OnLoadedFragmentEvent withStatus(IMainActivity.Status status) {
        this.status = status;
        return this;
    }
}
