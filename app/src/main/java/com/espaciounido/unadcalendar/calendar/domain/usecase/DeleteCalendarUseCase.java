package com.espaciounido.unadcalendar.calendar.domain.usecase;

import com.espaciounido.unadcalendar.UseCase;
import com.espaciounido.unadcalendar.data.repo.FirebaseReferences;
import com.espaciounido.unadcalendar.data.repo.gccalendar.GCCalendarDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by MyMac on 18/09/16.
 */
public class DeleteCalendarUseCase extends UseCase<DeleteCalendarUseCase.Request, DeleteCalendarUseCase.Response> {

    private GCCalendarDataSource repo;

    public DeleteCalendarUseCase(GCCalendarDataSource repo) {
        this.repo = repo;
    }

    @Override
    protected void executeUseCase(Request requestValues) {
        repo.deleteById(requestValues.id, new GCCalendarDataSource.DeleteCalendarCallback() {
            @Override
            public void onComplete() {
                getUseCaseCallback().onSuccess(new Response());
                String idr = extId(getRequestValues().id);
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseReferences.getGCCalendar().child(uid).child(idr).removeValue();
                FirebaseMessaging.getInstance()
                        .unsubscribeFromTopic(idr);
            }

            @Override
            public void onError(Throwable error) {
                getUseCaseCallback().onError();
            }
        });
    }

    private String extId(String id) {
        return id.substring(0, id.indexOf('@'));
    }


    @Override
    public void cancelUseCase() {
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
