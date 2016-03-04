package com.espaciounido.unadcalendar.presenters;

import android.content.Context;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.interfaces.presenters.IIssuesFragmentTabPresenter;
import com.espaciounido.unadcalendar.model.IssuesCourseModel;
import com.espaciounido.unadcalendar.model.parser.issues.Issues;
import com.espaciounido.unadcalendar.views.cards.IssuesCard;
import com.espaciounido.unadcalendar.views.fragments.IssuesFragmentTab;

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
 * Created by MyMac on 26/02/16.
 */
public class IssuesFragmentTabPresenter implements IIssuesFragmentTabPresenter {

    private final Context context;
    private final IssuesFragmentTab issuesFragmentTab;
    private final IssuesCourseModel issuesCourseModel;

    private ArrayList<Card> cards;
    private TreeMap<Issues.Type, Integer> treeSetSections;
    private ArrayList<Issues> listIssues;
    private SectionedCardAdapter sectionedCardAdapter;

    public IssuesFragmentTabPresenter(Context context, IssuesFragmentTab issuesFragmentTab){
        this.context = context;
        this.issuesFragmentTab = issuesFragmentTab;
        this.cards = new ArrayList<>();
        this.treeSetSections = new TreeMap<>();
        this.issuesCourseModel = new IssuesCourseModel();
        setupVars();
    }

    private void setupVars() {
        listIssues = issuesCourseModel.getIssues();

        Collections.sort(listIssues, new Comparator<Issues>() {
            @Override
            public int compare(Issues t1, Issues t2) {
                return t2.getType().compareTo(t1.getType());
            }
        });

        int count = 0;
        for (Issues issues : listIssues) {
            IssuesCard card = new IssuesCard(context, issues);
            if(!treeSetSections.containsKey(issues.getType()))
                treeSetSections.put(issues.getType(), count);
            cards.add(card);
            count++;
        }

        List<CardSection> sections = new ArrayList<>();
        for(Issues.Type type : treeSetSections.keySet()) {
            sections.add(new CardSection(treeSetSections.get(type), type.value()));
        }
        CardSection[] section = new CardSection[sections.size()];
        sectionedCardAdapter = new SectionedCardAdapter(context,
                R.layout.section_base_layout, getCardArrayAdapter());
        sectionedCardAdapter.setCardSections(sections.toArray(section));
    }

    @Override
    public ArrayList<Issues> getListIssues() {
        return listIssues;
    }

    @Override
    public ArrayList<Card> getIssuesCard() {
        return cards;
    }

    @Override
    public SectionedCardAdapter getSectionedCardAdapter() {
        return sectionedCardAdapter;
    }

    @Override
    public CardArrayAdapter getCardArrayAdapter() {
        return new CardArrayAdapter(context, getIssuesCard());
    }
}
