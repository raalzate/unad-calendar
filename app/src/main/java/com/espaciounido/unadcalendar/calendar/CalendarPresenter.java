package com.espaciounido.unadcalendar.calendar;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.UseCaseHandler;
import com.espaciounido.unadcalendar.calendar.domain.usecase.DeleteCalendarUseCase;
import com.espaciounido.unadcalendar.dashboard.events.OnListRefressEvent;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarLocalRepo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MyMac on 17/09/16.
 */
public class CalendarPresenter implements Calendar.Presenter {

    private final Calendar.View view;
    private final UseCaseHandler handler;
    private final DeleteCalendarUseCase deleteCalendarUseCase;
    private EventsPagerAdapter adapter;

    public CalendarPresenter(Calendar.View view, UseCaseHandler handler) {
        this.view = view;
        this.handler = handler;

        deleteCalendarUseCase = new DeleteCalendarUseCase(new GCCalendarLocalRepo());
    }

    @Override
    public void loadEvents() {
        adapter = new EventsPagerAdapter(view.getSupportFragmentManager(), view.getCode());
        view.setAdapter(adapter);
        view.setCurrentItem(1);
        view.setTitle(view.getName());
    }

    @Override
    public void deleteCalendar() {
        view.showSnarbar(view.getString(R.string.text_cal_delete_ok));
        handler.execute(deleteCalendarUseCase, new DeleteCalendarUseCase.Request(view.getCode()),
                new UseCase.UseCaseCallback<DeleteCalendarUseCase.Response>() {
                    @Override
                    public void onSuccess(DeleteCalendarUseCase.Response response) {
                        EventBus.getDefault().post(new OnListRefressEvent());
                        view.finish();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

}
