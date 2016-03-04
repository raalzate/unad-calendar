package com.espaciounido.unadcalendar.model.mock;



import com.espaciounido.unadcalendar.model.parser.issues.Issues;
import com.espaciounido.unadcalendar.model.parser.issues.NoteIssues;
import com.espaciounido.unadcalendar.model.parser.issues.ReminderNoteIssues;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by MyMac on 26/02/16.
 */
public class IssuesCourseModelMock {
    protected ArrayList<Issues> noteIssues;

    public IssuesCourseModelMock(){
        noteIssues = new ArrayList<>();

        noteIssues.add(new NoteIssues("Mi nota", "Descripcion de la nota"));
        noteIssues.add(new NoteIssues("Mi nota 2", "Descripcion de la nota", "importante"));
        noteIssues.add(new ReminderNoteIssues("Recordatorio", "REcordar todo", DateTime.now()));
    }
}
