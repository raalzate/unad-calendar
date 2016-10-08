package com.espaciounido.unadcalendar.data.repo.todo;


import java.util.List;

/**
 * Created by MyMac on 2/10/16.
 */
public interface TodoDataSource {
    void saveTodo(Todo todo, SaveTodoCallback saveTodoCallback);

    void getTodos(String eventId, LoadTodosCallback loadTodosCallback);

    void changeStatusTodo(String id, boolean status, SaveTodoCallback saveTodoCallback);

    void deleteTodo(String id, SaveTodoCallback saveTodoCallback);

    void close();

    interface LoadTodosCallback {
        void onTodosLoaded(List<Todo> todos);

        void onDataNotAvailable();
    }

    interface SaveTodoCallback {
        void onComplete();

        void onError(Throwable error);
    }
}
