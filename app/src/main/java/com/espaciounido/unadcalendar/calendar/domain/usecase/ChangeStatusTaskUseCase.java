package com.espaciounido.unadcalendar.calendar.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.data.repo.todo.TodoDataSource;

/**
 * Created by MyMac on 3/10/16.
 */
public class ChangeStatusTaskUseCase extends UseCase<ChangeStatusTaskUseCase.Request, ChangeStatusTaskUseCase.Response>{


    private final TodoDataSource repo;

    public ChangeStatusTaskUseCase(TodoDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        repo.changeStatusTodo(requestValues.id, requestValues.status,
                new TodoDataSource.SaveTodoCallback() {
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
        repo.close();
    }

    public static final class Request implements UseCase.RequestValues{
        private final String id;
        private final boolean status;

        public Request(String id, boolean status) {
            this.id = id;
            this.status = status;
        }
    }

    public static final class Response implements UseCase.ResponseValue{

    }
}
