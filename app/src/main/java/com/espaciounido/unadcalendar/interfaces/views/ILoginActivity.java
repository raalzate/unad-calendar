package com.espaciounido.unadcalendar.interfaces.views;

import android.view.View;
import android.widget.ArrayAdapter;

/**
 * Created by raalzate on 22/02/2016.
 */
public interface ILoginActivity {

    int REQUEST_READ_CONTACTS = 0;

    void showProgress(boolean show);
    void errorLoginPass();
    void loginPass();
    void setAutoCompleteEmail(ArrayAdapter<String> contacts);
    void showPermissionRationale(View.OnClickListener listener);
}
