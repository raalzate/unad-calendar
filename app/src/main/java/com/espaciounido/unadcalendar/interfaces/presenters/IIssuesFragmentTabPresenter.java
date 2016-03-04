package com.espaciounido.unadcalendar.interfaces.presenters;

import com.espaciounido.unadcalendar.model.parser.issues.Issues;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.prototypes.SectionedCardAdapter;

/**
 * Created by MyMac on 26/02/16.
 */
public interface IIssuesFragmentTabPresenter {
    ArrayList<Issues> getListIssues();
    ArrayList<Card> getIssuesCard();
    SectionedCardAdapter getSectionedCardAdapter();
    CardArrayAdapter getCardArrayAdapter();
}
