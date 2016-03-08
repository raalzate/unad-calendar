package com.espaciounido.unadcalendar.model.mock;

import com.espaciounido.unadcalendar.model.entitys.SetupActivity;

import java.util.ArrayList;

/**
 * Created by MyMac on 25/02/16.
 */
public class ActivityCourseModelMock {
    protected ArrayList<SetupActivity> listActivity;

    public ActivityCourseModelMock(){
        listActivity = new ArrayList<>();

        listActivity.add(new SetupActivity("Reconocimiento", SetupActivity.Priority.NORMAL, 1000, 10));
        listActivity.add(new SetupActivity("Reconocimiento2", SetupActivity.Priority.NORMAL, 4000, 50));
        listActivity.add(new SetupActivity("Reconocimiento3", SetupActivity.Priority.HIGH, 4000, 60));
    }
}
