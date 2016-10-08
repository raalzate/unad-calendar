package com.espaciounido.unadcalendar.calendar.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.data.repo.todo.TodoDataSource;

/**
 * Created by MyMac on 3/10/16.
 */
public class DeleteTaskUseCase extends UseCase<DeleteTaskUseCase.Request, DeleteTaskUseCase.Response> {

    private final TodoDataSource repo;

    public DeleteTaskUseCase(TodoDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        repo.deleteTodo(requestValues.id, new TodoDataSource.SaveTodoCallback() {
            @Override
            public void onComplete() {
                getUseCaseCallback().onSuccess(new DeleteTaskUseCase.Response());
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

    public static final class Request implements UseCase.RequestValues {
        private final String id;

        public Request(String id) {
            this.id = id;
        }
    }

    public static final class Response implements UseCase.ResponseValue {

    }
}
