package com.espaciounido.unadcalendar.views.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.espaciounido.unadcalendar.R;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by raalzate on 24/02/2016.
 */
public class DetailPlanCard extends Card {

    protected TextView mTextTitle;
    protected TextView mTextSubTitle1;
    protected TextView mTextSubTitle2;

    private String title;
    private String subtitle1;
    private String subtitle2;


    /**
     * Constructor with a custom inner layout
     * @param context
     */
    public DetailPlanCard(Context context) {
        this(context, R.layout.card_inner_content_detail_plan);
        subtitle1 = "";
        subtitle2 = "";
    }

    /**
     *
     * @param context
     * @param innerLayout
     */
    private DetailPlanCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    /**
     * Init
     */
    private void init(){

        //No Header

        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mTextTitle = (TextView) parent.findViewById(R.id.title);
        mTextSubTitle1 = (TextView) parent.findViewById(R.id.subtitle1);
        mTextSubTitle2 = (TextView) parent.findViewById(R.id.subtitle2);

        mTextTitle.setText(getTitle());
        mTextSubTitle1.setText(getSubtitle1());
        mTextSubTitle2.setText(getSubtitle2());

    }

    public String getSubtitle2() {
        return subtitle2;
    }

    public void setSubtitle2(String subtitle2) {
        this.subtitle2 = subtitle2;
    }

    public String getSubtitle1() {
        return subtitle1;
    }

    public void setSubtitle1(String subtitle1) {
        this.subtitle1 = subtitle1;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
