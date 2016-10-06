package com.espaciounido.unadcalendar.course;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;

import com.espaciounido.unadcalendar.course.domain.model.Course;

import java.util.List;

/**
 * Created by MyMac on 4/09/16.
 */
public class SearcherCourse {

    public interface Presenter extends
            MenuItemCompat.OnActionExpandListener,
            CourseAdapter.OnItemClickListener,
            SearchView.OnQueryTextListener {

        List<Course> filter(List<Course> models, String query);

        void loadCourses();

        void onStop();
    }

    public interface View {
        void setAdapter(CourseAdapter adapter);

        void showProgress();

        void hideProgress();

        void showDialogAlert(String title, String message);

        void resultCourse(Course course);

        Snackbar createSnackbar(String message);

        Context getBaseContext();
    }

}
