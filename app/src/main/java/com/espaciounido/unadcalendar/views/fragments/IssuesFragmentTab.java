package com.espaciounido.unadcalendar.views.fragments;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.util.ViewUtil;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.interfaces.views.IIssuesFragmentTab;
import com.espaciounido.unadcalendar.presenters.IssuesFragmentTabPresenter;

import it.gmariotti.cardslib.library.view.CardListView;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * Created by raalzate on 24/02/2016.
 */
public class IssuesFragmentTab extends RoboFragment implements IIssuesFragmentTab, View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    @InjectView(R.id.list_plan) CardListView listPlan;
    @InjectView(R.id.fab_menu) FloatingActionsMenu menu;
    @InjectView(R.id.fab_note) FloatingActionButton menuNote;
    @InjectView(R.id.fab_reminder) FloatingActionButton menuReminder;

    private IssuesFragmentTabPresenter issuesFragmentTabPresenter;

    public IssuesFragmentTab() {
    }


    public static IssuesFragmentTab newInstance(int sectionNumber) {
        IssuesFragmentTab fragment = new IssuesFragmentTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_issues_tab, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        issuesFragmentTabPresenter
                = new IssuesFragmentTabPresenter(getActivity(), this);
        initViews();
    }

    private void initViews() {
        menuReminder.setOnClickListener(this);
        menuNote.setOnClickListener(this);
        buildListCard();
    }



    private void buildListCard(){

        if(issuesFragmentTabPresenter
                .getSectionedCardAdapter() != null) {

            listPlan.setExternalAdapter(issuesFragmentTabPresenter
                    .getSectionedCardAdapter(), issuesFragmentTabPresenter.getCardArrayAdapter());
        } else {
            listPlan.setAdapter(issuesFragmentTabPresenter.getCardArrayAdapter());
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        menu.collapse();
        switch (id){
            case R.id.fab_note:
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                View v = LayoutInflater.from(getContext()).inflate(R.layout.view_bottomsheet, null);
                ViewUtil.setBackground(v, ResourcesCompat.getDrawable(getResources(),
                        R.drawable.bg_window_light, null));
                bottomSheetDialog
                        .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .contentView(v)
                .show();

                break;
            case R.id.fab_reminder:
                break;
        }
    }
}
