package com.espaciounido.unadcalendar.dashboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.espaciounido.unadcalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by raalzate on 07/04/2016.
 */
public class CalendarEventView extends RelativeLayout implements View.OnClickListener {

    private TextView titleNumberDay;
    private OnDateChangeListener onDateChangeListener;
    private List<Button> buttonItems;
    private List<TextView> titleItems;


    public CalendarEventView(Context context) {
        super(context);
        init();
    }

    public CalendarEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarEventView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        buttonItems = new ArrayList<>();
        titleItems = new ArrayList<>();
        inflate(getContext(), R.layout.view_calendar_program, this);
        titleNumberDay = (TextView) findViewById(R.id.title_number_day);
        settingWeeks(0, getCalendar());
    }

    private void setupHeaderTitle(Calendar now) {
        Locale locale = Locale.getDefault();
        String month = now.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
        int year = now.get(Calendar.YEAR);
        titleNumberDay.setText(String.format("%s %s", month.toUpperCase(), year));
    }

    public void setPivotDaySubtract(int daySubtract){

        for (Button button : buttonItems) {
            button.setSelected(false);
        }

        Calendar cal = getCalendar();
        cal.add(Calendar.DATE, daySubtract);

        if (onDateChangeListener != null) {
            onDateChangeListener.onSelectedDayChange(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            );
        }

        renderViews(cal);
    }

    public Calendar getCalendar() {
        return Calendar.getInstance();
    }

    private void settingWeeks(int daySubtract, Calendar calendar) {
        setupHeaderTitle(calendar);
        calendar.add(Calendar.DATE, daySubtract);

        for (int i = 1; i <= 7; i++) {

            TextView textView = (TextView) findViewWithTag("view_calendar_program_title_col" + i);
            Button button = (Button) findViewWithTag("view_calendar_program_col" + i);
            button.setOnClickListener(this);

            buttonItems.add(button);
            titleItems.add(textView);
        }

        renderViews(calendar);
    }

    private void renderViews(Calendar calendar){
        String[] dayOfWeek = getContext().getResources().getStringArray(R.array.array_dayofweek);

        for (int i = 0; i <= 6; i++) {
            titleItems.get(i).setText(dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            buttonItems.get(i).setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

            Calendar calItem = getCalendar();
            calItem.setTime(calendar.getTime());
            buttonItems.get(i).setTag(calItem);

            calendar.add(Calendar.DATE, 1);

            selectedFirstDay(0);
        }
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }


    private void selectedFirstDay(int position) {
        Button button = buttonItems.get(position);
        button.setSelected(true);
    }

    private void itemSelected(View viewSelected) {
        for (Button button : buttonItems) {
            button.setSelected(false);
        }
        viewSelected.setSelected(true);
    }

    @Override
    public void onClick(View v) {

        Calendar cal = (Calendar) v.getTag();
        setupHeaderTitle(cal);
        itemSelected(v);

        if (onDateChangeListener != null) {
            onDateChangeListener.onSelectedDayChange(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            );
        }

    }

    public interface OnDateChangeListener {
        void onSelectedDayChange(int year, int month, int dayOfMonth);
    }
}
