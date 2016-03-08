package com.espaciounido.unadcalendar.services;

import android.app.Activity;
import android.graphics.Color;


import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raalzate on 04/08/2015.
 */
public class DataInfUNAD {

    private String mUrl;
    private Activity mActivity;
    private Snackbar mSnackbar;
    private String period;
    private String course;
    private boolean accepted;
    private String email;

    private  static DataInfUNAD mDataInfUNAD;
    private DataInfoCallBack callBack;

    public static DataInfUNAD newInstance(Activity activity, int url){
        if(mDataInfUNAD == null)
            mDataInfUNAD = new DataInfUNAD(activity, url);
        return mDataInfUNAD;
    }

    private DataInfUNAD(Activity activity, int url){
        mUrl = activity.getResources().getString(url);
        mActivity = activity;
    }

    private void setTextStatus(String msn, boolean hasError){
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
                            searchDataInfo(callBack);
                        }
                    })
                    .color(Color.RED);
        }
        SnackbarManager.show(mSnackbar, mActivity);
    }

    /**
     *  Busca y obtene informacion de un servicio basico,
     *  relacionado con informacion de UNAD
     * @param callBack
     */
    public void searchDataInfo(DataInfoCallBack callBack) {
        this.callBack = callBack;

        setTextStatus("Cargando...", false);


    }

   public interface DataInfoCallBack{
       public void response(JSONObject response) throws JSONException;
       public void error(String error);
   }
}
