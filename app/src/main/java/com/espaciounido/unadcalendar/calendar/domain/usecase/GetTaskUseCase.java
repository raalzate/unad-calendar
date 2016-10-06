package com.espaciounido.unadcalendar.calendar.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.calendar.domain.Task;
import com.espaciounido.unadcalendar.data.repo.todo.Todo;
import com.espaciounido.unadcalendar.data.repo.todo.TodoDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyMac on 2/10/16.
 */
public class GetTaskUseCase extends UseCase<GetTaskUseCase.Request, GetTaskUseCase.Response>{

    private final TodoDataSource repo;

    public GetTaskUseCase(TodoDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        repo.getTodos(requestValues.eventId, new TodoDataSource.LoadTodosCallback() {
            @Override
            public void onTodosLoaded(List<Todo> todos) {
                List<Task> tasks = new ArrayList<>();
                for(Todo todo : todos){
                    Task task = new Task();
                    task.setMessage(todo.getMessage());
                    task.setId(todo.getId());
                    task.setStatus(todo.isStatus());
                    task.setEventId(todo.getId());
                    tasks.add(task);
                }

                getUseCaseCallback().onSuccess(new Response(tasks));
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onSuccess(new Response(new ArrayList<Task>()));
            }
        });
    }

    @Override
    public void cancelUseCase() {
        repo.close();
    }

    public static final class Request implements UseCase.RequestValues{
        private final String eventId;

        public Request(String eventId) {
            this.eventId = eventId;
        }
    }

    public static final class Response implements UseCase.ResponseValue{
        private final List<Task> tasks;

        public Response(List<Task> tasks) {
            this.tasks = tasks;
        }

        public List<Task> getData(){
            return tasks;
        }
    }
}
