package com.espaciounido.unadcalendar.model;

import com.espaciounido.unadcalendar.model.mock.IssuesCourseModelMock;
import com.espaciounido.unadcalendar.model.entitys.issues.Issues;

import java.util.ArrayList;

/**
 * Created by MyMac on 26/02/16.
 */
public class IssuesCourseModel extends IssuesCourseModelMock {

    public IssuesCourseModel(){ super();}

    public ArrayList<Issues> getIssues(){
        return noteIssues;
    }


}
