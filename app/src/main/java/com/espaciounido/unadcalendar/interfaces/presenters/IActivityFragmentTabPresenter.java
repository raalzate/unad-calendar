package com.espaciounido.unadcalendar.interfaces.presenters;

import com.espaciounido.unadcalendar.model.parser.SetupActivity;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.prototypes.SectionedCardAdapter;

/**
 * Created by MyMac on 25/02/16.
 */
public interface IActivityFragmentTabPresenter {
    ArrayList<SetupActivity> getListActivity();
    ArrayList<Card> getActivityCard();
    SectionedCardAdapter getSectionedCardAdapter();
    CardArrayAdapter getCardArrayAdapter();
}
