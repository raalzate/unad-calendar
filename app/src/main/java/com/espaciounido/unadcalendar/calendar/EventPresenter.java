package com.espaciounido.unadcalendar.calendar;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.UseCaseHandler;
import com.espaciounido.unadcalendar.calendar.domain.EventCalendar;
import com.espaciounido.unadcalendar.calendar.domain.Task;
import com.espaciounido.unadcalendar.calendar.domain.usecase.ChangeStatusTaskUseCase;
import com.espaciounido.unadcalendar.calendar.domain.usecase.CreateTaskUseCase;
import com.espaciounido.unadcalendar.calendar.domain.usecase.DeleteTaskUseCase;
import com.espaciounido.unadcalendar.calendar.domain.usecase.GetTaskUseCase;
import com.espaciounido.unadcalendar.calendar.domain.usecase.GetEventUseCase;
import com.espaciounido.unadcalendar.data.repo.gcevent.GCEventLocalRepo;
import com.espaciounido.unadcalendar.data.repo.todo.TodoRepo;
import com.espaciounido.unadcalendar.utils.DateUtils;
import com.espaciounido.unadcalendar.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyMac on 25/09/16.
 */
public class EventPresenter implements Event.Presenter {

    private final Event.View view;
    private final UseCaseHandler caseHandler;
    private final GetEventUseCase eventUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final CreateTaskUseCase createTaskUseCase;
    private final DeleteTaskUseCase deleteTastUseCase;
    private final ChangeStatusTaskUseCase changeStatusTestUseCase;

    private EventCalendar event;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;


    public EventPresenter(Event.View view, UseCaseHandler caseHandler){
        this.view = view;
        this.caseHandler = caseHandler;
        eventUseCase = new GetEventUseCase(new GCEventLocalRepo());

        TodoRepo repo = new TodoRepo();
        getTaskUseCase = new GetTaskUseCase(repo);
        createTaskUseCase = new CreateTaskUseCase(repo);
        deleteTastUseCase = new DeleteTaskUseCase(repo);
        changeStatusTestUseCase = new ChangeStatusTaskUseCase(repo);

        tasks = new ArrayList<>();


    }

    @Override
    public void loadEvent(String eventId) {
        caseHandler.execute(eventUseCase,
                new GetEventUseCase.Request(eventId),
                new UseCase.UseCaseCallback<GetEventUseCase.Response>() {
                    @Override
                    public void onSuccess(GetEventUseCase.Response response) {
                        int dayRest = DateUtils.restDay(
                                java.util.Calendar.getInstance().getTime(),
                                response.eventCalendar.getEnd());

                        event = response.eventCalendar;
                        int colorDefine = Utils.defineColorBayDay(dayRest);
                        view.setTitle(defineTitle(response.eventCalendar.getSummary()));
                        if (dayRest > 0) {
                            view.setBackgroundColor(colorDefine);
                        }
                        view.setCountdownImage(getDrawable(dayRest));
                        view.setStatusTitleColor(Color.WHITE);
                        view.bindEvent(response.eventCalendar);
                    }

                    @Override
                    public void onError() {
                        view.showSnarbar("Existe un problema en obtener el evento");
                    }
                });
    }

    @Override
    public void loadTasks(String eventId) {



        caseHandler.execute(getTaskUseCase,
                new GetTaskUseCase.Request(eventId),
                new UseCase.UseCaseCallback<GetTaskUseCase.Response>() {
            @Override
            public void onSuccess(GetTaskUseCase.Response response) {
                tasks = response.getData();
                taskAdapter = new TaskAdapter(tasks, EventPresenter.this);
                view.setTaskAdapter(taskAdapter);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void showEventCalendarLocal() {
        if (event != null) {
            view.actionCalendarLocal(event);
        }
    }

    @Override
    public void newTask(final Task task, final DialogInterface dialog) {

        caseHandler.execute(createTaskUseCase,
                new CreateTaskUseCase.Request(task),
                new UseCase.UseCaseCallback<CreateTaskUseCase.Response>() {
                    @Override
                    public void onSuccess(CreateTaskUseCase.Response response) {
                        tasks.add(task);
                        taskAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onError() {
                        dialog.dismiss();
                    }
                });
    }

    private Drawable getDrawable(int day){
        String days;
        if(day > 1) {
             days = String.format("-%sd", day);
        } else if(day == 1) {
            days = "Mañ";
        } else if(day == 0){
            days = "Hoy";
        } else {
            days = "Fin";
        }
        int colorDefine = Utils.defineColorBayDay(day);
        return TextDrawable.builder()
                        .beginConfig()
                        .withBorder(4)
                        .textColor(colorDefine == Color.WHITE ? Color.GRAY : Color.WHITE)
                        .endConfig()
                        .buildRoundRect(days, colorDefine, 10);
    }

    private String defineTitle(String summary) {
        return summary.substring(summary.indexOf('-')+2,
                summary.indexOf("Valor:"));
    }


    @Override
    public void onChangeStatus(final Task task) {
        caseHandler.execute(changeStatusTestUseCase, new ChangeStatusTaskUseCase.Request(task.getId(), task.isStatus()), new UseCase.UseCaseCallback<ChangeStatusTaskUseCase.Response>() {
            @Override
            public void onSuccess(ChangeStatusTaskUseCase.Response response) {
                taskAdapter.notifyDataSetChanged();
                if(task.isStatus()) {
                    view.showSnarbar("¡Tarea realizada!");
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onDelete(final Task task, final int position) {
        caseHandler.execute(deleteTastUseCase, new DeleteTaskUseCase.Request(task.getId()),
                new UseCase.UseCaseCallback<DeleteTaskUseCase.Response>() {
            @Override
            public void onSuccess(DeleteTaskUseCase.Response response) {
                tasks.remove(position);
                taskAdapter.notifyDataSetChanged();
                view.showSnarbar("¡Tarea eliminada!");
            }

            @Override
            public void onError() {

            }
        });
    }
}
