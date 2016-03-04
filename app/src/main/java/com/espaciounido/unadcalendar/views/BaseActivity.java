package com.espaciounido.unadcalendar.views;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.espaciounido.unadcalendar.R;
import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.event.EventManager;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by MyMac on 20/02/16.
 */
@ContentView(R.layout.activity_main)
public class BaseActivity  extends RoboActionBarActivity {

    protected @Inject EventManager eventManager;
    protected @InjectView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
