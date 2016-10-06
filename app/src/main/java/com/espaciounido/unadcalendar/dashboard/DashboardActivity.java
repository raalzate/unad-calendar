package com.espaciounido.unadcalendar.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.espaciounido.unadcalendar.MainApp;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.UseCaseHandler;
import com.espaciounido.unadcalendar.course.SearcherCourseActivity;
import com.espaciounido.unadcalendar.settings.PreferenceModel;
import com.espaciounido.unadcalendar.utils.job.JobAlarmReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DashboardActivity extends AppCompatActivity implements Dashboard.View {

    private static final int REQUEST_SEARCHER = 1;
    @BindView(R.id.calendar)
    CalendarEventView calendarView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.content_empty)
    RelativeLayout contentEmpty;
    @BindView(R.id.title)
    TextView title;

    private Dashboard.Presenter presenter;
    private TextView email;
    private TextView nick;
    private ListEventFragment listEventFragment;
    private SubMenu subMenuCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new DashboardPresenter(this, UseCaseHandler.getInstance(), new PreferenceModel(this));

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initViews();

        new JobAlarmReceiver()
                .setAlarm(this, MainApp
                        .getPreferenceModel().getNotiActiveFrequency(), 0);

    }

    private void initViews() {

        View header = navigationView.getHeaderView(0);
        email = (TextView) header.findViewById(R.id.email);
        nick = (TextView) header.findViewById(R.id.nickname);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(presenter);
        calendarView.setOnDateChangeListener(presenter);
        setupSubMenuCourse();

        presenter.onLoadSubMenuCourse();
        presenter.onLoadView();

        setupWindowAnimations();
    }

    private void setupWindowAnimations() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);

            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setReturnTransition(slide);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return  presenter.setOptionMenu(id);
    }

    private void setupSubMenuCourse() {
        MenuItem m = navigationView.getMenu().getItem(2);
        m.setTitle(R.string.title_menu_course);
        subMenuCourse = m.getSubMenu();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void addSubMenuCourse(String code, String name) {
        subMenuCourse.add(R.id.group_menu_course_register, code.hashCode(), 1, name);
    }

    @Override
    public void clearSubMenuCourse() {
        subMenuCourse.removeGroup(R.id.group_menu_course_register);
        subMenuCourse.clear();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void gotoActivity(Class activity) {
        if (SearcherCourseActivity.class.isAssignableFrom(activity)) {
            startActivityForResult(new Intent(this, activity), REQUEST_SEARCHER);
        } else {
            startActivity(new Intent(this, activity));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SEARCHER) {
                String code = data.getExtras().getString("code");
                String name = data.getExtras().getString("name");
                presenter.loadListItemNewEvent(code, name);
                presenter.onLoadSubMenuCourse();
                presenter.onLoadView();

            }
        }
    }

    @Override
    public void gotoActivity(Class activity, Bundle extras) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void hideCalendar() {
        appBarLayout.setVisibility(View.GONE);
        appBarLayout.setExpanded(false, false);
        contentEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCalendar() {
        contentEmpty.setVisibility(View.GONE);
        appBarLayout.setExpanded(true, true);
        appBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void openDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void setNickname(String name) {
        nick.setText(name);
    }

    @Override
    public void setEmail(String email) {
        this.email.setText(email);
    }

    @Override
    public void setPivotDaySubtractCalendar(int daySubtractCalendar) {
        calendarView.setPivotDaySubtract(daySubtractCalendar);
    }

    @OnClick(R.id.open_drawer)
    public void onOpenDawer() {
        presenter.onOpenDrawer();
    }
}
