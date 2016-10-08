package com.espaciounido.unadcalendar.calendar.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.calendar.domain.Task;
import com.espaciounido.unadcalendar.data.repo.todo.Todo;
import com.espaciounido.unadcalendar.data.repo.todo.TodoDataSource;

/**
 * Created by MyMac on 2/10/16.
 */
public class CreateTaskUseCase extends UseCase<CreateTaskUseCase.Request, CreateTaskUseCase.Response> {

    private final TodoDataSource repo;

    public CreateTaskUseCase(TodoDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        repo.saveTodo(requestValues.mapTODO(), new TodoDataSource.SaveTodoCallback() {
            @Override
            public void onComplete() {
                getUseCaseCallback().onSuccess(new Response());
            }

            @Override
            public void onError(Throwable error) {
                getUseCaseCallback().onError();
            }
        });
    }

    @Override
    public void cancelUseCase() {

    }

    public static final class Request implements UseCase.RequestValues {
        private final Task task;

        public Request(Task task) {
            this.task = task;
        }

        public Todo mapTODO() {
            Todo todo = new Todo();
            todo.setMessage(task.getMessage());
            todo.setId(task.getId());
            todo.setStatus(task.isStatus());
            todo.setEventId(task.getEventId());
            return todo;
        }
    }

    public static final class Response implements UseCase.ResponseValue {

    }
}
