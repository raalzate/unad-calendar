package com.espaciounido.unadcalendar.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.utils.TinyDB;
import com.espaciounido.unadcalendar.utils.Utils;
import com.espaciounido.unadcalendar.views.SettingsBasicActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raalzate on 23/09/2015.
 */
public class ManagerService {

    private static ManagerService ourInstance;
    private final TinyDB mTinyDB;
    private final ServiceData mServiceData;
    private final Context mContext;

    public final String STR_DATA_USER_ID = "STR_DATA_USER_ID";

    public static ManagerService getInstance(Context context) {
        if(ourInstance == null)
            ourInstance = new ManagerService(context);
        return ourInstance;
    }

    public static ManagerService getInstance(Activity activity) {
        if(ourInstance == null)
            ourInstance = new ManagerService(activity);
        return ourInstance;
    }

    private ManagerService(Context context) {
       mTinyDB = new TinyDB(context);
        mServiceData = new ServiceData(context);
        mContext = context;
    }

    private ManagerService(Activity activity) {
        mTinyDB = new TinyDB(activity);
        mServiceData = ServiceData.newInstance(activity);
        mContext = activity.getApplicationContext();
    }

    /**
     * //&userId=&eventId=&attached=&message=
     * @param message
     * @param eventId
     */
    public  void pushNotification(String message, String eventId, String summary,
                                  ServiceData.ServiceDataCallBack callBack){
        if(!Utils.isOnline(mContext)) {
            mServiceData.setTextStatus("No tiene Internet", true);
            return;
        }

        if(mTinyDB.getString(STR_DATA_USER_ID).equals("")){
            mContext.startActivity(new Intent(mContext, SettingsBasicActivity.class));
            mServiceData.setTextStatus("No existe el usuario", true);
            return;
        }

        String userId = mTinyDB.getString(STR_DATA_USER_ID);

        String installationId = ParseInstallation.getCurrentInstallation().getObjectId();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("eventId", eventId);
        params.put("message", message);
        params.put("summary", summary);
        params.put("installationId", installationId);

        mServiceData.postDataInfo(callBack, params);

    }

     public void registerUser(){

         if(!Utils.isOnline(mContext)) return;

         HashMap<String, String> params = new HashMap<String, String>();

         final String email = mTinyDB.getString("user_email");
         final String period = mTinyDB.getString("user_period");
         final String carrera = mTinyDB.getString("user_carrera");
         final String cead = mTinyDB.getString("user_cead");
         final String name = mTinyDB.getString("user_name");

         params.put("username", name);
         params.put("email", email);
         params.put("cead", cead);
         params.put("program", carrera);
         params.put("period", period);

         mServiceData.postDataInfo(new ServiceData.ServiceDataCallBack() {
             @Override
             public void response(JSONObject response) throws JSONException {
                    if(response.getBoolean("status")){
                        if(mTinyDB.getString(STR_DATA_USER_ID).equals("")){

                            //Track Event Register User
                            Map<String, String> dimensions = new HashMap<>();
                            dimensions.put("Period", period);
                            dimensions.put("CEAD", cead);
                            dimensions.put("Program", carrera);
                            ParseAnalytics.trackEventInBackground("RegisterUser", dimensions);

                            mServiceData.setTextStatus("Usuario registrado!", true);
                        } else {
                            mServiceData.setTextStatus("Usuario actualizado!", true);
                        }
                        mTinyDB.putString(STR_DATA_USER_ID, response.getString("userId"));
                    }
             }

             @Override
             public void error(String error) {
                 //Track Event Error
                 Map<String, String> dimensions = new HashMap<>();
                 dimensions.put("Type", "Service");
                 dimensions.put("LocalizedMessage", ManagerService.class.getCanonicalName());
                 dimensions.put("Method", "registerUser");

                 mServiceData.setTextStatus(error, true);
             }
         }, params);
     }



}
