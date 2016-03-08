package com.espaciounido.unadcalendar.services;

import android.app.Activity;
import android.graphics.Color;


import com.espaciounido.unadcalendar.utils.CallBack;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.model.Diarys;
import com.espaciounido.unadcalendar.model.Event;
import com.espaciounido.unadcalendar.model.ModelSerializableStore;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raalzate on 02/08/2015.
 */
public class CourseCalendar {

    private Activity mActivity;
    private Snackbar mSnackbar;
    private String period;
    private String course;
    private boolean accepted;
    private String email;

    private  static CourseCalendar mCourseCalendar;
    private CallBack callBack;

    public static CourseCalendar newInstance(Activity activity){
        if(mCourseCalendar == null)
           mCourseCalendar = new CourseCalendar(activity);
        return mCourseCalendar;
    }

    public CourseCalendar(Activity activity){
        mActivity = activity;
    }

    private void setTextStatus(String msn, boolean hasError){
        mSnackbar = Snackbar.with(mActivity)
                .type(SnackbarType.MULTI_LINE)
                .text(msn)
                .animation(false)
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE);

        if (hasError) {
            mSnackbar.actionLabel("Volver a Intentar")
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            snackbar.dismiss();
                            searchAgenda( period,  course,  email,  accepted, callBack);
                        }
                    })
                    .color(Color.RED);
        }
        SnackbarManager.show(mSnackbar, mActivity);
    }
    /**
     * Esta funcion se implementara en versiones posteriores
     */
    public void searchAgenda(String period,
                             String course,
                             String email,
                             boolean accepted,
                             CallBack callBack) {

        this.period   = period;
        this.course   = course;
        this.email    = email;
        this.accepted = accepted;
        this.callBack = callBack;

        setTextStatus("Buscando agenda...", false);


    }

    private void settingData(JSONObject response) throws  JSONException{
            if (response.getInt("code") == 0) {

                setTextStatus("Agregando eventos...", false);

                String calendar = response.getJSONObject("success").getString("calendar");
                String id = response.getJSONObject("success").getString("id");

                JSONArray arrEvents = response.getJSONObject("success")
                        .getJSONArray("events");

                ArrayList<Event> events = new ArrayList<Event>();

                for (int i = 0; i < arrEvents.length(); i++) {
                    Event event = new Event();
                    event.setDescription(arrEvents.getJSONObject(i).getString("description"));
                    event.setEnd(arrEvents.getJSONObject(i).getString("end"));
                    event.setStart(arrEvents.getJSONObject(i).getString("start"));
                    event.setSummary(arrEvents.getJSONObject(i).getString("summary"));
                    event.setHtmlLink(arrEvents.getJSONObject(i).getString("htmlLink"));
                    event.setId(arrEvents.getJSONObject(i).getString("id"));

                    events.add(i, event);
                }
                Diarys diarys = new Diarys();
                diarys.setId(id);
                diarys.setEvents(events);
                diarys.setName(calendar);

                updateChannels(this.course);

                new ModelSerializableStore(mActivity).saveEvent(diarys, id);

            } else {
                //Track Event Error
                Map<String, String> dimensions = new HashMap<>();
                dimensions.put("Type", "JSONException");
                dimensions.put("LocalizedMessage", CourseCalendar.class.getCanonicalName());
                dimensions.put("Method", "settingData");
                ParseAnalytics.trackEventInBackground("Error", dimensions);

                throw new JSONException(response.getString("error"));
            }

    }

    /**
     * Funcion que actualiza el canal
     * @param channel
     */
    private void updateChannels(String channel){
        ParseInstallation install = ParseInstallation.getCurrentInstallation();

        List<String> channels = ParseInstallation.getCurrentInstallation().getList("channels");
        channels.add(channel);
        install.put("channels",channels);

        install.saveInBackground();
    }
}
