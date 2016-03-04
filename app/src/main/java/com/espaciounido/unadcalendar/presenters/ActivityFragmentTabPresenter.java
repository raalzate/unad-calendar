package com.espaciounido.unadcalendar.presenters;

import android.app.Activity;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.interfaces.views.IActivityFragmentTab;
import com.espaciounido.unadcalendar.interfaces.presenters.IActivityFragmentTabPresenter;
import com.espaciounido.unadcalendar.model.ActivityCourseModel;
import com.espaciounido.unadcalendar.model.parser.SetupActivity;
import com.espaciounido.unadcalendar.views.cards.ActivityCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.prototypes.CardSection;
import it.gmariotti.cardslib.library.prototypes.SectionedCardAdapter;

/**
 * Created by MyMac on 25/02/16.
 */
public class ActivityFragmentTabPresenter implements IActivityFragmentTabPresenter {

    private final ActivityCourseModel activityCourseModel;

    private final IActivityFragmentTab yourActivityFragment;
    private final Activity activity;

    private ArrayList<Card> cards;
    private TreeMap<SetupActivity.Priority, Integer> treeSetSections;
    private ArrayList<SetupActivity> listActivitys;
    private SectionedCardAdapter sectionedCardAdapter;

    public ActivityFragmentTabPresenter(Activity activity, IActivityFragmentTab yourActivityFragment){
        this.yourActivityFragment = yourActivityFragment;
        this.activity = activity;
        this.cards = new ArrayList<>();
        this.treeSetSections = new TreeMap<>();
        this.activityCourseModel = new ActivityCourseModel();
        setupVars();
    }

    private void setupVars() {
        listActivitys = activityCourseModel.getListActivity();
        Collections.sort(listActivitys, new Comparator<SetupActivity>() {
            @Override
            public int compare(SetupActivity t1, SetupActivity t2) {
                return t1.priority.compareTo(t2.priority);
            }
        });

        int count = 0;
        for (SetupActivity setupActivity  : listActivitys) {
            ActivityCard activityCard = new ActivityCard(getActivity(), setupActivity);
            if(!treeSetSections.containsKey(setupActivity.priority))
                treeSetSections.put(setupActivity.priority, count);
            cards.add(activityCard);
            count++;
        }


        List<CardSection> sections = new ArrayList<>();
        for(SetupActivity.Priority priority : treeSetSections.keySet()) {
            treeSetSections.get(priority);
            sections.add(new CardSection(treeSetSections.get(priority), priority.description()));
        }
        CardSection[] prioritySection = new CardSection[sections.size()];
        sectionedCardAdapter = new SectionedCardAdapter(getActivity(),
                R.layout.section_base_layout, getCardArrayAdapter());
        sectionedCardAdapter.setCardSections(sections.toArray(prioritySection));
    }


    @Override
    public ArrayList<SetupActivity> getListActivity() {
        return listActivitys;
    }

    @Override
    public ArrayList<Card> getActivityCard() {
        return cards;
    }

    @Override
    public SectionedCardAdapter getSectionedCardAdapter() {
        return sectionedCardAdapter;
    }

    @Override
    public CardArrayAdapter getCardArrayAdapter() {
        return new CardArrayAdapter(getActivity(), getActivityCard());
    }

    private Activity getActivity() {
        return activity;
    }
}
