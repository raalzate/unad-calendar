package com.espaciounido.unadcalendar.dashboard;

import android.os.Bundle;
import android.view.MenuItem;

import com.espaciounido.unadcalendar.MainApp;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.UseCaseHandler;
import com.espaciounido.unadcalendar.calendar.CalendarActivity;
import com.espaciounido.unadcalendar.course.SearcherCourseActivity;
import com.espaciounido.unadcalendar.dashboard.domain.model.Course;
import com.espaciounido.unadcalendar.dashboard.domain.model.ItemEvent;
import com.espaciounido.unadcalendar.dashboard.domain.usecase.GetCourseRegisterUseCase;
import com.espaciounido.unadcalendar.dashboard.domain.usecase.GetEventsUseCase;
import com.espaciounido.unadcalendar.dashboard.events.OnListItemsEvent;
import com.espaciounido.unadcalendar.dashboard.events.OnListRefressEvent;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarLocalRepo;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventLocalRepo;
import com.espaciounido.unadcalendar.settings.AboutActivity;
import com.espaciounido.unadcalendar.settings.Preference;
import com.espaciounido.unadcalendar.settings.PreferenceModel;
import com.espaciounido.unadcalendar.settings.SettingsActivity;
import com.espaciounido.unadcalendar.settings.SettingsBasicActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by MyMac on 20/02/16.
 */

public class DashboardPresenter implements Dashboard.Presenter {

    private final Dashboard.View view;
    private final UseCaseHandler useCaseHandler;
    private final PreferenceModel preference;
    private final GetCourseRegisterUseCase getCourseRegisterUseCase;
    private final GetEventsUseCase getEventsUseCase;

    private List<Course> courseList;

    public DashboardPresenter(Dashboard.View view, UseCaseHandler useCaseHandler, PreferenceModel preference) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.preference = preference;
        getCourseRegisterUseCase = new GetCourseRegisterUseCase(new GCCalendarLocalRepo());
        getEventsUseCase = new GetEventsUseCase(new GCEventLocalRepo(), new GCCalendarLocalRepo());

        EventBus.getDefault().register(this);

    }


    @Override
    public void onLoadSubMenuCourse() {

        view.clearSubMenuCourse();

        useCaseHandler.execute(
                getCourseRegisterUseCase,
                new GetCourseRegisterUseCase.Request(),
                new UseCase.UseCaseCallback<GetCourseRegisterUseCase.Response>() {
                    @Override
                    public void onSuccess(GetCourseRegisterUseCase.Response response) {
                        courseList = response.getData();
                        if (courseList.size() == 0) {
                            view.hideCalendar();
                        } else {
                            view.showCalendar();
                            for (Course course : courseList) {
                                view.addSubMenuCourse(course.code, course.name.toUpperCase());
                            }
                        }
                    }

                    @Override
                    public void onError() {
                        courseList = new ArrayList<>();
                        view.hideCalendar();

                    }
                });
    }

    @Override
    public void onLoadView() {
        loadEventsByDayOfYear(dayTodayOfYear());
        if (preference.getName() != null && !preference.getName().equals("")) {
            view.setNickname(preference.getName());
        } else {
            view.setNickname("UNAD Calendar");

        }
        if (preference.getEmail() != null && !preference.getEmail().equals("")) {
            view.setEmail(preference.getEmail());
        } else {
            view.setEmail("Universidad UNAD");
        }
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onOpenDrawer() {
        view.openDrawer();
    }

    @Override
    public boolean setOptionMenu(int id) {
        if (id == R.id.action_in_fifteen_days) {
            view.setPivotDaySubtractCalendar(15);
            return true;
        }

        if (id == R.id.action_today) {
            view.setPivotDaySubtractCalendar(0);
            return true;
        }

        if (id == R.id.action_in_moth) {
            view.setPivotDaySubtractCalendar(30);
            return true;
        }

        if (id == R.id.action_next_week) {
            view.setPivotDaySubtractCalendar(8);
            return true;
        }
        return false;
    }

    @Override
    public void loadListItemNewEvent(String code, String name) {
        view.showCalendar();
        view.addSubMenuCourse(code, name);
        courseList.add(new Course(name, code));
    }

    @Override
    public void onSelectedDayChange(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        loadEventsByDayOfYear(calendar.get(Calendar.DAY_OF_YEAR));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getGroupId() == R.id.group_menu_course_register) {
            for (Course course : courseList) {
                if (course.code.hashCode() == id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", course.name);
                    bundle.putString("code", course.code);
                    view.gotoActivity(CalendarActivity.class, bundle);
                    view.closeDrawer();
                }
            }
            return true;
        }

        return gotoMenu(id);
    }


    private boolean gotoMenu(int id) {
        view.closeDrawer();
        switch (id) {
            case R.id.nav_home:
                onLoadView();
                return true;
            case R.id.nav_add_course:
                if (hasSettingConfigured()) {
                    view.gotoActivity(SearcherCourseActivity.class);
                } else {
                    view.gotoActivity(SettingsBasicActivity.class);
                }

                return true;
            case R.id.nav_data:
                view.gotoActivity(SettingsBasicActivity.class);
                return true;

            case R.id.nav_notification:
                view.gotoActivity(SettingsActivity.class);
                return true;
            case R.id.nav_about:
                view.gotoActivity(AboutActivity.class);
                return true;

        }

        return true;
    }


    private boolean hasSettingConfigured() {
        Preference preferenceModel = MainApp.getPreferenceModel();
        return !(preferenceModel.getEmail().equals("Anonimo")
                || preferenceModel.getEmail().equals(""));
    }

    private int dayTodayOfYear() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    private void loadEventsByDayOfYear(final int dayOfYear) {
        useCaseHandler.execute(
                getEventsUseCase,
                new GetEventsUseCase.Request(dayOfYear),
                new UseCase.UseCaseCallback<GetEventsUseCase.Response>() {
                    @Override
                    public void onSuccess(GetEventsUseCase.Response response) {
                        EventBus.getDefault().post(new OnListItemsEvent(response.getData()));
                    }

                    @Override
                    public void onError() {
                        EventBus.getDefault().post(new OnListItemsEvent(new ArrayList<ItemEvent>()));
                        view.hideCalendar();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleOnListRefressEvent(OnListRefressEvent onListRefressEvent) {
        onLoadSubMenuCourse();
        loadEventsByDayOfYear(dayTodayOfYear());
    }
}
