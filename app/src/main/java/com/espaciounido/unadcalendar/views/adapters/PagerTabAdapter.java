package com.espaciounido.unadcalendar.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.interfaces.views.IPagerTabAdapter;
import com.espaciounido.unadcalendar.presenters.PagerTabAdapterPresenter;
import com.espaciounido.unadcalendar.views.fragments.IssuesFragmentTab;
import com.espaciounido.unadcalendar.views.fragments.ActivityFragmentTab;

/**
 * Created by raalzate on 20/01/2016.
 */
public class PagerTabAdapter extends FragmentPagerAdapter implements IPagerTabAdapter {


    private final PagerTabAdapterPresenter pagerTabAdapterPresenter;
    private final Context context;
    private String [] tabNames;



    public PagerTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.tabNames = context.getResources().getStringArray(R.array.fragment_option_tabs);
        pagerTabAdapterPresenter = new PagerTabAdapterPresenter(this);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  ActivityFragmentTab.newInstance(position + 1);
            case 1:
                return IssuesFragmentTab.newInstance(position + 1);
        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }





}
