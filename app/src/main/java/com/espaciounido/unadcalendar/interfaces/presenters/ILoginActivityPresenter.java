package com.espaciounido.unadcalendar.interfaces.presenters;

import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * Created by raalzate on 22/02/2016.
 */
public interface ILoginActivityPresenter {

    String[] PROJECTION = {
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
    };

    int ADDRESS = 0;
    int IS_PRIMARY = 1;

    boolean isAuthTask();
    Loader<Cursor> getContactsContract();
    void loadContacts(Cursor cursor);
    boolean mayRequestContacts();
    void attemptLoginEmailAndPassword(AutoCompleteTextView mEmailView, EditText mPasswordView);
}
