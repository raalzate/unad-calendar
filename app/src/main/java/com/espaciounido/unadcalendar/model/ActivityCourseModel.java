package com.espaciounido.unadcalendar.model;

import com.espaciounido.unadcalendar.model.mock.ActivityCourseModelMock;
import com.espaciounido.unadcalendar.model.parser.SetupActivity;

import java.util.ArrayList;

/**
 * Created by MyMac on 25/02/16.
 */
public class ActivityCourseModel extends ActivityCourseModelMock {

    public ActivityCourseModel(){
        super();
    }

    public ArrayList<SetupActivity> getListActivity() {
        return listActivity;
    }

}
