package com.espaciounido.unadcalendar.model;

import com.espaciounido.unadcalendar.model.mock.ActivityCourseModelMock;
import com.espaciounido.unadcalendar.model.entitys.SetupActivity;

import java.util.ArrayList;

/**
 * Created by MyMac on 25/02/16.
 */
public class CourseModel extends ActivityCourseModelMock {

    public CourseModel(){
        super();
    }

    public ArrayList<SetupActivity> getListActivity() {
        return listActivity;
    }

}
