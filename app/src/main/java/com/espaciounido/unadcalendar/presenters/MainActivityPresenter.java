package com.espaciounido.unadcalendar.presenters;

import android.os.Bundle;

import com.espaciounido.unadcalendar.listeners.events.OnLoadedFragmentEvent;
import com.espaciounido.unadcalendar.interfaces.views.IMainActivity;
import com.espaciounido.unadcalendar.interfaces.presenters.IMainActivityPresenter;
import com.espaciounido.unadcalendar.views.fragments.HomeCourseFragment;

/**
 * Created by MyMac on 20/02/16.
 */

public class MainActivityPresenter implements IMainActivityPresenter {
    private final IMainActivity iMainActivity;

    public MainActivityPresenter(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
    }

    @Override
    public void onLoadFragment(final Class fragmentClass) {


        if(HomeCourseFragment.class.isAssignableFrom(fragmentClass)){
            Bundle bundle = new Bundle();
            iMainActivity.getEventManager().fire(
                    new OnLoadedFragmentEvent(
                            iMainActivity,
                            fragmentClass,
                            bundle,
                            "Mi home").withStatus(IMainActivity.Status.COMPLETED));


        }
    }

    @Override
    public String[] getDaysByWeek() {
        return new String[]{"LUNES 2 DE JUNIO", "MARTES 3 DE JUNIO"};
    }

}
