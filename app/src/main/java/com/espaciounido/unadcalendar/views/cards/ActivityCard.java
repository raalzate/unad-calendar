package com.espaciounido.unadcalendar.views.cards;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.model.parser.SetupActivity;

import it.gmariotti.cardslib.library.internal.Card;


/**
 * Created by raalzate on 25/02/2016.
 */
public class ActivityCard extends Card {

    TextView mTitle;
    TextView mSubtitle;
    ImageView mThumbnail;
    NumberProgressBar mPercentage;
    TextView mLabelLeft;
    TextView mLabelRight;


    private SetupActivity setupActivity;


    public ActivityCard(Context context, SetupActivity setupActivity) {
        super(context, R.layout.card_inner_content_activity);
        this.setupActivity = setupActivity;
        init();
    }

    private ActivityCard(Context context, int innerLayout) {
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
                Toast.makeText(getContext(), "Click Listener card=" + setupActivity.title, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        int color  = getColor(setupActivity.priority);

        mThumbnail = (ImageView)parent.findViewById(R.id.thumbnail);
        mTitle = (TextView)parent.findViewById(R.id.title);
        mLabelLeft = (TextView)parent.findViewById(R.id.label_left);
        mLabelRight = (TextView)parent.findViewById(R.id.label_right);
        mPercentage = (NumberProgressBar)parent.findViewById(R.id.number_progress_bar);

        mPercentage.setMax(100);
        mPercentage.setProgress(setupActivity.percentage);
        mPercentage.setReachedBarColor(color);

        mTitle.setText(setupActivity.title);
        mTitle.setTextColor(color);
        //mThumbnail.setImageResource(setupActivity.thumbnail);
        mLabelRight.setText(getFormatText(setupActivity.priority, setupActivity.maxTimeDay, 100));
        mLabelLeft.setText(getFormatText(setupActivity.priority, setupActivity.maxTimeDay, setupActivity.percentage));
        mLabelRight.setTextColor(color);
        mLabelLeft.setTextColor(color);

    }

    private int getColor(SetupActivity.Priority unit){
        Resources resource = getContext().getResources();
        switch (unit) {
            case NORMAL: return resource.getColor(R.color.purple);
            case LOW: return resource.getColor(R.color.light_orange);
            case HALF: return resource.getColor(R.color.orange);
            case HIGH: return resource.getColor(R.color.red);
        }
        return 0;
    }

    private String getFormatText(SetupActivity.Priority priority, int max, int percentage){
        int result;
        switch (priority) {
            case NORMAL:
                result = max * percentage/100;
                return String.format("%s Sememas.", result);
            case LOW:
                result = max * percentage/100;
                return String.format("%s Dias.", result);

            case HALF:
                result = max * percentage/100;
                return String.format("%s Dias.", result);

            case HIGH:
                result = max * percentage/100;
                return String.format("%s Horas.", result);

            default:
                return null;
        }
    }
}
