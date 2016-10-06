package com.espaciounido.unadcalendar;

/**
 * Created by MyMac on 11/09/16.
 */

import android.os.Handler;

/**
 * Runs {@link UseCase}s using a {@link UseCaseScheduler}.
 */
public class UseCaseHandler {

    private static UseCaseHandler INSTANCE;

    private final Handler mHandler = new Handler();

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler();
        }
        return INSTANCE;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(
            final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
        useCase.setRequestValues(values);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback));
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                useCase.run();
            }
        });
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements
            UseCase.UseCaseCallback<V> {
        private final UseCase.UseCaseCallback<V> mCallback;

        public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback) {
            mCallback = callback;
        }

        @Override
        public void onSuccess(V response) {
            mCallback.onSuccess(response);
        }

        @Override
        public void onError() {
            mCallback.onError();
        }
    }
}
