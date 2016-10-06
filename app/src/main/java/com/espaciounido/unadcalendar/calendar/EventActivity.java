package com.espaciounido.unadcalendar.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.UseCaseHandler;
import com.espaciounido.unadcalendar.calendar.domain.EventCalendar;
import com.espaciounido.unadcalendar.calendar.domain.Task;
import com.espaciounido.unadcalendar.utils.Utils;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventActivity extends AppCompatActivity implements Event.View{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinator) CoordinatorLayout coordinator;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar) AppBarLayout appBar;
    @BindView(R.id.summary) TextView summary;
    @BindView(R.id.end) TextView end;
    @BindView(R.id.start) TextView start;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.countdown) ImageView countdown;
    @BindView(R.id.todo_list) RecyclerView todoList;

    private Event.Presenter presenter;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        todoList.setHasFixedSize(true);
        todoList.setItemAnimator(new DefaultItemAnimator());
        todoList.setLayoutManager(new LinearLayoutManager(this));
        eventId = getIntent().getExtras().getString("id");
        presenter = new EventPresenter(this, UseCaseHandler.getInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadEvent(eventId);
        presenter.loadTasks(eventId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @OnClick(R.id.task_add)
    public void addTask(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_task_input, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.new_task);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(editText.getText().toString().length() > 0) {
                            Task task = new Task();
                            task.setEventId(eventId);
                            task.setStatus(false);
                            task.setId(UUID.randomUUID().toString());
                            task.setMessage(editText.getText().toString());
                            presenter.newTask(task, dialog);
                        }
                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_show_event) {
            presenter.showEventCalendarLocal();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void bindEvent(EventCalendar event) {
        summary.setText(event.getSummary());
        description.setText(event.getDescription());
        end.setText(Utils.getDataByFormat(event.getEnd(), "EEE d MMM"));
        start.setText(Utils.getDataByFormat(event.getStart(), "EEE d MMM"));
    }

    @Override
    public void setBackgroundColor(int color) {
        toolbar.setBackgroundColor(color);
        appBar.setBackgroundColor(color);
        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);

    }

    @Override
    public void setStatusTitleColor(int color){
        collapsingToolbar.setCollapsedTitleTextColor(color);
        collapsingToolbar.setExpandedTitleColor(color);
        toolbar.setTitleTextColor(color);
    }

    @Override
    public void showSnarbar(String message) {
        Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbar.setTitle(title);
        getSupportActionBar().setTitle(title);

    }

    @Override
    public void setCountdownImage(Drawable drawable) {
        countdown.setImageDrawable(drawable);
    }

    @Override
    public void actionCalendarLocal(EventCalendar event) {

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("title", event.getSummary());
        intent.putExtra("description", event.getDescription());

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(event.getEnd().getTime());
        c.add(java.util.Calendar.DATE, -12);

        intent.putExtra("beginTime", c.getTimeInMillis());
        intent.putExtra("endTime", event.getEnd());
        startActivity(intent);
    }

    @Override
    public void setTaskAdapter(RecyclerView.Adapter adapterBitacora) {
        todoList.setAdapter(adapterBitacora);
    }
}
