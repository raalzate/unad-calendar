package com.espaciounido.unadcalendar.views.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.model.parser.issues.Issues;

import it.gmariotti.cardslib.library.internal.Card;


/**
 * Created by raalzate on 25/02/2016.
 */
public class IssuesCard extends Card {

    TextView mTitle;
    TextView mSubtitle;


    private Issues issues;


    public IssuesCard(Context context, Issues issues) {
        super(context, R.layout.card_inner_content_issues);
        this.issues = issues;
        init();
    }

    private IssuesCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    /**
     * Init
     */
    private void init(){
        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=" + issues.title, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {


        mTitle = (TextView)parent.findViewById(R.id.title);
        mSubtitle = (TextView)parent.findViewById(R.id.subtitle);
        mTitle.setText(issues.title);
        mSubtitle.setText(issues.subtitle);

    }




}
