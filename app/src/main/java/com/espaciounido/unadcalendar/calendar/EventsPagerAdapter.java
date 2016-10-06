package com.espaciounido.unadcalendar.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by MyMac on 17/09/16.
 */
public class EventsPagerAdapter extends FragmentPagerAdapter {
    private final String code;

    public EventsPagerAdapter(FragmentManager fm, String code) {
        super(fm);
        this.code = code;
    }

    @Override
    public Fragment getItem(int position) {
        return EventFragment.newInstance(position + 1, code);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "PENDIENTES";
            case 1:
                return "EN CURSO";
            case 2:
                return "FINALIZADAS";
        }
        return null;
    }
}
