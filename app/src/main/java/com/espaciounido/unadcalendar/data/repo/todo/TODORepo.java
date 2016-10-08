package com.espaciounido.unadcalendar.data.repo.todo;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by MyMac on 2/10/16.
 */
public class TodoRepo implements TodoDataSource {

    @Override
    public void saveTodo(final Todo todo, final SaveTodoCallback saveTodoCallback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(todo);
        realm.commitTransaction();
        saveTodoCallback.onComplete();
    }

    @Override
    public void getTodos(String eventId, LoadTodosCallback loadTodosCallback) {
        RealmResults<Todo> elements = Realm.getDefaultInstance()
                .where(Todo.class)
                .equalTo("eventId", eventId)
                .findAll();
        if (elements.size() > 0) {
            loadTodosCallback.onTodosLoaded(elements);
        } else {
            loadTodosCallback.onDataNotAvailable();
        }
    }

    @Override
    public void changeStatusTodo(String id, boolean status, SaveTodoCallback saveTodoCallback) {
        Todo todo = Realm.getDefaultInstance()
                .where(Todo.class)
                .equalTo("id", id)
                .findFirst();

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        todo.setStatus(status);
        realm.copyToRealmOrUpdate(todo);

        realm.commitTransaction();
        saveTodoCallback.onComplete();

    }

    @Override
    public void deleteTodo(String id, SaveTodoCallback saveTodoCallback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Todo> elements = Realm.getDefaultInstance()
                .where(Todo.class)
                .equalTo("id", id)
                .findAll();

        elements.deleteAllFromRealm();
        realm.commitTransaction();
        saveTodoCallback.onComplete();
    }

    @Override
    public void close() {
        Realm.getDefaultInstance().close();
    }
}
