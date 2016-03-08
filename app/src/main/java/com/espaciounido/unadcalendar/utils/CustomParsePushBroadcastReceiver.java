package com.espaciounido.unadcalendar.utils;

import android.content.Context;
import android.content.Intent;

import com.espaciounido.unadcalendar.db.C2DBOpenHelper;
import com.espaciounido.unadcalendar.model.Notification;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rauloko on 2/03/15.
 */
public class CustomParsePushBroadcastReceiver extends ParsePushBroadcastReceiver {
    private static String mId = "";
    public CustomParsePushBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //intent.setAction("com.parse.push.intent.DELETE");
        super.onReceive(context, intent);
        String action = intent.getAction();
        if(intent.hasExtra("com.parse.Data"))
        {
            try {
                JSONObject json = new JSONObject(intent.getExtras()
                        .getString("com.parse.Data"));
                registreNotification(context, json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Esta funcion se encarga de registrar la notificacion entrante
     *
     * @param jsonObject
     * @throws JSONException
     */
    private void registreNotification(Context context, JSONObject jsonObject) throws JSONException {
        if(jsonObject.getString("push_hash").equals(mId)) return;

        mId = jsonObject.getString("push_hash");

        if(jsonObject.has("title") &&
                jsonObject.has("alert") &&
                jsonObject.has("type") &&
                jsonObject.has("content_type")){
            new C2DBOpenHelper(context).open().insertNotification(
                    jsonObject.getString("content_type"),
                    jsonObject.getString("type"),
                    jsonObject.getString("alert"),
                    jsonObject.getString("title")
            );
        } else {
            new C2DBOpenHelper(context).open().insertNotification(
                    "",
                    Notification.TYPE_NONE,
                    jsonObject.getString("alert"),
                    "Notificación"
            );
        }


    }

    protected void onPushReceive(Context context,Intent intent) {
        super.onPushReceive(context, intent);
    }

    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

}
