package com.espaciounido.unadcalendar.views;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.inject.Inject;
import com.rey.material.widget.ProgressView;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.interfaces.views.IMainActivity;
import com.espaciounido.unadcalendar.listeners.MainActivityListener;
import com.espaciounido.unadcalendar.presenters.MainActivityPresenter;
import com.espaciounido.unadcalendar.views.fragments.HomeCourseFragment;

import roboguice.event.EventManager;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity  implements
        IMainActivity, NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private MainActivityPresenter mainActivityPresenter;

    @Inject  MainActivityListener mainActivityListener;
    @InjectView(R.id.drawer_layout) DrawerLayout drawer;
    @InjectView(R.id.nav_view) NavigationView navigationView;
    @InjectView(R.id.progress_view) ProgressView progressView;
    @InjectView(R.id.cmb_toolbar) Spinner cmbToolbar;
    private Fragment fragmentCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityPresenter = new MainActivityPresenter(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initViews();

    }

    private void initViews() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,mainActivityPresenter.getDaysByWeek());

        adapter.setDropDownViewResource(R.layout.appbar_filter_list);
        cmbToolbar.setAdapter(adapter);
        cmbToolbar.setOnItemSelectedListener(this);


        MenuItem m = navigationView.getMenu().getItem(2);
        m.setTitle("Cursos Registrados");
        m.getSubMenu().add("CALCULO 2");

        onLoadFragment(getHomeFragment());
    }


    @Override
    public void onLoadFragment(Class fragmentClass) {
        progressView.setVisibility(View.VISIBLE);
        mainActivityPresenter.onLoadFragment(fragmentClass);
    }

    @Override
    public void onLoadedFragment(Status status) {
        progressView.setVisibility(View.GONE);
        switch (status) {
            case COMPLETED:
                break;
            case ERROR:
                System.out.println("Existe un problema");
                break;
        }

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        System.out.println(item.getTitle());
        item.setChecked(true);

        switch (id) {
            case R.id.nav_home:
            default:
                onLoadFragment(getHomeFragment());
                return true;

        }

    }

    @Override
    public boolean navigationToUp(Class fragmentClass, Bundle bundle, String title){

        drawer.closeDrawer(GravityCompat.START);

        if (fragmentCurrent != null) {
            if (fragmentCurrent.getClass().isAssignableFrom(fragmentClass)) {
                return false;
            }
        }

        try {

            fragmentCurrent = (Fragment) fragmentClass.newInstance();
            if(getFragmentManager().findFragmentByTag("content") == null) {
                fragmentCurrent.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_left, R.anim.exit_to_right);

                transaction.add(R.id.container, fragmentCurrent, "content").commit();
                setTitle(title);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public Class getHomeFragment() {
        return HomeCourseFragment.class;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
