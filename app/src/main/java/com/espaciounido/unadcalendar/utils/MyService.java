package com.espaciounido.unadcalendar.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyService extends IntentService {

    private static final String ACTION_ALERT = "com.espaciounido.unadcalendar.utils.action.ALERT";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionAlert(Context context) {
        Intent intent = new Intent(context, MyService.class);
        intent.setAction(ACTION_ALERT);
       // intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }



    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ALERT.equals(action)) {
               // final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActioAlert();
            }
        }
    }


    private void handleActioAlert() {
        while (true){
            SystemClock.sleep(5000);
            System.out.println("Activo");
        }
    }

}
