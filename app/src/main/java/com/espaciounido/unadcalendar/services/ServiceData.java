package com.espaciounido.unadcalendar.services;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by raalzate on 27/08/2015.
 */
public class ServiceData {
    private String mUrl;
    private Activity mActivity;
    private Context mContext;
    private Snackbar mSnackbar;

    private  static ServiceData mServiceData;
    private ServiceDataCallBack callBack;

    public static ServiceData newInstance(Activity activity){
        if(mServiceData == null) {
            mServiceData = new ServiceData(activity);
        }
        return mServiceData;
    }

    private ServiceData(Activity activity){
        mActivity = activity;
        mContext = mActivity.getApplicationContext();
    }

     public ServiceData(Context context){
        mContext = context;
    }


    public void setUrl(int url){
        mUrl = mContext.getResources().getString(url);
    }



    public void setTextStatus(String msn, boolean hasError){
        if(mActivity != null) {
            mSnackbar = Snackbar.with(mActivity)
                    .type(SnackbarType.MULTI_LINE)
                    .text(msn)
                    .animation(false)
                    .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE);

            if (hasError) {
                mSnackbar.actionLabel("Volver a Intenar")
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                snackbar.dismiss();
                                getDataInfo(callBack);
                            }
                        })
                        .color(Color.RED);
            }
            SnackbarManager.show(mSnackbar, mActivity);
        } else {
            Toast.makeText(mContext,msn, Toast.LENGTH_LONG).show();
        }
    }



    /**
     *  Busca y obtene informacion de un servicio basico,
     *  relacionado con informacion de UNAD
     * @param callBack
     */
    public void getDataInfo(ServiceDataCallBack callBack) {
        this.callBack = callBack;

        setTextStatus("Consultando...", false);

    }


    /**
     *  Busca y obtene informacion de un servicio basico,
     *  relacionado con informacion de UNAD
     * @param callBack
     */
    public void postDataInfo(ServiceDataCallBack callBack, final HashMap<String, String> params) {
        this.callBack = callBack;

        setTextStatus("Enviando...", false);

    }

    public interface ServiceDataCallBack {
        void response(JSONObject response) throws JSONException;
        void error(String error);
    }
}
