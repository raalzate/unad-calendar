package com.espaciounido.unadcalendar.calendar;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.espaciounido.unadcalendar.calendar.domain.EventCalendar;
import com.espaciounido.unadcalendar.calendar.domain.Task;

/**
 * Created by MyMac on 25/09/16.
 */
public interface Event {
    interface View {
        void bindEvent(EventCalendar event);
        void setBackgroundColor(int color);
        void setStatusTitleColor(int color);
        void showSnarbar(String message);
        void setTitle(String title);
        void setCountdownImage(Drawable drawable);
        void actionCalendarLocal(EventCalendar event);
        void setTaskAdapter(RecyclerView.Adapter taskAdapter);
    }

    interface Presenter extends TaskAdapter.OnChangeListener{
        void loadEvent(String eventId);
        void loadTasks(String eventId);
        void showEventCalendarLocal();
        void newTask(Task task, DialogInterface dialog);
    }
}
