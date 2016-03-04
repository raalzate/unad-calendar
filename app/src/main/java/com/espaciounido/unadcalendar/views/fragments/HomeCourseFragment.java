package com.espaciounido.unadcalendar.views.fragments;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.interfaces.views.IHomeCourseFragment;
import com.espaciounido.unadcalendar.interfaces.events.OnListenerEvent;
import com.espaciounido.unadcalendar.views.adapters.PagerTabAdapter;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;


public class HomeCourseFragment extends RoboFragment implements IHomeCourseFragment,
        ViewPager.OnPageChangeListener {

    private PagerTabAdapter pagerTabAdapter;
    private OnListenerEvent onListenerEvent;

    @InjectView(R.id.pager) ViewPager pager;
    @InjectView(R.id.contentTabFragment) PagerSlidingTabStrip contentTabFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerTabAdapter = new PagerTabAdapter(getChildFragmentManager(), getActivity());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_course, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        pager.setAdapter(pagerTabAdapter);
        contentTabFragment.setViewPager(pager);
        contentTabFragment.setOnPageChangeListener(this);
    }


    @Override
    public void setOnListenerEvent(OnListenerEvent onListenerEventProgram) {
        this.onListenerEvent = onListenerEventProgram;
    }

    @Override
    public OnListenerEvent getOnListenerEvent() {
        return this.onListenerEvent;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}

