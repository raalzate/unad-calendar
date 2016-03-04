package com.espaciounido.unadcalendar.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.interfaces.views.IActivityFragmentTab;
import com.espaciounido.unadcalendar.presenters.ActivityFragmentTabPresenter;

import it.gmariotti.cardslib.library.view.CardListView;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * Created by raalzate on 24/02/2016.
 */
public class ActivityFragmentTab extends RoboFragment implements IActivityFragmentTab {

    private static final String ARG_SECTION_NUMBER = "section_number";

    @InjectView(R.id.list_plan) CardListView listPlan;
    private ActivityFragmentTabPresenter activityFragmentTabPresenter;

    public ActivityFragmentTab() {
    }


    public static ActivityFragmentTab newInstance(int sectionNumber) {
        ActivityFragmentTab fragment = new ActivityFragmentTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_tab, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityFragmentTabPresenter
                = new ActivityFragmentTabPresenter(getActivity(), this);
        initViews();
    }

    private void initViews() {
        buildListCard();
    }

    private void buildListCard(){
        if(activityFragmentTabPresenter
                .getSectionedCardAdapter() != null) {

            listPlan.setExternalAdapter(activityFragmentTabPresenter
                    .getSectionedCardAdapter(), activityFragmentTabPresenter.getCardArrayAdapter());
        } else {
            listPlan.setAdapter(activityFragmentTabPresenter.getCardArrayAdapter());
        }
    }
}
