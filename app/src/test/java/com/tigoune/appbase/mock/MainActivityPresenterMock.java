package com.espaciounido.unadcalendar.mock;


import com.espaciounido.unadcalendar.interfaces.views.IMainActivity;
import com.espaciounido.unadcalendar.interfaces.presenters.IMainActivityPresenter;

/**
 * Created by MyMac on 21/02/16.
 */
public class MainActivityPresenterMock implements IMainActivityPresenter {
    private IMainActivity mainActivity;

    public MainActivityPresenterMock(IMainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLoadFragment(Class fragmentClass) {
        if(SessionUserFragment.class.isAssignableFrom(fragmentClass)) {
            mainActivity.onLoadedFragment(IMainActivity.Status.COMPLETED);
        }
    }
}
